import { render, screen } from '@testing-library/react';
import userEvent from '@testing-library/user-event';
import { describe, it, expect, vi } from 'vitest';
import { GitHubLoginForm } from './GitHubLoginForm';
import { LocaleProvider } from '@core/context/language/LocaleProvider';

describe('GitHubLoginForm Component', () => {
    const defaultProps = {
        onSuccess: vi.fn(),
        onError: vi.fn(),
        loginFn: vi.fn(),
        loading: false
    };

    it('should update token input value when user types', async () => {
        const user = userEvent.setup();
        render(
            <LocaleProvider>
                <GitHubLoginForm {...defaultProps} />
            </LocaleProvider>
        );

        const tokenInput = screen.getByTestId('github-token-input');
        await user.type(tokenInput, 'my-github-pat');

        expect(tokenInput).toHaveValue('my-github-pat');
    });

    it('should call loginFn with correct parameters on form submit', async () => {
        const user = userEvent.setup();
        const mockLoginFn = vi.fn().mockResolvedValue(undefined);

        render(
            <LocaleProvider>
                <GitHubLoginForm {...defaultProps} loginFn={mockLoginFn} />
            </LocaleProvider>
        );

        const tokenInput = screen.getByTestId('github-token-input');
        const submitButton = screen.getByTestId('github-submit-button');

        await user.type(tokenInput, '  my-github-pat  ');
        await user.click(submitButton);

        expect(mockLoginFn).toHaveBeenCalledWith('my-github-pat', 'github');
        expect(defaultProps.onSuccess).toHaveBeenCalled();
    });

    it('should call onError when loginFn rejects', async () => {
        const user = userEvent.setup();
        const mockError = { response: { data: { message: 'Bad Credentials' } } };
        const mockLoginFn = vi.fn().mockRejectedValue(mockError);

        render(
            <LocaleProvider>
                <GitHubLoginForm {...defaultProps} loginFn={mockLoginFn} />
            </LocaleProvider>
        );

        const tokenInput = screen.getByTestId('github-token-input');
        const submitButton = screen.getByTestId('github-submit-button');

        await user.type(tokenInput, 'wrong-token');
        await user.click(submitButton);

        expect(mockLoginFn).toHaveBeenCalledWith('wrong-token', 'github');
        expect(defaultProps.onError).toHaveBeenCalledWith('Bad Credentials');
    });

    it('should disable input and button when loading is true', () => {
        render(
            <LocaleProvider>
                <GitHubLoginForm {...defaultProps} loading={true} />
            </LocaleProvider>
        );

        const tokenInput = screen.getByTestId('github-token-input');
        const submitButton = screen.getByTestId('github-submit-button');

        expect(tokenInput).toBeDisabled();
        expect(submitButton).toBeDisabled();
    });
});