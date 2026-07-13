import type { TranslationKeys } from './locales';

export interface KpiConfig {
    key: keyof TranslationKeys['kpi'];
    color: string;
    borderColor: string;
    format?: (value: any) => string;
}

export const KPI_CONFIGS: KpiConfig[] = [
    {
        key: 'averageCycleTime',
        color: 'text-blue-400',
        borderColor: 'border-blue-500/20',
    },
    {
        key: 'averageReviewTime',
        color: 'text-purple-400',
        borderColor: 'border-purple-500/20',
    },
    {
        key: 'deliverySuccessRate',
        color: 'text-emerald-400',
        borderColor: 'border-emerald-500/20',
        format: (val) => `${val}%`,
    },
    {
        key: 'numberOfIssueDone',
        color: 'text-emerald-400',
        borderColor: 'border-emerald-500/20',
    },
    {
        key: 'numberOfIssueStarted',
        color: 'text-emerald-400',
        borderColor: 'border-emerald-500/20',
    },
    {
        key: 'numberOfReviewReopened',
        color: 'text-red-400',
        borderColor: 'border-red-500/20',
    },
    {
        key: 'parallelIssuesInProgressRate',
        color: 'text-amber-400',
        borderColor: 'border-amber-500/20',
    },
    {
        key: 'pingPongReviewRate',
        color: 'text-red-400',
        borderColor: 'border-red-500/20',
        format: (val) => `${val}%`,
    },
];