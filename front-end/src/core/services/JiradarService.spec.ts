import { describe, it, expect, vi, beforeEach } from 'vitest';
import { JiradarService } from './JiradarService';
import { apiClient } from '@core/api/jiradar-api-client';

vi.mock('@core/api/jiradar-api-client', () => ({
    apiClient: {
        get: vi.fn()
    }
}));

describe('JiradarService', () => {
    const filters = {
        projectKey: 'JIRA',
        startDate: '2026-06-14',
        endDate: '2026-07-14',
        granularity: 'WEEK' as const
    };

    beforeEach(() => {
        vi.clearAllMocks();
    });

    it('fetches metrics data successfully', async () => {
        const mockData = { numberOfIssueDone: 42 };
        vi.mocked(apiClient.get).mockResolvedValueOnce({ data: mockData });

        const result = await JiradarService.fetchMetrics(filters);
        expect(result).toEqual(mockData);
        expect(apiClient.get).toHaveBeenCalledWith(
            '/api/v1/tracker/jira/users/me/metrics',
            expect.objectContaining({
                params: {
                    projectKeys: 'JIRA',
                    startDate: '2026-06-14',
                    endDate: '2026-07-14',
                    historyGranularity: 'WEEK'
                }
            })
        );
    });

    it('fetches history with parsed pagination headers', async () => {
        const mockEvents = [{ issueKey: 'JIRA-1' }];
        vi.mocked(apiClient.get).mockResolvedValueOnce({
            data: mockEvents,
            headers: {
                'total-pages': '5',
                'page-number': '2',
                'total-elements': '45'
            }
        });

        const result = await JiradarService.fetchHistory({ ...filters, page: 10, size: 20 });
        expect(result.content).toEqual(mockEvents);
        expect(result.page.totalPages).toBe(5);
        expect(result.page.number).toBe(2);
        expect(result.page.totalElements).toBe(45);
    });
});