import {
    Chart as ChartJS,
    CategoryScale,
    LinearScale,
    PointElement,
    LineElement,
    Title,
    Tooltip,
    Legend,
    Filler
} from 'chart.js';
import { Line } from 'react-chartjs-2';
import type {BaseChartProps} from "@core/models/charts/BaseChartProps";

ChartJS.register(
    CategoryScale,
    LinearScale,
    PointElement,
    LineElement,
    Title,
    Tooltip,
    Legend,
    Filler
);

interface BaseLineChartProps extends BaseChartProps {
    stepped?: boolean;
}

export function BaseLineChart({
                                  title,
                                  labels,
                                  datasets,
                                  stepped = false,
                                  maxWidth = 'max-w-2xl',
                                  yMax
                              }: BaseLineChartProps) {
    const options = {
        responsive: true,
        maintainAspectRatio: false,
        plugins: {
            legend: {
                position: 'top' as const,
                labels: { color: '#94a3b8' }
            },
        },
        scales: {
            x: { grid: { color: '#334155' }, ticks: { color: '#94a3b8' } },
            y: {
                grid: { color: '#334155' },
                ticks: { color: '#94a3b8' },
                ...(yMax !== undefined ? { max: yMax } : {})
            },
        },
    };

    const data = {
        labels,
        datasets: datasets.map((dataset) => ({
            ...dataset,
            borderColor: dataset.borderColor ?? '#a855f7',
            backgroundColor: dataset.backgroundColor ?? 'rgba(168, 85, 247, 0.2)',
            borderWidth: dataset.borderWidth ?? 1,
            fill: true,
            tension: stepped ? 0 : 0.3,
            stepped: stepped,
        })),
    };

    return (
        <div className={`bg-slate-900 p-4 sm:p-6 rounded-xl border border-slate-800 w-full ${maxWidth} mx-auto`}>
            <h2 className="text-base sm:text-lg font-bold text-white mb-4">{title}</h2>
            <div className="h-[260px] sm:h-[320px] w-full">
                <Line options={options} data={data} />
            </div>
        </div>
    );
}