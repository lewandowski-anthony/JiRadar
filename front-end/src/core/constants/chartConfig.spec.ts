import { describe, it, expect } from 'vitest';
import { CHARTS_REGISTRY } from './chartConfig';
import type { PeriodicUserMetricsDto } from '@core/models/dashboard';
import type { TranslationKeys } from './locales';

const mockTranslation = {
    charts: {
        flowTitle: 'Flow',
        issuesStarted: 'Started',
        issuesDone: 'Done',
        typesTitle: 'Types',
        reviewHealthTitle: 'Health',
        reviewsDone: 'Reviews Done',
        reviewsReopened: 'Reopened',
        wipTitle: 'WIP',
        concurrentIssues: 'Concurrent',
        leadTimesTitle: 'Lead Times',
        cycleTime: 'Cycle',
        reviewTime: 'Review',
        collaborationTitle: 'Collaboration',
        participationRate: 'Participation',
        deliverySuccess: 'Success'
    }
} as unknown as TranslationKeys;

describe('CHARTS_REGISTRY Branch Coverage Suite', () => {
    it('should securely process static datasets titles and metric extraction values', () => {

        CHARTS_REGISTRY.forEach((chart) => {
            expect(chart.title(mockTranslation)).toBeDefined();

            if (chart.staticDatasets) {
                chart.staticDatasets.forEach((dataset) => {
                    expect(dataset.label(mockTranslation)).toBeDefined();
                    const dummyItem = {
                        numberOfIssueStarted: 10,
                        numberOfIssueDone: 8,
                        numberOfReviewDone: 5,
                        numberOfReviewReopened: 2,
                        parallelIssuesInProgressRate: 3,
                        averageCycleTime: '1d 4h',
                        averageReviewTime: '2h',
                        teamReviewParticipationRate: 85,
                        deliverySuccessRate: 95
                    } as unknown as PeriodicUserMetricsDto;

                    expect(dataset.getValue(dummyItem)).toBeDefined();
                });
            }
        });
    });

    it('should secure the dynamic "types" registry chart mapping when payload is ideal', () => {
        const targetChart = CHARTS_REGISTRY.find((c) => c.id === 'types');
        expect(targetChart).toBeDefined();
        expect(targetChart?.getDynamicDatasets).toBeDefined();

        const mockData: PeriodicUserMetricsDto[] = [
            {
                label: 'Week 1',
                issueRateByType: [
                    { type: 'Bug', rate: 40 },
                    { type: 'Task', rate: 60 }
                ]
            } as unknown as PeriodicUserMetricsDto
        ];

        const result = targetChart!.getDynamicDatasets!(mockData, mockTranslation);

        expect(result).toHaveLength(2);
        expect(result[0]).toEqual({
            label: 'Bug',
            borderColor: '#10b981',
            backgroundColor: '#10b9811A',
            data: [40]
        });
    });

    it('should cover fallback branches when issueRateByType is null or target key missing', () => {
        const targetChart = CHARTS_REGISTRY.find((c) => c.id === 'types');

        const mockDataWithFallbacks: PeriodicUserMetricsDto[] = [
            {
                label: 'Week 1',
                issueRateByType: [
                    { type: 'Bug', rate: 30 }
                ]
            } as unknown as PeriodicUserMetricsDto,
            {
                label: 'Week 2',
                issueRateByType: undefined
            } as unknown as PeriodicUserMetricsDto
        ];

        const result = targetChart!.getDynamicDatasets!(mockDataWithFallbacks, mockTranslation);

        expect(result).toHaveLength(1);
        expect(result[0].label).toBe('Bug');
        expect(result[0].data).toEqual([30, 0]);
    });
});