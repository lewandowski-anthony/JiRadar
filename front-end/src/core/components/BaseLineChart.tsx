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

export interface LineDataset {
    label: string;
    data: number[];
    borderColor: string;
    backgroundColor: string;
}

interface BaseLineChartProps {
    title: string;
    labels: string[];
    datasets: LineDataset[];
    stepped?: boolean;
    maxWidth?: string;
    yMax?: number;
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
            fill: true,
            tension: stepped ? 0 : 0.3,
            stepped: stepped,
        })),
    };

    return (
        <div className={`bg-slate-900 p-6 rounded-xl border border-slate-800 w-full ${maxWidth} mx-auto`}>
            <h2 className="text-lg font-bold text-white mb-4">{title}</h2>
            <Line options={options} data={data} />
        </div>
    );
}