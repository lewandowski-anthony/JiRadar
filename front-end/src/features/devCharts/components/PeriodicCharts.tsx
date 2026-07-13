import type { PeriodicUserMetricsDto } from '@core/models/dashboard';
import { BaseLineChart } from '@core/components/BaseLineChart.tsx';
import { BaseBarChart } from '@core/components/BaseBarChart.tsx';
import { CHARTS_REGISTRY } from '@core/constants/chartConfig';

interface PeriodicChartsProps {
    granularityData: PeriodicUserMetricsDto[];
}

export function PeriodicCharts({ granularityData }: PeriodicChartsProps) {
    const labels = granularityData.map((item) => item.label);

    const renderChart = (chart: typeof CHARTS_REGISTRY[0]) => {
        const datasets = chart.getDynamicDatasets
            ? chart.getDynamicDatasets(granularityData)
            : (chart.staticDatasets || []).map((d) => ({
                label: d.label,
                borderColor: d.borderColor,
                backgroundColor: d.backgroundColor,
                data: granularityData.map(d.getValue),
            }));

        const props = {
            key: chart.id,
            title: chart.title,
            labels,
            yMax: chart.yMax,
            maxWidth: "max-w-full",
            datasets
        };

        switch (chart.type) {
            case 'bar':
                return <BaseBarChart {...props} />;
            case 'line':
            default:
                return <BaseLineChart {...props} />;
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