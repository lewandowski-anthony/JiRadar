import { apiClient } from '@core/api/jiradar-api-client';
import type { DashboardFilters, UserMetricsDto } from '@core/models/dashboard';
import type { UserHistoryEventDto } from '@core/models/history';
import type { Page } from '@core/models/pages';
import type {User} from "@core/models/user.ts";

export const JiradarService = {
    async getMyAccount(tracker = 'jira'): Promise<User> {

        const response = await apiClient.get<User>(
            `/api/v1/tracker/${tracker}/users/me`
        );
        return response.data;
    },

    async fetchMetrics(filters: DashboardFilters, tracker = 'jira'): Promise<UserMetricsDto> {
        const params = {
            projectKeys: filters.projectKey,
            startDate: filters.startDate,
            endDate: filters.endDate,
            ...(filters.granularity?.toUpperCase() !== 'NONE' && { historyGranularity: filters.granularity }),
        };

        const response = await apiClient.get<UserMetricsDto>(
            `/api/v1/tracker/${tracker}/users/me/metrics`,
            { params }
        );
        return response.data;
    },

    async fetchHistory(filters: DashboardFilters, tracker = 'jira', page = 0, size = 10): Promise<Page<UserHistoryEventDto>> {
        const params = {
            projectKeys: filters.projectKey,
            startDate: filters.startDate,
            endDate: filters.endDate,
            page,
            size,
        };

        const response = await apiClient.get<UserHistoryEventDto[]>(
            `/api/v1/tracker/${tracker}/users/me/history`,
            { params }
        );

        return {
            content: response.data,
            page: {
                totalPages: parseInt(response.headers['x-total-pages'] || '1', 10),
                number: parseInt(response.headers['x-page-number'] || '0', 10),
                totalElements: parseInt(response.headers['x-total-elements'] || '0', 10),
                size,
            }
        };
    }
};