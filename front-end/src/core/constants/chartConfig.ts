import type { ComponentType } from 'react';
import type { PeriodicUserMetricsDto } from '@core/models/dashboard';
import { BaseBarChart } from '@core/components/charts/BaseBarChart';
import { BaseLineChart } from '@core/components/charts/BaseLineChart';
import type { BaseChartProps } from '@core/models/charts/BaseChartProps';
import type { TranslationKeys } from './locales';
import {parseDurationToHours} from "@core/utils/time";

export interface FormattedDataset {
    label: string;
    borderColor?: string;
    backgroundColor?: string;
    borderWidth?: number;
    data: number[];
}

export interface ChartDatasetConfig {
    label: (t: TranslationKeys) => string;
    borderColor: string;
    backgroundColor: string;
    getValue: (item: PeriodicUserMetricsDto) => number;
}

export interface ChartConfig {
    id: string;
    title: (t: TranslationKeys) => string;
    type: 'line' | 'bar';
    ChartComponent: ComponentType<BaseChartProps>;
    yMax?: number;
    fullWidth?: boolean;
    staticDatasets?: ChartDatasetConfig[];
    getDynamicDatasets?: (data: PeriodicUserMetricsDto[], t: TranslationKeys) => FormattedDataset[];
}

export const CHARTS_REGISTRY: ChartConfig[] = [
    {
        id: 'flow',
        title: (t) => t.charts.flowTitle,
        type: 'line',
        ChartComponent: BaseLineChart,
        staticDatasets: [
            {
                label: (t) => t.charts.issuesStarted,
                borderColor: '#3b82f6',
                backgroundColor: 'rgba(59, 130, 246, 0.1)',
                getValue: (item) => item.numberOfIssueStarted,
            },
            {
                label: (t) => t.charts.issuesDone,
                borderColor: '#10b981',
                backgroundColor: 'rgba(16, 185, 129, 0.1)',
                getValue: (item) => item.numberOfIssueDone,
            },
        ],
    },
    {
        id: 'types',
        title: (t) => t.charts.typesTitle,
        type: 'bar',
        ChartComponent: BaseBarChart,
        yMax: 100,
        getDynamicDatasets: (data) => {
            const uniqueTypes = Array.from(
                new Set(data.flatMap((item) => (item.issueRateByType || []).map((t) => t.type)))
            );
            const palette = ['#10b981', '#ef4444', '#a855f7', '#f59e0b', '#3b82f6'];

            return uniqueTypes.map((type, index) => {
                const color = palette[index % palette.length];
                return {
                    label: type,
                    borderColor: color,
                    backgroundColor: `${color}1A`,
                    data: data.map((item) => {
                        const target = (item.issueRateByType || []).find((t) => t.type === type);
                        return target ? target.rate : 0;
                    }),
                };
            });
        },
    },
    {
        id: 'review-health',
        title: (t) => t.charts.reviewHealthTitle,
        type: 'bar',
        ChartComponent: BaseBarChart,
        staticDatasets: [
            {
                label: (t) => t.charts.reviewsDone,
                borderColor: '#10b981',
                backgroundColor: 'rgba(16, 185, 129, 0.1)',
                getValue: (item) => item.numberOfReviewDone,
            },
            {
                label: (t) => t.charts.reviewsReopened,
                borderColor: '#ef4444',
                backgroundColor: 'rgba(239, 68, 68, 0.1)',
                getValue: (item) => item.numberOfReviewReopened,
            },
        ],
    },
    {
        id: 'wip-rate',
        title: (t) => t.charts.wipTitle,
        type: 'line',
        ChartComponent: BaseLineChart,
        staticDatasets: [
            {
                label: (t) => t.charts.concurrentIssues,
                borderColor: '#3b82f6',
                backgroundColor: 'rgba(59, 130, 246, 0.05)',
                getValue: (item) => item.parallelIssuesInProgressRate,
            },
        ],
    },
    {
        id: 'lead-times',
        title: (t) => t.charts.leadTimesTitle,
        type: 'line',
        ChartComponent: BaseLineChart,
        fullWidth: true,
        staticDatasets: [
            {
                label: (t) => t.charts.cycleTime,
                borderColor: '#a855f7',
                backgroundColor: 'rgba(168, 85, 247, 0.1)',
                getValue: (item) => parseDurationToHours(item.averageCycleTime),
            },
            {
                label: (t) => t.charts.reviewTime,
                borderColor: '#f59e0b',
                backgroundColor: 'rgba(245, 158, 11, 0.1)',
                getValue: (item) => parseDurationToHours(item.averageReviewTime),
            },
        ],
    },
    {
        id: 'team-collaboration',
        title: (t) => t.charts.collaborationTitle,
        type: 'line',
        ChartComponent: BaseLineChart,
        yMax: 100,
        fullWidth: true,
        staticDatasets: [
            {
                label: (t) => t.charts.participationRate,
                borderColor: '#6366f1',
                backgroundColor: 'rgba(99, 102, 241, 0.05)',
                getValue: (item) => item.teamReviewParticipationRate,
            },
            {
                label: (t) => t.charts.deliverySuccess,
                borderColor: '#10b981',
                backgroundColor: 'rgba(16, 185, 129, 0.05)',
                getValue: (item) => item.deliverySuccessRate,
            },
        ],
    },
];