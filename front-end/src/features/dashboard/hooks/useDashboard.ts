import { useState } from 'react';
import { apiClient } from '@core/api/jiradar-api-client';
import type { DashboardFilters, UserMetricsDto } from '@core/models/dashboard.ts';
import type { Page } from '@core/models/pages.ts';
import type {UserHistoryEventDto} from '@core/models/history.ts';

export function useDashboard() {
  const [userMetrics, setUserMetrics] = useState<UserMetricsDto | null>(null);
  const [userHistory, setUserHistory] = useState<Page<UserHistoryEventDto> | null>(null);
  const [loading, setLoading] = useState<boolean>(false);
  const [error, setError] = useState<string | null>(null);

  const fetchDashboardData = async (filters: DashboardFilters) => {
    setLoading(true);
    setError(null);

    try {
      const tracker = 'jira';

      const commonParams = Object.fromEntries(
        Object.entries({
          projectKeys: filters.projectKey,
          startDate: filters.startDate,
          endDate: filters.endDate,
        }).filter(([_, value]) => value !== null && value !== undefined && value !== '')
      );

      const metricsParams = {
        ...commonParams,
        ...(filters.granularity?.toUpperCase() !== 'NONE' && { historyGranularity: filters.granularity }),
      };

      const historyParams = {
        ...commonParams,
        page: 0,
        size: 10,
      };

      const [metricsResponse, historyResponse] = await Promise.all([
        apiClient.get<UserMetricsDto>(`/api/v1/tracker/${tracker}/users/me/metrics`, { params: metricsParams }),
        apiClient.get<Page<UserHistoryEventDto>>(`/api/v1/tracker/${tracker}/users/me/history`, { params: historyParams })
      ]);

      const historyContent = historyResponse?.data as unknown as UserHistoryEventDto[];
      const totalPages = parseInt(historyResponse.headers['x-total-pages'] || '1', 10);
      const number = parseInt(historyResponse.headers['x-page-number'] || '0', 10);
      const totalElements = parseInt(historyResponse.headers['x-total-elements'] || '0', 10);
      const size = parseInt(historyResponse.headers['x-page-size'] || '10', 10);

      setUserMetrics(metricsResponse.data);
      setUserHistory({
        content: historyContent,
        page: {
          totalPages,
          number,
          totalElements,
          size
        }
      });

    } catch (err: any) {
      console.error('Erreur lors de la récupération des données:', err);
      setError(err.response?.data?.message || 'Impossible de charger les données du Dashboard.');
    } finally {
      setLoading(false);
    }
  };

  return { userMetrics: userMetrics, history: userHistory, loading, error, fetchDashboardData };
}
