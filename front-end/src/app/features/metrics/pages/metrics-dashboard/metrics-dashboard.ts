import {Component, inject, signal} from '@angular/core';
import { CommonModule, DatePipe, NgClass } from '@angular/common';
import {MetricsService} from '../../services/metricsService';

@Component({
  selector: 'app-metrics-dashboard',
  imports: [CommonModule, DatePipe, NgClass],
  templateUrl: './metrics-dashboard.html'
})
export class MetricsDashboard {

  private metricService: MetricsService = inject(MetricsService);

  isLoading = signal<boolean>(false);
  errorMessage = signal<string | null>(null);
  userMetrics = this.metricService.userMetrics;

  ngOnInit(): void {
    this.loadDashboardData();
  }

  loadDashboardData(): void {
    this.isLoading.set(true);
    this.errorMessage.set(null);

    this.metricService.getUserMetrics(['SMSUP'], '2025-01-01', '2025-07-01')
      .subscribe({
        next: () => {
          this.isLoading.set(false);
        },
        error: (err) => {
          console.error(err);
          this.errorMessage.set('Application KPIs cannot be loaded');
          console.log(err);
          this.isLoading.set(false);
        }
      });
  }
}
