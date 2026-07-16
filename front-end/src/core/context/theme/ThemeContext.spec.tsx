import { render, screen, act } from '@testing-library/react';
import { describe, it, expect, vi, beforeEach } from 'vitest';
import { ThemeProvider, useTheme } from './ThemeContext';
import { getCookie, setCookie } from '@core/utils/cookies';

vi.mock('@core/utils/cookies', () => ({
    getCookie: vi.fn(),
    setCookie: vi.fn(),
}));

function TestComponent() {
    const { theme, toggleTheme } = useTheme();
    return (
        <div>
            <span data-testid="theme-status">{theme}</span>
            <button data-testid="toggle-btn" onClick={toggleTheme}>Toggle</button>
        </div>
    );
}

describe('ThemeContext Framework', () => {
    beforeEach(() => {
        vi.clearAllMocks();
        document.documentElement.className = '';
        document.documentElement.style.colorScheme = '';
    });

    it('should initialize theme from cookies if present', async () => {
        vi.mocked(getCookie).mockReturnValue('light');

        render(
            <ThemeProvider>
                <TestComponent />
            </ThemeProvider>
        );

        expect(screen.getByTestId('theme-status').textContent).toBe('light');
        expect(document.documentElement.classList.contains('dark')).toBe(false);
        expect(document.documentElement.style.colorScheme).toBe('light');
    });

    it('should fallback to prefers-color-scheme light if matchMedia matches', async () => {
        vi.mocked(getCookie).mockReturnValue(null);
        Object.defineProperty(window, 'matchMedia', {
            writable: true,
            value: vi.fn().mockImplementation((query) => ({
                matches: query === '(prefers-color-scheme: light)',
                media: query,
            })),
        });

        render(
            <ThemeProvider>
                <TestComponent />
            </ThemeProvider>
        );

        expect(screen.getByTestId('theme-status').textContent).toBe('light');
    });

    it('should toggle theme from dark to light and store cookie allocation', async () => {
        vi.mocked(getCookie).mockReturnValue('dark');

        render(
            <ThemeProvider>
                <TestComponent />
            </ThemeProvider>
        );

        expect(screen.getByTestId('theme-status').textContent).toBe('dark');
        expect(document.documentElement.classList.contains('dark')).toBe(true);

        const button = screen.getByTestId('toggle-btn');
        await act(async () => {
            button.click();
        });

        expect(screen.getByTestId('theme-status').textContent).toBe('light');
        expect(document.documentElement.classList.contains('dark')).toBe(false);
        expect(document.documentElement.style.colorScheme).toBe('light');
        expect(setCookie).toHaveBeenCalledWith('jiradar_theme', 'light', 365);
    });

    it('should toggle theme from light to dark on interactive trigger', async () => {
        vi.mocked(getCookie).mockReturnValue('light');

        render(
            <ThemeProvider>
                <TestComponent />
            </ThemeProvider>
        );

        const button = screen.getByTestId('toggle-btn');
        await act(async () => {
            button.click();
        });

        expect(screen.getByTestId('theme-status').textContent).toBe('dark');
        expect(document.documentElement.classList.contains('dark')).toBe(true);
        expect(document.documentElement.style.colorScheme).toBe('dark');
        expect(setCookie).toHaveBeenCalledWith('jiradar_theme', 'dark', 365);
    });

    it('should throw an error error block when hook used outside boundary target', () => {
        const consoleSpy = vi.spyOn(console, 'error').mockImplementation(() => {});

        expect(() => render(<TestComponent />)).toThrow(
            'useTheme must be used within a ThemeProvider'
        );

        consoleSpy.mockRestore();
    });
});