import { render, screen } from '@testing-library/react';
import userEvent from '@testing-library/user-event';
import { describe, it, expect, vi } from 'vitest';
import { JiraLoginForm } from './JiraLoginForm';

describe('JiraLoginForm Component', () => {
    const defaultProps = {
        onSuccess: vi.fn(),
        onError: vi.fn(),
        loginFn: vi.fn(),
        loading: false
    };

    it('should update token and email input values on user typing', async () => {
        const user = userEvent.setup();
        render(<JiraLoginForm {...defaultProps} />);

        const emailInput = screen.getByTestId('jira-email-input');
        const tokenInput = screen.getByTestId('jira-token-input');

        await user.type(emailInput, 'test@jiradar.com');
        await user.type(tokenInput, 'my-jira-token');

        expect(emailInput).toHaveValue('test@jiradar.com');
        expect(tokenInput).toHaveValue('my-jira-token');
    });

    it('should call loginFn with correct parameters on form submit', async () => {
        const user = userEvent.setup();
        const mockLoginFn = vi.fn().mockResolvedValue(undefined);
        render(<JiraLoginForm {...defaultProps} loginFn={mockLoginFn} />);

        const emailInput = screen.getByTestId('jira-email-input');
        const tokenInput = screen.getByTestId('jira-token-input');
        const submitButton = screen.getByTestId('jira-submit-button');

        await user.type(emailInput, 'test@jiradar.com');
        await user.type(tokenInput, 'my-jira-token');
        await user.click(submitButton);

        expect(mockLoginFn).toHaveBeenCalledWith('test@jiradar.com', 'my-jira-token', 'jira');
        expect(defaultProps.onSuccess).toHaveBeenCalled();
    });

    it('should call onError when loginFn rejects', async () => {
        const user = userEvent.setup();
        const mockError = { response: { data: { message: 'Invalid Credentials' } } };
        const mockLoginFn = vi.fn().mockRejectedValue(mockError);
        render(<JiraLoginForm {...defaultProps} loginFn={mockLoginFn} />);

        const emailInput = screen.getByTestId('jira-email-input');
        const tokenInput = screen.getByTestId('jira-token-input');
        const submitButton = screen.getByTestId('jira-submit-button');

        await user.type(emailInput, 'test@jiradar.com');
        await user.type(tokenInput, 'wrong-token');
        await user.click(submitButton);

        expect(mockLoginFn).toHaveBeenCalledWith('test@jiradar.com', 'wrong-token', 'jira');
        expect(defaultProps.onError).toHaveBeenCalledWith('Invalid Credentials');
    });

    it('should disable inputs and button when loading is true', () => {
        render(<JiraLoginForm {...defaultProps} loading={true} />);

        const emailInput = screen.getByTestId('jira-email-input');
        const tokenInput = screen.getByTestId('jira-token-input');
        const submitButton = screen.getByTestId('jira-submit-button');

        expect(emailInput).toBeDisabled();
        expect(tokenInput).toBeDisabled();
        expect(submitButton).toBeDisabled();
    });
});