import { describe, it, expect, beforeEach } from 'vitest';
import { getCookie, setCookie } from './cookies';

describe('Cookies Utils', () => {
    beforeEach(() => {
        document.cookie = '';
    });

    it('should return null if cookie is not found', () => {
        expect(getCookie('non_existent')).toBeNull();
    });

    it('should set and get a cookie correctly', () => {
        setCookie('jiradar_locale', 'fr', 365);
        expect(getCookie('jiradar_locale')).toBe('fr');
    });

    it('should handle clean values stripping quotes', () => {
        document.cookie = 'test_cookie="hello_world"';
        expect(getCookie('test_cookie')).toBe('hello_world');
    });
});