import type { PeriodicUserMetricsDto } from '@core/models/dashboard';
import {parseDurationToHours} from "@core/utils/time.ts";

export interface ChartDatasetConfig {
    label: string;
    borderColor: string;
    backgroundColor: string;
    getValue: (item: PeriodicUserMetricsDto) => number;
}

export interface ChartConfig {
    id: string;
    title: string;
    type: 'line' | 'bar' | 'pie';
    yMax?: number;
    fullWidth?: boolean;
    staticDatasets?: ChartDatasetConfig[];
    getDynamicDatasets?: (data: PeriodicUserMetricsDto[]) => {
        label: string;
        borderColor: string;
        backgroundColor: string;
        data: number[];
    }[];
}

export const CHARTS_REGISTRY: ChartConfig[] = [
    {
        id: 'flow',
        title: "Flux d'Activité (Issues Flow)",
        type: 'line',
        staticDatasets: [
            {
                label: 'Tickets Commencés',
                borderColor: '#3b82f6',
                backgroundColor: 'rgba(59, 130, 246, 0.1)',
                getValue: (item) => item.numberOfIssueStarted,
            },
            {
                label: 'Tickets Terminés',
                borderColor: '#10b981',
                backgroundColor: 'rgba(16, 185, 129, 0.1)',
                getValue: (item) => item.numberOfIssueDone,
            },
        ],
    },
    {
        id: 'lead-times',
        title: 'Délais de Traitement Moyens (en heures)',
        type: 'line',
        staticDatasets: [
            {
                label: 'Temps de Cycle (Cycle Time)',
                borderColor: '#a855f7',
                backgroundColor: 'rgba(168, 85, 247, 0.1)',
                getValue: (item) => parseDurationToHours(item.averageCycleTime),
            },
            {
                label: 'Temps de Revue (Review Time)',
                borderColor: '#f59e0b',
                backgroundColor: 'rgba(245, 158, 11, 0.1)',
                getValue: (item) => parseDurationToHours(item.averageReviewTime),
            },
        ],
    },
    {
        id: 'review-health',
        title: 'Efficacité & Retours de Revue',
        type: 'bar',
        staticDatasets: [
            {
                label: 'Revues Validées',
                borderColor: '#10b981',
                backgroundColor: 'rgba(16, 185, 129, 0.1)',
                getValue: (item) => item.numberOfReviewDone,
            },
            {
                label: 'Retours / Réouvertures',
                borderColor: '#ef4444',
                backgroundColor: 'rgba(239, 68, 68, 0.1)',
                getValue: (item) => item.numberOfReviewReopened,
            },
        ],
    },
    {
        id: 'wip-rate',
        title: 'Tickets en Parallèle Moyen (WIP)',
        type: 'line',
        staticDatasets: [
            {
                label: 'Tickets en Cours Simultanément',
                borderColor: '#3b82f6',
                backgroundColor: 'rgba(59, 130, 246, 0.05)',
                getValue: (item) => item.parallelIssuesInProgressRate,
            },
        ],
    },
    {
        id: 'team-collaboration',
        title: 'Collaboration & Succès des Livraisons (%)',
        type: 'line',
        yMax: 100,
        staticDatasets: [
            {
                label: 'Participation aux Reviews (%)',
                borderColor: '#6366f1',
                backgroundColor: 'rgba(99, 102, 241, 0.05)',
                getValue: (item) => item.teamReviewParticipationRate,
            },
            {
                label: 'Succès des Livraisons (%)',
                borderColor: '#10b981',
                backgroundColor: 'rgba(16, 185, 129, 0.05)',
                getValue: (item) => item.deliverySuccessRate,
            },
        ],
    },
    {
        id: 'types',
        title: 'Répartition des Types de Tickets (%)',
        type: 'bar',
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
];