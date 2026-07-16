import { render, screen } from '@testing-library/react';
import userEvent from '@testing-library/user-event';
import { describe, it, expect, vi } from 'vitest';
import Dashboard from './Dashboard';
import { useFetchDashboardDatas } from '../hooks/useFetchDashboardDatas';
import { LocaleProvider } from '@core/context/language/LocaleProvider';
import type { Page } from "@core/models/pages";
import type { UserHistoryEventDto } from "@core/models/history";
import type { UserMetricsDto } from "@core/models/dashboard";

vi.mock('../hooks/useFetchDashboardDatas');

vi.mock('react-chartjs-2', () => ({
    Bar: () => <div data-testid="mock-bar" />,
    Line: () => <div data-testid="mock-line" />
}));

describe('Dashboard Orchestrator Component', () => {
    it('should drive pagination changes and global filter execution loops', async () => {
        const user = userEvent.setup();
        const mockFetchDashboardData = vi.fn();
        const mockFetchHistoryPage = vi.fn();

        vi.mocked(useFetchDashboardDatas).mockReturnValue({
            userMetrics: {
                averageCycleTime: '5h',
                userMetricsByGranularity: []
            } as unknown as UserMetricsDto,
            history: {
                content: [{ issueKey: 'TS-45', date: '2026-07-16', transitionType: 'Done' }],
                page: { totalPages: 3, number: 0, totalElements: 30, size: 10 }
            } as unknown as Page<UserHistoryEventDto>,
            loading: false,
            historyLoading: false,
            error: null,
            fetchDashboardData: mockFetchDashboardData,
            fetchHistoryPage: mockFetchHistoryPage
        });

        render(
            <LocaleProvider>
                <Dashboard />
            </LocaleProvider>
        );

        const projectInput = screen.getByLabelText(/Project Code/i);
        const submitButton = screen.getByRole('button', { name: /Update Dashboard/i });

        await user.type(projectInput, 'PRJ');
        await user.click(submitButton);

        expect(mockFetchDashboardData).toHaveBeenCalledWith(expect.objectContaining({
            projectKey: 'PRJ'
        }));

        const historyTabButton = screen.getByRole('button', { name: /Work History/i });
        await user.click(historyTabButton);

        const nextButton = screen.getByTestId('history-next-btn');
        await user.click(nextButton);

        expect(mockFetchHistoryPage).toHaveBeenCalledWith(
            expect.objectContaining({ projectKey: 'PRJ' }),
            1,
            10
        );
    });
});