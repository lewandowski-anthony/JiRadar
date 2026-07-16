import { describe, it, expect, vi } from 'vitest';
import { apiClient } from './jiradar-api-client';
import { getCookie } from '../utils/cookies';

vi.mock('../utils/cookies', () => ({
    getCookie: vi.fn(),
    setCookie: vi.fn()
}));

describe('apiClient Centralized Interceptors Architecture', () => {

    it('should inject Basic Auth header when basic token cookie exists', async () => {
        vi.mocked(getCookie).mockReturnValue('encoded_base64_hash_string');

        const requestInterceptor = (apiClient.interceptors.request as any).handlers[0].fulfilled;

        const mockConfig = { headers: {} };
        const resultConfig = requestInterceptor(mockConfig);

        expect(resultConfig.headers.Authorization).toBe('Basic encoded_base64_hash_string');
    });

    it('should bypass authorization injection if cookie allocation is absent', async () => {
        vi.mocked(getCookie).mockReturnValue(null);

        const requestInterceptor = (apiClient.interceptors.request as any).handlers[0].fulfilled;
        const mockConfig = { headers: {} };
        const resultConfig = requestInterceptor(mockConfig);

        expect(resultConfig.headers.Authorization).toBeUndefined();
    });

    it('should bubble up request rejections unmodified on intercept errors', async () => {
        const errorInterceptor = (apiClient.interceptors.request as any).handlers[0].rejected;
        const mockError = new Error('Interceptor Failure Context');

        await expect(errorInterceptor(mockError)).rejects.toThrow('Interceptor Failure Context');
    });

    it('should map response data keys to camelCase upon successful resolution', async () => {
        const responseInterceptor = (apiClient.interceptors.response as any).handlers[0].fulfilled;

        const mockResponse = {
            data: {
                project_key: 'JIRA',
                total_issues_done: 12
            }
        };

        const result = responseInterceptor(mockResponse);

        expect(result.data).toEqual({
            projectKey: 'JIRA',
            totalIssuesDone: 12
        });
    });

    it('should bypass camelCase mapping if response payload contains no data object', async () => {
        const responseInterceptor = (apiClient.interceptors.response as any).handlers[0].fulfilled;

        const mockResponse = { data: null };
        const result = responseInterceptor(mockResponse);

        expect(result.data).toBeNull();
    });

    it('should log and reject with error payload details upon response intercept rejections', async () => {
        const consoleSpy = vi.spyOn(console, 'error').mockImplementation(() => {});
        const responseErrorInterceptor = (apiClient.interceptors.response as any).handlers[0].rejected;

        const mockError = {
            message: 'Network Error',
            response: { data: 'Internal Server Error' }
        };

        await expect(responseErrorInterceptor(mockError)).rejects.toEqual(mockError);
        expect(consoleSpy).toHaveBeenCalledWith(
            'API Error Intercepted:',
            'Internal Server Error'
        );

        consoleSpy.mockRestore();
    });
});