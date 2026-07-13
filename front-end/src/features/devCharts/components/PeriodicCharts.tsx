import type { PeriodicUserMetricsDto } from '@core/models/dashboard';
import { BaseLineChart } from '@core/components/BaseLineChart.tsx';

interface PeriodicChartsProps {
    granularityData: PeriodicUserMetricsDto[];
}

export function PeriodicCharts({ granularityData }: PeriodicChartsProps) {
    const labels = granularityData.map((item) => item.label);

    const flowDatasets = [
        {
            label: 'Tickets Commencés',
            data: granularityData.map((item) => item.numberOfIssueStarted),
            borderColor: '#3b82f6',
            backgroundColor: 'rgba(59, 130, 246, 0.1)',
        },
        {
            label: 'Tickets Terminés',
            data: granularityData.map((item) => item.numberOfIssueDone),
            borderColor: '#10b981',
            backgroundColor: 'rgba(16, 185, 129, 0.1)',
        },
    ];

    const uniqueTypes = Array.from(
        new Set(
            granularityData.flatMap((item) =>
                (item.issueRateByType || []).map((t) => t.type)
            )
        )
    );

    const typeColorPalette = ['#10b981', '#ef4444', '#a855f7', '#f59e0b', '#3b82f6'];

    const typeDatasets = uniqueTypes.map((type, index) => {
        const color = typeColorPalette[index % typeColorPalette.length];
        return {
            label: type,
            data: granularityData.map((item) => {
                const target = (item.issueRateByType || []).find((t) => t.type === type);
                return target ? target.rate : 0;
            }),
            borderColor: color,
            backgroundColor: `${color}1A`,
        };
    });

    const healthDatasets = [
        {
            label: 'Succès Livraison (%)',
            data: granularityData.map((item) => item.deliverySuccessRate),
            borderColor: '#10b981',
            backgroundColor: 'rgba(16, 185, 129, 0.05)',
        },
        {
            label: 'Ping-Pong Rate (%)',
            data: granularityData.map((item) => item.pingPongReviewRate),
            borderColor: '#ef4444',
            backgroundColor: 'rgba(239, 68, 68, 0.05)',
        },
    ];

    return (
        <div className="grid grid-cols-1 lg:grid-cols-2 gap-6 w-full max-w-7xl mx-auto">
            <BaseLineChart
                title="Flux d'Activité (Issues Flow)"
                labels={labels}
                datasets={flowDatasets}
                maxWidth="max-w-full"
            />
            <BaseLineChart
                title="Répartition des Types de Tickets (%)"
                labels={labels}
                datasets={typeDatasets}
                maxWidth="max-w-full"
                yMax={100}
            />
            <BaseLineChart
                title="Santé du Flux & Qualité (Delivery vs Friction)"
                labels={labels}
                datasets={healthDatasets}
                maxWidth="max-w-full"
                yMax={100}
            />
        </div>
    );
}