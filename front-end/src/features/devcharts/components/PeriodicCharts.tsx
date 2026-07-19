import type { PeriodicUserMetricsDto } from '@core/models/dashboard';
import { CHARTS_REGISTRY, type ChartConfig, type FormattedDataset } from '@core/constants/chartConfig';
import { useTranslation } from '@core/hooks/useTranslation';

interface PeriodicChartsProps {
    granularityData: PeriodicUserMetricsDto[];
}

export function PeriodicCharts({ granularityData }: Readonly<PeriodicChartsProps>) {
    const labels = granularityData.map((item) => item.label);
    const t = useTranslation();

    const renderChart = (chart: ChartConfig) => {

        const datasets: FormattedDataset[] = chart.getDynamicDatasets
            ? chart.getDynamicDatasets(granularityData, t)
            : (chart.staticDatasets || []).map((d) => ({
                label: d.label(t),
                borderColor: d.borderColor,
                backgroundColor: d.backgroundColor,
                data: granularityData.map((item) => d.getValue(item)),
            }));

        const TargetChart = chart.ChartComponent;

        return (
            <TargetChart
                key={chart.id}
                title={chart.title(t)}
                labels={labels}
                yMax={chart.yMax}
                maxWidth="max-w-full"
                datasets={datasets}
            />
        );
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