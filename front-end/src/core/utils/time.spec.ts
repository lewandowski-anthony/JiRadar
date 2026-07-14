import { describe, it, expect, vi, beforeEach, afterEach } from 'vitest';
import { getPastMonthDateStr, getCurrentDateStr, parseDurationToHours } from './time';

describe('Time Utils', () => {
    beforeEach(() => {
        vi.useFakeTimers();
        vi.setSystemTime(new Date('2026-07-14T12:00:00.000Z'));
    });

    afterEach(() => {
        vi.useRealTimers();
    });

    it('should return correct past month date', () => {
        expect(getPastMonthDateStr()).toBe('2026-06-14');
    });

    it('should return correct current date', () => {
        expect(getCurrentDateStr()).toBe('2026-07-14');
    });

    describe('parseDurationToHours', () => {
        it('should return 0 for invalid inputs', () => {
            expect(parseDurationToHours('')).toBe(0);
            expect(parseDurationToHours('0m')).toBe(0);
        });

        it('should parse days correctly', () => {
            expect(parseDurationToHours('1d')).toBe(24);
        });

        it('should parse hours correctly', () => {
            expect(parseDurationToHours('5h')).toBe(5);
        });

        it('should parse minutes correctly', () => {
            expect(parseDurationToHours('30m')).toBe(0.5);
        });

        it('should parse complete combinations correctly', () => {
            expect(parseDurationToHours('1d 2h 15m')).toBe(26.3);
        });
    });
});