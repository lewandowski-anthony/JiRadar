import {inject, Service, signal} from '@angular/core';
import {HttpClient, HttpParams} from '@angular/common/http';
import {UserMetrics} from '../models/userMetrics.model';
import {map, Observable, tap} from 'rxjs';

import { environment } from '@env/environment';
import {autoMapSnakeToCamel} from '../../../core/utils/case-mapper';

@Service()
export class MetricsService {

  private readonly httpClient: HttpClient = inject(HttpClient);
  private readonly userMetricsSignal = signal<UserMetrics | null>(null);
  private readonly backEndpoint = `${environment.apiUrl}/api/v1/tracker/jira/users/myself/metrics`;

  readonly userMetrics = this.userMetricsSignal.asReadonly();

  getUserMetrics(projects: string[], startDate: string, endDate: string): Observable<UserMetrics> {

    let httpParams = new HttpParams();
    projects.forEach(project => {
      httpParams = httpParams.append('projects_key', project);
    });
    httpParams = httpParams.set('start_date', startDate);
    httpParams = httpParams.set('end_date', endDate);

    return this.httpClient.get<unknown>(this.backEndpoint, { params: httpParams }).pipe(
      map(rawJson => autoMapSnakeToCamel<UserMetrics>(rawJson)),
      tap(metrics => this.userMetricsSignal.set(metrics))
    );
  }
}
