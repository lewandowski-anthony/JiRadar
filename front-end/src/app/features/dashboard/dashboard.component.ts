import { Component, inject, signal } from '@angular/core';
import { FormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';
import { DatePipe } from '@angular/common';

import { MetricsService } from '../metrics/services/metrics.service';
import { HistoryService } from '../history/services/history.service';
import { MetricsDisplayComponent } from '../metrics/components/metrics-display.component';
import { HistoryListComponent } from '../history/components/history-list.component';
import {PeriodicChartsComponent} from '@features/metrics/components/periodic-charts.component';

@Component({
  selector: 'app-dashboard',
  standalone: true,
  imports: [ReactiveFormsModule, DatePipe, MetricsDisplayComponent, HistoryListComponent, PeriodicChartsComponent],
  templateUrl: './dashboard.component.html'
})
export class DashboardComponent {
  private readonly fb = inject(FormBuilder);
  private readonly metricsService = inject(MetricsService);
  private readonly historyService = inject(HistoryService);

  readonly isLoading = signal<boolean>(false);
  readonly errorMessage = signal<string | null>(null);

  readonly activeTab = signal<'global' | 'periodic'>('global');

  readonly metricsData = this.metricsService.userMetrics;
  readonly historyData = this.historyService.history;

  historyPage = 0;
  historySize = 5;

  readonly searchForm = this.fb.group({
    projectKey: ['', [Validators.required, Validators.minLength(2)]],
    startDate: [''],
    endDate: [''],
    granularity: ['']
  });

  loadHistoryPage(page: number): void {
    if (this.searchForm.invalid) return;

    this.historyPage = page;
    const { projectKey, startDate, endDate } = this.searchForm.value;

    this.historyService.getUserHistories('jira', [projectKey!], startDate!, endDate!, this.historyPage, this.historySize).subscribe({
      error: (err) => {
        this.errorMessage.set(err.message || 'Failed to load history');
        this.isLoading.set(false);
      }
    });
  }

  updateHistorySize(size: number): void {
    this.historySize = size;
    this.loadHistoryPage(0);
  }

  loadDashboardData(): void {
    if (this.searchForm.invalid) return;

    this.isLoading.set(true);
    this.errorMessage.set(null);
    this.historyPage = 0;

    const { projectKey, startDate, endDate, granularity } = this.searchForm.value;

    if (!granularity && this.activeTab() === 'periodic') {
      this.activeTab.set('global');
    }

    this.metricsService.getUserMetrics('jira', [projectKey!], startDate!, endDate!, granularity!).subscribe({
      next: () => this.isLoading.set(false),
      error: (err) => {
        this.errorMessage.set(err.message || 'Failed to load metrics');
        this.isLoading.set(false);
      }
    });

    this.historyService.getUserHistories('jira', [projectKey!], startDate!, endDate!, this.historyPage, this.historySize).subscribe({
      error: (err) => {
        this.errorMessage.set(err.message || 'Failed to load history');
        this.isLoading.set(false);
      }
    });
  }
}
