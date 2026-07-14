import { describe, it, expect, vi } from 'vitest';
import { apiClient } from './jiradar-api-client';

describe('apiClient Interceptors', () => {
    it('should map response data keys to camelCase', async () => {

        const mockResponse = {
            data: {
                project_key: 'JIRA',
                total_issues_done: 12
            }
        };

        const interceptor = (apiClient.interceptors.response as any).handlers[0].fulfilled;
        const result = interceptor(mockResponse);

        expect(result.data).toEqual({
            projectKey: 'JIRA',
            totalIssuesDone: 12
        });
    });

    it('should log and reject on API error response', async () => {
        const consoleSpy = vi.spyOn(console, 'error').mockImplementation(() => {});
        const mockError = {
            message: 'Network Error',
            response: { data: 'Internal Server Error' }
        };

        const interceptor = (apiClient.interceptors.response as any).handlers[0].rejected;

        await expect(interceptor(mockError)).rejects.toThrow();
        expect(consoleSpy).toHaveBeenCalled();

        consoleSpy.mockRestore();
    });
});