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
import type { PeriodicUserMetricsDto } from '@core/models/dashboard';

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

interface PeriodicChartsProps {
  granularityData: PeriodicUserMetricsDto[];
}

export function PeriodicCharts({ granularityData }: PeriodicChartsProps) {
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
      y: { grid: { color: '#334155' }, ticks: { color: '#94a3b8' } },
    },
  };

  const labels = granularityData.map((item) => item.label);
  const issuesStarted = granularityData.map((item) => item.numberOfIssueStarted);
  const issuesDone = granularityData.map((item) => item.numberOfIssueDone);

  const data = {
    labels,
    datasets: [
      {
        label: 'Tickets Commencés',
        data: issuesStarted,
        borderColor: '#3b82f6',
        backgroundColor: 'rgba(59, 130, 246, 0.1)',
        fill: true,
        tension: 0.3,
      },
      {
        label: 'Tickets Terminés',
        data: issuesDone,
        borderColor: '#10b981',
        backgroundColor: 'rgba(16, 185, 129, 0.1)',
        fill: true,
        tension: 0.3,
      },
    ],
  };

  return (
    <div className="bg-slate-900 p-6 rounded-xl border border-slate-800 max-w-4xl mx-auto">
      <h2 className="text-xl font-bold text-white mb-4">Flux des Tickets (Issues Flow)</h2>
      <Line options={options} data={data} />
    </div>
  );
}
