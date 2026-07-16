import { render, screen } from '@testing-library/react';
import userEvent from '@testing-library/user-event';
import { describe, it, expect, vi } from 'vitest';
import { HistoryList } from './HistoryList';
import { LocaleProvider } from '@core/context/language/LocaleProvider';

describe('HistoryList Component', () => {
    const mockPageChange = vi.fn();
    const defaultPage = {
        content: [
            {
                issueKey: 'JIRA-101',
                issueSummary: 'Fix core memory leak architecture',
                issueType: 'Bug',
                date: '2026-07-14T12:00:00Z',
                transitionType: 'In Progress',
                issueAssignee: { name: 'Alex', email: 'alex@jiradar.com', displayName: 'Alex', avatarUrl: 'avatar.png' },
            }
        ],
        page: { size: 10, number: 0, totalElements: 11, totalPages: 2 }
    };

    const renderWithContext = (ui: React.ReactElement) => {
        return render(
            <LocaleProvider>
                {ui}
            </LocaleProvider>
        );
    };

    it('renders list items and executes callback on next page button click', async () => {
        const user = userEvent.setup();
        renderWithContext(<HistoryList historyPage={defaultPage} onPageChange={mockPageChange} historyLoading={false} />);

        expect(screen.getByText('JIRA-101')).toBeInTheDocument();
        expect(screen.getByText('Fix core memory leak architecture')).toBeInTheDocument();

        const nextButton = screen.getByTestId('history-next-btn');
        await user.click(nextButton);

        expect(mockPageChange).toHaveBeenCalledWith(1, 10);
    });

    it('renders placeholder empty message when content is empty', () => {
        const emptyPage = { content: [], page: { size: 10, number: 0, totalElements: 0, totalPages: 0 } };
        renderWithContext(<HistoryList historyPage={emptyPage} onPageChange={mockPageChange} historyLoading={false} />);
        expect(screen.getByText('No events found in history.')).toBeInTheDocument();
    });
});