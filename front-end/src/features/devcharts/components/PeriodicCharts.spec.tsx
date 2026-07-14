import { render, screen } from '@testing-library/react';
import { describe, it, expect, vi } from 'vitest';
import { PeriodicCharts } from './PeriodicCharts';
import { LocaleProvider } from '@core/context/LocaleProvider';

vi.mock('react-chartjs-2', () => ({
    Bar: () => <div data-testid="mock-bar" />,
    Line: () => <div data-testid="mock-line" />
}));

describe('PeriodicCharts Component', () => {
    it('should iterate over the charts registry registry and map datasets securely', () => {
        const mockedMetrics = [
            {
                label: 'Week 28',
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
                <PeriodicCharts granularityData={mockedMetrics as any} />
            </LocaleProvider>
        );

        expect(screen.getByText('Activity Flow (Issues Flow)')).toBeInTheDocument();
        expect(screen.getByText('Average Lead Times (Hours)')).toBeInTheDocument();
    });
});