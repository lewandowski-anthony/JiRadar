import { render, screen } from '@testing-library/react';
import userEvent from '@testing-library/user-event';
import { describe, it, expect, vi } from 'vitest';
import { LoginDropdown } from './LoginDropdown';
import {LocaleProvider} from "@core/context/LocaleProvider";
import {AuthProvider} from "@core/context/AuthContext.tsx";

describe('LoginDropdown Component', () => {
    it('should return null if isOpen is false', () => {
        const { container } = render(
            <LocaleProvider>
                <AuthProvider>
                    <LoginDropdown isOpen={false} onClose={vi.fn()} />
                </AuthProvider>
            </LocaleProvider>
        );
        expect(container.firstChild).toBeNull();
    });

    it('should handle token value input state change', async () => {
        const user = userEvent.setup();
        render(
            <LocaleProvider>
                <AuthProvider>
                    <LoginDropdown isOpen={true} onClose={vi.fn()} />
                </AuthProvider>
            </LocaleProvider>
        );

        const tokenInput = screen.getByPlaceholderText('Token');
        await user.type(tokenInput, 'secret-jira-key');

        expect(tokenInput).toHaveValue('secret-jira-key');
    });

    it('should trigger onClose when clicking outside the dropdown container', async () => {
        const mockClose = vi.fn();
        const user = userEvent.setup();

        render(
            <LocaleProvider>
                <AuthProvider>
                    <div>
                        <div data-testid="outside">Outside Zone</div>
                        <LoginDropdown isOpen={true} onClose={mockClose} />
                    </div>
                </AuthProvider>
            </LocaleProvider>
        );

        const outsideElement = screen.getByTestId('outside');
        await user.click(outsideElement);

        expect(mockClose).toHaveBeenCalled();
    });
});