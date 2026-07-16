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

    async fetchHistory(filters: DashboardFilters & { page?: number; size?: number }, tracker = 'jira'): Promise<Page<UserHistoryEventDto>> {
        const params = {
            projectKeys: filters.projectKey,
            startDate: filters.startDate,
            endDate: filters.endDate,
            page: filters.page ?? 0,
            size: filters.size ?? 20,
        };

        const response = await apiClient.get<UserHistoryEventDto[]>(
            `/api/v1/tracker/${tracker}/users/me/history`,
            { params }
        );

        return {
            content: response.data,
            page: {
                totalPages: parseInt(response.headers['total-pages'] || '1', 10),
                number: parseInt(response.headers['page-number'] || '0', 10),
                totalElements: parseInt(response.headers['total-elements'] || '0', 10),
                size: parseInt(response.headers['page-size'] || '20', 10),
            }
        };
    }
};