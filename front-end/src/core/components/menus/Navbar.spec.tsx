import { render, screen } from '@testing-library/react';
import userEvent from '@testing-library/user-event';
import { describe, it, expect } from 'vitest';
import { Navbar } from './Navbar';
import { LocaleProvider } from '@core/context/LocaleProvider';
import {AuthProvider} from "@core/context/AuthContext.tsx";

describe('Navbar Component', () => {
    it('should render application titles and handle login modal toggling', async () => {
        const user = userEvent.setup();
        render(
            <LocaleProvider>
                <AuthProvider>
                    <Navbar />
                </AuthProvider>
            </LocaleProvider>
        );

        expect(screen.getByText('JiRadar Dashboard')).toBeInTheDocument();

        const toggleButton = screen.getByRole('button', { name: '' });
        expect(toggleButton).not.toHaveClass('border-purple-500');

        await user.click(toggleButton);
        expect(toggleButton).toHaveClass('border-purple-500');
    });
});