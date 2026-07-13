import { Component, input } from '@angular/core';
import { CommonModule } from '@angular/common';
import {UserMetrics} from '@features/metrics/models/userMetrics.model';

@Component({
  selector: 'app-metrics-display',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './metrics-display.component.html'
})
export class MetricsDisplayComponent {
  readonly data = input.required<UserMetrics>();
}
