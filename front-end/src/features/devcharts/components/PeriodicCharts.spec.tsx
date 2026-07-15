import { render, screen } from '@testing-library/react';
import { describe, it, expect, vi } from 'vitest';
import { PeriodicCharts } from './PeriodicCharts';
import { LocaleProvider } from '@core/context/language/LocaleProvider';
import type { PeriodicUserMetricsDto } from "@core/models/dashboard.ts";

vi.mock('react-chartjs-2', () => ({
    Bar: () => <div data-testid="mock-bar" />,
    Line: () => <div data-testid="mock-line" />
}));

describe('PeriodicCharts Component', () => {
    it('should iterate over the charts registry and map datasets securely', () => {
        const mockedMetrics: PeriodicUserMetricsDto[] = [
            {
                label: 'Week 28',
                from: '2026-07-06',
                to: '2026-07-12',
                numberOfIssueStarted: 10,
                numberOfIssueDone: 8,
                averageCycleTime: '2d 4h',
                averageReviewTime: '5h',
                numberOfReviewDone: 4,
                numberOfReviewReopened: 1,
                teamReviewParticipationRate: 75,
                deliverySuccessRate: 90,
                pingPongReviewRate: 15,
                parallelIssuesInProgressRate: 2,
                issueRateByType: [{ type: 'Bug', rate: 40 }]
            }
        ];

        render(
            <LocaleProvider>
                <PeriodicCharts granularityData={mockedMetrics} />
            </LocaleProvider>
        );

        expect(screen.getByText('Activity Flow (Issues Flow)')).toBeInTheDocument();
        expect(screen.getByText('Average Lead Times (Hours)')).toBeInTheDocument();
    });
});