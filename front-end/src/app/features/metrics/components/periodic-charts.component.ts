import { Component, computed, input } from '@angular/core';
import { BaseChartDirective } from 'ng2-charts';
import { Chart, ChartConfiguration, registerables } from 'chart.js';
import {PeriodicUserMetrics, UserMetrics} from '../models/userMetrics.model';

Chart.register(...registerables);

@Component({
  selector: 'app-periodic-charts',
  standalone: true,
  imports: [BaseChartDirective],
  templateUrl: './periodic-charts.component.html'
})
export class PeriodicChartsComponent {
  readonly userMetrics = input.required<UserMetrics>();

  readonly commonOptions: ChartConfiguration<'bar'>['options'] = {
    responsive: true,
    maintainAspectRatio: false,
    plugins: {
      legend: {
        position: 'bottom',
        labels: { color: '#cbd5e1', boxWidth: 12, font: { size: 11 } }
      }
    },
    scales: {
      x: { grid: { color: '#334155' }, ticks: { color: '#94a3b8', font: { size: 10 } } },
      y: { grid: { color: '#334155' }, ticks: { color: '#94a3b8', font: { size: 10 } } }
    }
  };

  readonly doughnutOptions: ChartConfiguration<'doughnut'>['options'] = {
    responsive: true,
    maintainAspectRatio: false,
    plugins: {
      legend: {
        position: 'bottom',
        labels: { color: '#cbd5e1', boxWidth: 12, font: { size: 11 } }
      }
    }
  };

  readonly issuesData = computed<ChartConfiguration<'bar'>['data']>(() => {
    const periodicMetrics = this.userMetrics().userMetricsByGranularity;
    return {
      labels: periodicMetrics.map(periodicMetric => periodicMetric.label),
      datasets: [
        { data: periodicMetrics.map(periodicMetric => periodicMetric.numberOfIssueStarted), label: 'Started', backgroundColor: 'rgba(59, 130, 246, 0.8)', borderRadius: 4 },
        { data: periodicMetrics.map(periodicMetric => periodicMetric.numberOfIssueDone), label: 'Done', backgroundColor: 'rgba(16, 185, 129, 0.8)', borderRadius: 4 }
      ]
    };
  });

  readonly reviewsData = computed<ChartConfiguration<'bar'>['data']>(() => {
    const periodicMetrics = this.userMetrics().userMetricsByGranularity;
    return {
      labels: periodicMetrics.map(periodicMetric => periodicMetric.label),
      datasets: [
        { data: periodicMetrics.map(periodicMetric => periodicMetric.numberOfReviewDone), label: 'Done', backgroundColor: 'rgba(139, 92, 246, 0.8)', borderRadius: 4 },
        { data: periodicMetrics.map(periodicMetric => periodicMetric.numberOfReviewReopened), label: 'Reopened', backgroundColor: 'rgba(239, 68, 68, 0.8)', borderRadius: 4 }
      ]
    };
  });

  readonly ratesData = computed<ChartConfiguration<'bar'>['data']>(() => {
    const periodicMetrics = this.userMetrics().userMetricsByGranularity;
    return {
      labels: periodicMetrics.map(periodicMetric => periodicMetric.label),
      datasets: [
        { data: periodicMetrics.map(periodicMetric => periodicMetric.deliverySuccessRate), label: 'Delivery %', backgroundColor: 'rgba(234, 179, 8, 0.8)', borderRadius: 4 },
        { data: periodicMetrics.map(periodicMetric => periodicMetric.teamReviewParticipationRate), label: 'Review %', backgroundColor: 'rgba(236, 72, 153, 0.8)', borderRadius: 4 }
      ]
    };
  });

  readonly typeDistributionData = computed<ChartConfiguration<'doughnut'>['data']>(() => {
    const data = this.userMetrics();
    if (data.issueRateByType.length === 0) return { labels: [], datasets: [] };

    const distribution = data.issueRateByType || [];

    return {
      labels: distribution.map(item => item.type),
      datasets: [
        {
          data: distribution.map(item => item.rate),
          backgroundColor: [
            'rgba(59, 130, 246, 0.8)',
            'rgba(16, 185, 129, 0.8)',
            'rgba(239, 68, 68, 0.8)',
            'rgba(234, 179, 8, 0.8)',
            'rgba(139, 92, 246, 0.8)'
          ],
          borderWidth: 1,
          borderColor: '#1e293b'
        }
      ]
    };
  });
}
