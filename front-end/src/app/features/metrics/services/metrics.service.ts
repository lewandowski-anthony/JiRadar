import {inject, Service, signal} from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { map, Observable, tap } from 'rxjs';
import { environment } from '@env/environment';

import { UserMetrics } from '../models/userMetrics.model';
import { autoMapSnakeToCamel } from '@core/utils/case-mapper';
import { buildTrackerParams } from '@core/utils/jiradar-http.util';

@Service()
export class MetricsService {

  private readonly httpClient: HttpClient = inject(HttpClient);
  private readonly userMetricsSignal = signal<UserMetrics | null>(null);

  readonly userMetrics = this.userMetricsSignal.asReadonly();

  getUserMetrics(tracker: string, projects: string[], startDate: string, endDate: string, granularity: string): Observable<UserMetrics> {
    const url = `${environment.apiUrl}/api/v1/tracker/${tracker}/users/me/metrics`;
    let params = buildTrackerParams(projects, startDate, endDate);
    if (granularity) {
      params = params.set("history_granularity", granularity);
    }

    return this.httpClient.get<UserMetrics>(url, { params }).pipe(
      map(rawJson => autoMapSnakeToCamel<UserMetrics>(rawJson)),
      tap(metrics => this.userMetricsSignal.set(metrics))
    );
  }
}
