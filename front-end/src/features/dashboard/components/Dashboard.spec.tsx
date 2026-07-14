import { render, screen } from '@testing-library/react';
import { describe, it, expect, vi } from 'vitest';
import Dashboard from './Dashboard';
import { useFetchDashboardDatas } from '../hooks/useFetchDashboardDatas';
import { LocaleProvider } from '@core/context/LocaleProvider';

vi.mock('../hooks/useFetchDashboardDatas');
vi.mock('react-chartjs-2', () => ({
    Bar: () => <div data-testid="mock-bar" />,
    Line: () => <div data-testid="mock-line" />
}));

describe('Dashboard Container Component', () => {
    it('should display conditional application layout states correctly', () => {
        // State A: Loading loop active
        vi.mocked(useFetchDashboardDatas).mockReturnValue({
            userMetrics: null,
            history: null,
            loading: true,
            error: null,
            fetchDashboardData: vi.fn()
        });

        const { rerender } = render(
            <LocaleProvider>
                <Dashboard />
            </LocaleProvider>
        );

        // 🌟 CORRECTION: Use getAllByText since "Loading..." appears in the splash AND the button
        const loadingElements = screen.getAllByText('Loading...');
        expect(loadingElements.length).toBeGreaterThanOrEqual(1);

        // State B: Loaded metrics payload injection active
        vi.mocked(useFetchDashboardDatas).mockReturnValue({
            userMetrics: {
                averageCycleTime: '2h',
                userMetricsByGranularity: []
            } as any,
            history: { content: [], page: { totalPages: 1 } } as any,
            loading: false,
            error: 'Server unavailable connection timeout',
            fetchDashboardData: vi.fn()
        });

        rerender(
            <LocaleProvider>
                <Dashboard />
            </LocaleProvider>
        );
        expect(screen.getByText('Server unavailable connection timeout')).toBeInTheDocument();
        expect(screen.getByText('Average Cycle Time')).toBeInTheDocument();
    });
});