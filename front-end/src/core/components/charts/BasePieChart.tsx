import {
    Chart as ChartJS,
    ArcElement,
    Tooltip,
    Legend
} from 'chart.js';
import { Doughnut, Pie } from 'react-chartjs-2';
import type { BaseChartProps } from "@core/models/charts/BaseChartProps";
import { getThemeColor } from '@core/utils/theme-resolver';

ChartJS.register(ArcElement, Tooltip, Legend);

interface BasePieChartProps extends BaseChartProps {
    isDonut?: boolean;
}

export function BasePieChart({ title, labels, datasets, isDonut = true }: BasePieChartProps) {

    const textColor = getThemeColor('--color-text-muted');
    const cardBgColor = getThemeColor('--color-cardbg');

    const options = {
        responsive: true,
        plugins: {
            legend: {
                position: 'right' as const,
                labels: { color: textColor, boxWidth: 15 }
            },
        },
    };

    const data = {
        labels,
        datasets: datasets.map((dataset) => ({
            ...dataset,
            borderColor: dataset.borderColor ?? cardBgColor,
            borderWidth: dataset.borderWidth ?? 2,
        })),
    };

    const ChartComponent = isDonut ? Doughnut : Pie;

    return (
        <div className="bg-cardbg p-6 rounded-xl border border-border-subtle max-w-md mx-auto transition-colors">
            <h2 className="text-xl font-bold text-text-main mb-4">{title}</h2>
            <div className="max-h-[300px] flex justify-center">
                <ChartComponent options={options} data={data} />
            </div>
        </div>
    );
}