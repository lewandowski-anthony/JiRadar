import { render, screen } from '@testing-library/react';
import userEvent from '@testing-library/user-event';
import { describe, it, expect, vi } from 'vitest';
import { GitHubLoginForm } from './GitHubLoginForm';

describe('GitHubLoginForm Component', () => {
    const defaultProps = {
        onSuccess: vi.fn(),
        onError: vi.fn(),
        loginFn: vi.fn(),
        loading: false
    };

    it('should update token input value on user typing', async () => {
        const user = userEvent.setup();
        render(<GitHubLoginForm {...defaultProps} />);

        const tokenInput = screen.getByTestId('github-token-input');

        await user.type(tokenInput, 'ghp_secretToken123');
        expect(tokenInput).toHaveValue('ghp_secretToken123');
    });

    it('should call loginFn with correct parameters on form submit', async () => {
        const user = userEvent.setup();
        const mockLoginFn = vi.fn().mockResolvedValue(undefined);
        render(<GitHubLoginForm {...defaultProps} loginFn={mockLoginFn} />);

        const tokenInput = screen.getByTestId('github-token-input');
        const submitButton = screen.getByTestId('github-submit-button');

        await user.type(tokenInput, 'ghp_secretToken123');
        await user.click(submitButton);

        expect(mockLoginFn).toHaveBeenCalledWith('ghp_secretToken123', 'github');
        expect(defaultProps.onSuccess).toHaveBeenCalled();
    });

    it('should call onError when loginFn rejects', async () => {
        const user = userEvent.setup();
        const mockError = { response: { data: { message: 'Bad credentials' } } };
        const mockLoginFn = vi.fn().mockRejectedValue(mockError);
        render(<GitHubLoginForm {...defaultProps} loginFn={mockLoginFn} />);

        const tokenInput = screen.getByTestId('github-token-input');
        const submitButton = screen.getByTestId('github-submit-button');

        await user.type(tokenInput, 'invalid-pat');
        await user.click(submitButton);

        expect(mockLoginFn).toHaveBeenCalledWith('invalid-pat', 'github');
        expect(defaultProps.onError).toHaveBeenCalledWith('Bad credentials');
    });

    it('should disable inputs and button when loading is true', () => {
        render(<GitHubLoginForm {...defaultProps} loading={true} />);

        const tokenInput = screen.getByTestId('github-token-input');
        const submitButton = screen.getByTestId('github-submit-button');

        expect(tokenInput).toBeDisabled();
        expect(submitButton).toBeDisabled();
    });
});