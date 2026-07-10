import { Component, inject, signal } from '@angular/core';
import { FormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';
import { DatePipe } from '@angular/common';

import { MetricsService } from '../metrics/services/metrics.service';
import { HistoryService } from '../history/services/history.service';
import { MetricsDisplayComponent } from '../metrics/components/metrics-display.component';
import { HistoryListComponent } from '../history/components/history-list.component';

@Component({
  selector: 'app-dashboard',
  standalone: true,
  imports: [ReactiveFormsModule, DatePipe, MetricsDisplayComponent, HistoryListComponent],
  templateUrl: './dashboard.component.html'
})
export class DashboardComponent {
  private readonly fb = inject(FormBuilder);
  private readonly metricsService = inject(MetricsService);
  private readonly historyService = inject(HistoryService);

  readonly isLoading = signal<boolean>(false);
  readonly errorMessage = signal<string | null>(null);

  readonly metricsData = this.metricsService.userMetrics;
  readonly historyData = this.historyService.history;

  readonly searchForm = this.fb.group({
    projectKey: ['', [Validators.required, Validators.minLength(2)]],
    startDate: [''],
    endDate: [''],
    granularity: ['']
  });

  loadDashboardData(): void {
    if (this.searchForm.invalid) return;

    this.isLoading.set(true);
    this.errorMessage.set(null);

    const { projectKey, startDate, endDate } = this.searchForm.value;

    const projectsArray = [projectKey!];
    const tracker = 'jira';

    this.metricsService.getUserMetrics(tracker, projectsArray, startDate!, endDate!).subscribe({
      next: () => this.isLoading.set(false),
      error: (err) => {
        this.errorMessage.set(err.message || 'Failed to load metrics');
        this.isLoading.set(false);
      }
    });

    this.historyService.getUserHistories(tracker, projectsArray, startDate!, endDate!).subscribe({
      error: (err) => {
        this.errorMessage.set(err.message || 'Failed to load history');
        this.isLoading.set(false);
      }
    });
  }
}
