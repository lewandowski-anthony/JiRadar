import {
  Chart as ChartJS,
  CategoryScale,
  LinearScale,
  BarElement,
  Title,
  Tooltip,
  Legend,
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
  backgroundColor: string;
  borderColor?: string;
  borderWidth?: number;
}

interface BaseBarChartProps {
  title: string;
  labels: string[];
  datasets: BarDataset[];
  horizontal?: boolean;
}

export function BaseBarChart({ title, labels, datasets, horizontal = false }: BaseBarChartProps) {
  const options = {
    responsive: true,
    indexAxis: horizontal ? ('y' as const) : ('x' as const),
    plugins: {
      legend: {
        position: 'top' as const,
        labels: { color: '#94a3b8' }
      },
    },
    scales: {
      x: { grid: { color: '#334155' }, ticks: { color: '#94a3b8' } },
      y: { grid: { color: '#334155' }, ticks: { color: '#94a3b8' } },
    },
  };

  return (
    <div className="bg-slate-900 p-6 rounded-xl border border-slate-800 max-w-4xl mx-auto">
      <h2 className="text-xl font-bold text-white mb-4">{title}</h2>
      <Bar options={options} data={{ labels, datasets }} />
    </div>
  );
}
