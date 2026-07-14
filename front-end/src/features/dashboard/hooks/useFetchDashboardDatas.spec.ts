import { renderHook, waitFor, act } from '@testing-library/react';
import { describe, it, expect, vi, beforeEach } from 'vitest';
import { useFetchDashboardDatas } from './useFetchDashboardDatas';
import { JiradarService } from '@core/services/JiradarService';
import type {UserMetricsDto} from "@core/models/dashboard.ts";
import type {UserHistoryEventDto} from "@core/models/history.ts";
import type {Page} from "@core/models/pages";

vi.mock('@core/services/JiradarService', () => ({
    JiradarService: {
        fetchMetrics: vi.fn(),
        fetchHistory: vi.fn()
    }
}));

describe('useFetchDashboardDatas Hook', () => {
    const mockFilters = {
        projectKey: 'TEST',
        startDate: '2026-01-01',
        endDate: '2026-02-01',
        granularity: 'DAY' as const
    };

    beforeEach(() => {
        vi.clearAllMocks();
    });

    it('should fetch data successfully and update states', async () => {
        const mockMetrics = { numberOfIssueDone: 5 };
        const mockHistory = { content: [], page: { totalPages: 1 } };

        vi.mocked(JiradarService.fetchMetrics).mockResolvedValueOnce(mockMetrics as UserMetricsDto);
        vi.mocked(JiradarService.fetchHistory).mockResolvedValueOnce(mockHistory as unknown as Page<UserHistoryEventDto>);

        const { result } = renderHook(() => useFetchDashboardDatas());

        expect(result.current.loading).toBe(false);

        act(() => {
            result.current.fetchDashboardData(mockFilters);
        });

        expect(result.current.loading).toBe(true);

        await waitFor(() => {
            expect(result.current.loading).toBe(false);
        });

        expect(result.current.userMetrics).toEqual(mockMetrics);
        expect(result.current.history).toEqual(mockHistory);
        expect(result.current.error).toBeNull();
    });

    it('should catch API errors and set the error message state', async () => {
        const apiError = { response: { data: { message: 'Jira API Offline' } } };
        vi.mocked(JiradarService.fetchMetrics).mockRejectedValueOnce(apiError);
        vi.mocked(JiradarService.fetchHistory).mockResolvedValueOnce({} as unknown as Page<UserHistoryEventDto>);

        const { result } = renderHook(() => useFetchDashboardDatas());

        act(() => {
            result.current.fetchDashboardData(mockFilters);
        });

        await waitFor(() => {
            expect(result.current.loading).toBe(false);
        });

        expect(result.current.error).toBe('Jira API Offline');
        expect(result.current.userMetrics).toBeNull();
    });
});