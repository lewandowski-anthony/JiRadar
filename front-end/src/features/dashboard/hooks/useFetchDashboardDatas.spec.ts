import { renderHook, waitFor, act } from '@testing-library/react';
import { describe, it, expect, vi, beforeEach } from 'vitest';
import { useFetchDashboardDatas } from './useFetchDashboardDatas';
import { JiradarService } from '@core/services/JiradarService';
import type { UserMetricsDto } from "@core/models/dashboard";
import type { UserHistoryEventDto } from "@core/models/history";
import type { Page } from "@core/models/pages";

vi.mock('@core/services/JiradarService', () => ({
    JiradarService: {
        fetchMetrics: vi.fn(),
        fetchHistory: vi.fn()
    }
}));

describe('useFetchDashboardDatas Hook Framework', () => {
    const mockFilters = {
        projectKey: 'TEST',
        startDate: '2026-01-01',
        endDate: '2026-02-01',
        granularity: 'DAY' as const
    };

    beforeEach(() => {
        vi.clearAllMocks();
    });

    it('should fetch metrics and history page 0 on initialization', async () => {
        const mockMetrics = { numberOfIssueDone: 5 };
        const mockHistory = { content: [], page: { totalPages: 1 } };

        vi.mocked(JiradarService.fetchMetrics).mockResolvedValueOnce(mockMetrics as UserMetricsDto);
        vi.mocked(JiradarService.fetchHistory).mockResolvedValueOnce(mockHistory as unknown as Page<UserHistoryEventDto>);

        const { result } = renderHook(() => useFetchDashboardDatas());

        act(() => {
            result.current.fetchDashboardData(mockFilters);
        });

        expect(result.current.loading).toBe(true);

        await waitFor(() => {
            expect(result.current.loading).toBe(false);
        });

        expect(result.current.userMetrics).toEqual(mockMetrics);
        expect(result.current.history).toEqual(mockHistory);
    });

    it('should handle history page target requests standalone without metrics interaction', async () => {
        const mockHistoryPage = { content: [{ issueKey: 'H-1' }], page: { totalPages: 3, number: 1 } };
        vi.mocked(JiradarService.fetchHistory).mockResolvedValueOnce(mockHistoryPage as unknown as Page<UserHistoryEventDto>);

        const { result } = renderHook(() => useFetchDashboardDatas());

        act(() => {
            result.current.fetchHistoryPage(mockFilters, 1, 10);
        });

        expect(result.current.historyLoading).toBe(true);

        await waitFor(() => {
            expect(result.current.historyLoading).toBe(false);
        });

        expect(result.current.history).toEqual(mockHistoryPage);
        expect(result.current.error).toBeNull();
    });

    it('should catch error allocations on fetchHistoryPage failures', async () => {
        const pageError = { response: { data: { message: 'History Page Timeout Error' } } };
        vi.mocked(JiradarService.fetchHistory).mockRejectedValueOnce(pageError);

        const { result } = renderHook(() => useFetchDashboardDatas());

        act(() => {
            result.current.fetchHistoryPage(mockFilters, 2, 10);
        });

        await waitFor(() => {
            expect(result.current.historyLoading).toBe(false);
        });

        expect(result.current.error).toBe('History Page Timeout Error');
    });

    it('should fallback to default error string on generic hook rejections', async () => {
        vi.mocked(JiradarService.fetchMetrics).mockRejectedValueOnce(new Error('Fatal'));
        const { result } = renderHook(() => useFetchDashboardDatas());

        act(() => {
            result.current.fetchDashboardData(mockFilters);
        });

        await waitFor(() => {
            expect(result.current.loading).toBe(false);
        });
        expect(result.current.error).toBe('Error in fetching dashboard data.');
    });
});