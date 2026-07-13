import {
    Chart as ChartJS,
    CategoryScale,
    LinearScale,
    BarElement,
    Title,
    Tooltip,
    Legend
} from 'chart.js';
import { Bar } from 'react-chartjs-2';

ChartJS.register(
    CategoryScale,
    LinearScale,
    BarElement,
    Title,
    Tooltip,
    Legend
);

export interface BarDataset {
    label: string;
    data: number[];
    borderColor: string;
    backgroundColor: string;
}

interface BaseBarChartProps {
    title: string;
    labels: string[];
    datasets: BarDataset[];
    maxWidth?: string;
    yMax?: number;
}

export function BaseBarChart({
                                 title,
                                 labels,
                                 datasets,
                                 maxWidth = 'max-w-2xl',
                                 yMax
                             }: BaseBarChartProps) {
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
            x: { grid: { display: false }, ticks: { color: '#94a3b8' } },
            y: {
                grid: { color: '#334155', drawTicks: false },
                ticks: { color: '#94a3b8' },
                border: { dash: [4, 4] },
                ...(yMax !== undefined ? { max: yMax } : {})
            },
        },
    };

    const data = {
        labels,
        datasets: datasets.map((dataset) => ({
            ...dataset,
            borderRadius: 4,
            borderWidth: 1,
            barPercentage: 0.7,
            categoryPercentage: 0.8,
        })),
    };

    return (
        <div className={`bg-slate-900 p-4 sm:p-6 rounded-xl border border-slate-800 w-full ${maxWidth} mx-auto`}>
            <h2 className="text-base sm:text-lg font-bold text-white mb-4">{title}</h2>
            <div className="h-[260px] sm:h-[300px] w-full">
                <Bar options={options} data={data} />
            </div>
        </div>
    );
}