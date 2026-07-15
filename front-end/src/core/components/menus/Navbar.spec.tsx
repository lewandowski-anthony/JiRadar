import { render, screen } from '@testing-library/react';
import userEvent from '@testing-library/user-event';
import { describe, it, expect } from 'vitest';
import { Navbar } from './Navbar';
import { LocaleProvider } from '@core/context/language/LocaleProvider';
import { AuthProvider } from '@core/context/authentication/AuthContext';
import { ThemeProvider } from '@core/context/theme/ThemeContext';

describe('Navbar Component', () => {
    it('should render application titles and handle login modal toggling', async () => {
        const user = userEvent.setup();
        render(
            <LocaleProvider>
                <ThemeProvider>
                    <AuthProvider>
                        <Navbar />
                    </AuthProvider>
                </ThemeProvider>
            </LocaleProvider>
        );

        expect(screen.getByText('JiRadar Dashboard')).toBeInTheDocument();

        const loginButton = screen.getByRole('button', { name: '' });

        expect(loginButton).toHaveClass('bg-btn-primary-hover');
        expect(loginButton).not.toHaveClass('bg-btn-primary');

        await user.click(loginButton);

        expect(loginButton).toHaveClass('bg-btn-primary');
        expect(loginButton).not.toHaveClass('bg-btn-primary-hover');
    });
});