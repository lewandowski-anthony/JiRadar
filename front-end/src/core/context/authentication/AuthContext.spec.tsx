import { render, screen, waitFor } from '@testing-library/react';
import { describe, it, expect, vi, beforeEach } from 'vitest';
import { AuthProvider, useAuth } from '@core/context/authentication/AuthContext';
import { JiradarService } from '@core/services/JiradarService';
import { getCookie, setCookie } from '@core/utils/cookies';

vi.mock('@core/services/JiradarService', () => ({
    JiradarService: {
        getMyAccount: vi.fn(),
    },
}));

vi.mock('@core/utils/cookies', () => ({
    getCookie: vi.fn(),
    setCookie: vi.fn(),
}));

function TestComponent() {
    const { user, isAuthenticated, loading, jiraLogin, logout } = useAuth();

    const handleLogin = async () => {
        try {
            await jiraLogin('john@doe.com', 'my-token', 'jira');
        } catch (e) {
            console.error(e);
        }
    };

    if (loading) return <div data-testid="loading">Loading...</div>;

    return (
        <div>
            <div data-testid="auth-state">{isAuthenticated ? 'Connected' : 'Disconnected'}</div>
            <div data-testid="user-name">{user ? user.displayName : 'No User'}</div>
            <button data-testid="login-btn" onClick={handleLogin}>Login</button>
            <button data-testid="logout-btn" onClick={logout}>Logout</button>
        </div>
    );
}

describe('AuthContext Component', () => {
    const mockUser = {
        name: 'John Doe',
        displayName: 'John Doe',
        email: 'john@doe.com',
        avatarUrl: 'http://avatar.com',
    };

    beforeEach(() => {
        vi.clearAllMocks();
    });

    it('should initialize as disconnected when no token is present', async () => {
        vi.mocked(getCookie).mockImplementation((name) => {
            if (name === 'jiradar_tracker') return 'jira';
            return null;
        });

        render(
            <AuthProvider>
                <TestComponent />
            </AuthProvider>
        );

        const authState = await screen.findByTestId('auth-state');
        expect(authState.textContent).toBe('Disconnected');
        expect(screen.getByTestId('user-name').textContent).toBe('No User');
    });

    it('should automatically connect user if token and profile are valid on init', async () => {
        vi.mocked(getCookie).mockImplementation((name) => {
            if (name === 'jiradar_token') return 'valid-token';
            if (name === 'jiradar_tracker') return 'jira';
            return null;
        });
        vi.mocked(JiradarService.getMyAccount).mockResolvedValue(mockUser);

        render(
            <AuthProvider>
                <TestComponent />
            </AuthProvider>
        );

        const authState = await screen.findByTestId('auth-state');
        expect(authState.textContent).toBe('Connected');
        expect(screen.getByTestId('user-name').textContent).toBe('John Doe');
        expect(JiradarService.getMyAccount).toHaveBeenCalledWith('jira');
    });

    it('should log out and clear cookies if API fails on initialization', async () => {
        vi.mocked(getCookie).mockImplementation((name) => {
            if (name === 'jiradar_token') return 'invalid-token';
            return null;
        });
        vi.mocked(JiradarService.getMyAccount).mockRejectedValue(new Error('API Error'));

        render(
            <AuthProvider>
                <TestComponent />
            </AuthProvider>
        );

        const authState = await screen.findByTestId('auth-state');
        expect(authState.textContent).toBe('Disconnected');
        expect(setCookie).toHaveBeenCalledWith('jiradar_token', '', -1);
        expect(setCookie).toHaveBeenCalledWith('jiradar_tracker', '', -1);
    });

    it('should handle successful jiraLogin flow with base64 encoding', async () => {
        vi.mocked(getCookie).mockReturnValue(null);
        vi.mocked(JiradarService.getMyAccount).mockResolvedValue(mockUser);

        render(
            <AuthProvider>
                <TestComponent />
            </AuthProvider>
        );

        const loginButton = await screen.findByTestId('login-btn');
        loginButton.click();

        const expectedBase64 = btoa('john@doe.com:my-token');

        await waitFor(() => {
            expect(setCookie).toHaveBeenCalledWith('jiradar_token', expectedBase64, 1);
            expect(setCookie).toHaveBeenCalledWith('jiradar_tracker', 'jira', 7);
        });

        const authState = await screen.findByTestId('auth-state');
        expect(authState.textContent).toBe('Connected');
        expect(screen.getByTestId('user-name').textContent).toBe('John Doe');
    });

    it('should clean up state, clear cookies and handle failed jiraLogin without crashing', async () => {
        vi.mocked(getCookie).mockReturnValue(null);
        vi.mocked(JiradarService.getMyAccount).mockRejectedValue(new Error('API Error'));

        render(
            <AuthProvider>
                <TestComponent />
            </AuthProvider>
        );

        const loginButton = await screen.findByTestId('login-btn');
        loginButton.click();

        const expectedBase64 = btoa('john@doe.com:my-token');

        await waitFor(() => {
            expect(setCookie).toHaveBeenCalledWith('jiradar_token', expectedBase64, 1);
        });

        const authState = await screen.findByTestId('auth-state');
        expect(authState.textContent).toBe('Disconnected');
    });

    it('should clean up state and cookies on logout', async () => {
        vi.mocked(getCookie).mockImplementation((name) => {
            if (name === 'jiradar_token') return 'valid-token';
            return null;
        });
        vi.mocked(JiradarService.getMyAccount).mockResolvedValue(mockUser);

        render(
            <AuthProvider>
                <TestComponent />
            </AuthProvider>
        );

        const authState = await screen.findByTestId('auth-state');
        expect(authState.textContent).toBe('Connected');

        const logoutButton = screen.getByTestId('logout-btn');
        logoutButton.click();

        await waitFor(() => {
            expect(setCookie).toHaveBeenCalledWith('jiradar_token', '', -1);
            expect(setCookie).toHaveBeenCalledWith('jiradar_tracker', '', -1);
            expect(authState.textContent).toBe('Disconnected');
        });

        expect(screen.getByTestId('user-name').textContent).toBe('No User');
    });

    it('should throw error when useAuth is used outside AuthProvider', () => {
        const consoleSpy = vi.spyOn(console, 'error').mockImplementation(() => {});

        expect(() => render(<TestComponent />)).toThrow(
            "Use auth must be used within a AuthProvider"
        );

        consoleSpy.mockRestore();
    });
});