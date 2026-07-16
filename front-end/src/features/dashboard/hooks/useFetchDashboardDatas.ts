import { useState } from 'react';
import { JiradarService } from '@core/services/JiradarService';
import type { DashboardFilters, UserMetricsDto } from '@core/models/dashboard';
import type { Page } from '@core/models/pages';
import type { UserHistoryEventDto } from '@core/models/history';

export function useFetchDashboardDatas() {
    const [userMetrics, setUserMetrics] = useState<UserMetricsDto | null>(null);
    const [userHistory, setUserHistory] = useState<Page<UserHistoryEventDto> | null>(null);
    const [loading, setLoading] = useState<boolean>(false);
    const [historyLoading, setHistoryLoading] = useState<boolean>(false);
    const [error, setError] = useState<string | null>(null);

    const fetchDashboardData = async (filters: DashboardFilters) => {
        setLoading(true);
        setError(null);

        try {
            const [metrics, history] = await Promise.all([
                JiradarService.fetchMetrics(filters),
                JiradarService.fetchHistory({ ...filters, page: 0 }) // Force page 0 à la soumission
            ]);
            setUserMetrics(metrics);
            setUserHistory(history);
        } catch (err: unknown) {
            console.error(err);
            setError((err as { response?: { data?: { message?: string } } })?.response?.data?.message || 'Error in fetching dashboard data.');
        } finally {
            setLoading(false);
        }
    };

    const fetchHistoryPage = async (filters: DashboardFilters, page: number, size: number) => {
        setHistoryLoading(true);
        try {
            const history = await JiradarService.fetchHistory({ ...filters, page, size });
            setUserHistory(history);
        } catch (err: unknown) {
            console.error(err);
            setError((err as { response?: { data?: { message?: string } } })?.response?.data?.message || 'Error in fetching history page.');
        } finally {
            setHistoryLoading(false);
        }
    };

    return {
        userMetrics,
        history: userHistory,
        loading,
        historyLoading,
        error,
        fetchDashboardData,
        fetchHistoryPage
    };
}