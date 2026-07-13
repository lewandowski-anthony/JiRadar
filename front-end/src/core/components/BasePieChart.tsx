import {
  Chart as ChartJS,
  ArcElement,
  Tooltip,
  Legend
} from 'chart.js';
import { Doughnut, Pie } from 'react-chartjs-2';

ChartJS.register(ArcElement, Tooltip, Legend);

interface BasePieChartProps {
  title: string;
  labels: string[];
  dataValues: number[];
  backgroundColors: string[];
  isDonut?: boolean;
}

export function BasePieChart({ title, labels, dataValues, backgroundColors, isDonut = true }: BasePieChartProps) {
  const options = {
    responsive: true,
    plugins: {
      legend: {
        position: 'right' as const,
        labels: { color: '#94a3b8', boxWidth: 15 }
      },
    },
  };

  const data = {
    labels,
    datasets: [
      {
        data: dataValues,
        backgroundColor: backgroundColors,
        borderColor: '#0f172a',
        borderWidth: 2,
      },
    ],
  };

  const ChartComponent = isDonut ? Doughnut : Pie;

  return (
    <div className="bg-slate-900 p-6 rounded-xl border border-slate-800 max-w-md mx-auto">
      <h2 className="text-xl font-bold text-white mb-4">{title}</h2>
      <div className="max-h-[300px] flex justify-center">
        <ChartComponent options={options} data={data} />
      </div>
    </div>
  );
}
