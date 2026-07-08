import {Component, inject, signal} from '@angular/core';
import { CommonModule, DatePipe, NgClass } from '@angular/common';
import {MetricsService} from '../../services/metrics-service';
import {FormBuilder, FormGroup, ReactiveFormsModule, Validators} from '@angular/forms';

@Component({
  selector: 'app-metrics-dashboard',
  imports: [CommonModule, DatePipe, NgClass, ReactiveFormsModule],
  templateUrl: './metrics-dashboard.html'
})
export class MetricsDashboard {

  private metricService: MetricsService = inject(MetricsService);
  private readonly formBuilder = inject(FormBuilder);

  isLoading = signal<boolean>(false);
  errorMessage = signal<string | null>(null);
  userMetrics = this.metricService.userMetrics;

  searchForm!: FormGroup;

  ngOnInit(): void {
    this.initForm();
  }

  initForm(): void {
    this.searchForm = this.formBuilder.group({
      projectKey: ['', [Validators.required, Validators.minLength(2)]],
      startDate: ['', []],
      endDate: ['', []],
      granularity: ['', []],
    });
  }

  loadDashboardData(): void {
    this.isLoading.set(true);
    this.errorMessage.set(null);
    const { projectKey: projectKeys, startDate, endDate } = this.searchForm.value;
    this.metricService.getUserMetrics([projectKeys], startDate, endDate)
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
