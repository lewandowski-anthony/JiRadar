import type { TranslationKeys } from './locales';
import type {UserMetricsDto} from "@core/models/dashboard";

type AllowedKpiKeys = {
    [K in keyof UserMetricsDto]: UserMetricsDto[K] extends string | number ? K : never;
}[keyof UserMetricsDto];

export interface KpiConfig {
    key: AllowedKpiKeys & keyof TranslationKeys['kpi'];
    color: string;
    borderColor: string;
    format?: (value: string | number | undefined) => string;
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
        format: (val) => (val !== undefined ? `${val}%` : ''),
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
        format: (val) => (val !== undefined ? `${val}%` : ''),
    },
];