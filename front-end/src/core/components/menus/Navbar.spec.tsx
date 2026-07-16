import {act, render, screen} from '@testing-library/react';
import userEvent from '@testing-library/user-event';
import { describe, it, expect } from 'vitest';
import { Navbar } from './Navbar';
import {AppProviders} from "@core/context/app/AppProviders.tsx";

describe('Navbar Component', () => {
    it('should render application titles and handle login modal toggling', async () => {
        const user = userEvent.setup();
        render(
            <AppProviders>
                <Navbar />
            </AppProviders>
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

it('should support explicit close handler triggers from dropdown child components', async () => {
    render(
        <AppProviders>
            <Navbar />
        </AppProviders>
    );

    const loginButton = screen.getByRole('button', { name: '' });
    await act(async () => { loginButton.click(); });

    await act(async () => { loginButton.click(); });
    expect(loginButton).toHaveClass('bg-btn-primary-hover');
});