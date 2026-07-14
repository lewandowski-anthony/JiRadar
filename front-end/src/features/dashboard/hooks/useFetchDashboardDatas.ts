import { useState } from 'react';
import { JiradarService } from '@core/services/JiradarService';
import type { DashboardFilters, UserMetricsDto } from '@core/models/dashboard';
import type { Page } from '@core/models/pages';
import type { UserHistoryEventDto } from '@core/models/history';

export function useFetchDashboardDatas() {
    const [userMetrics, setUserMetrics] = useState<UserMetricsDto | null>(null);
    const [userHistory, setUserHistory] = useState<Page<UserHistoryEventDto> | null>(null);
    const [loading, setLoading] = useState<boolean>(false);
    const [error, setError] = useState<string | null>(null);

    const fetchDashboardData = async (filters: DashboardFilters) => {
        setLoading(true);
        setError(null);

        try {
            const [metrics, history] = await Promise.all([
                JiradarService.fetchMetrics(filters),
                JiradarService.fetchHistory(filters)
            ]);
            setUserMetrics(metrics);
            setUserHistory(history);
        } catch (err: unknown) {
            console.error(err);
            //TODO : Change the message
            setError((err as { response?: { data?: { message?: string } } })?.response?.data?.message || 'Error in fetching dashboard.');
        } finally {
            setLoading(false);
        }
    };

    return { userMetrics, history: userHistory, loading, error, fetchDashboardData };
}