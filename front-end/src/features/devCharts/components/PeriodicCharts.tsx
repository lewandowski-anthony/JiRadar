import type { PeriodicUserMetricsDto } from '@core/models/dashboard';
import { BaseLineChart } from '@core/components/BaseLineChart.tsx';
import { BaseBarChart } from '@core/components/BaseBarChart.tsx';
import { CHARTS_REGISTRY } from '@core/constants/chartConfig';
import { useTranslation } from '@core/hooks/useTranslation';

interface PeriodicChartsProps {
    granularityData: PeriodicUserMetricsDto[];
}

export function PeriodicCharts({ granularityData }: PeriodicChartsProps) {
    const labels = granularityData.map((item) => item.label);
    const t = useTranslation();

    const renderChart = (chart: any) => {
        const datasets = chart.getDynamicDatasets
            ? chart.getDynamicDatasets(granularityData, t)
            : (chart.staticDatasets || []).map((d: any) => ({
                label: d.label(t),
                borderColor: d.borderColor,
                backgroundColor: d.backgroundColor,
                data: granularityData.map(d.getValue),
            }));

        const props = {
            title: chart.title(t),
            labels,
            yMax: chart.yMax,
            maxWidth: "max-w-full",
            datasets
        };

        switch (chart.type) {
            case 'bar':
                return <BaseBarChart key={chart.id} {...props} />;
            case 'line':
            default:
                return <BaseLineChart key={chart.id} {...props} />;
        }
    };

    return (
        <div className="grid grid-cols-1 lg:grid-cols-2 gap-6 w-full max-w-7xl mx-auto">
            {CHARTS_REGISTRY.filter((c) => !c.fullWidth).map(renderChart)}

            {CHARTS_REGISTRY.filter((c) => c.fullWidth).map((chart) => (
                <div key={chart.id} className="lg:col-span-2">
                    {renderChart(chart)}
                </div>
            ))}
        </div>
    );
}