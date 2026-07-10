import { inject, Injectable, signal } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { map, Observable, tap } from 'rxjs';
import { environment } from '@env/environment';

import { autoMapSnakeToCamel } from '@core/utils/case-mapper';
import { buildTrackerParams } from '@core/utils/jiradar-http.util';
import { IssueHistory } from '@features/history/models/history.model';

export interface IssueHistoryPage {
  content: IssueHistory[];
  number: number;
  size: number;
  totalElements: number;
  totalPages: number;
}

@Injectable({ providedIn: 'root' })
export class HistoryService {
  private readonly httpClient = inject(HttpClient);

  private readonly historySignal = signal<IssueHistoryPage | null>(null);

  readonly history = this.historySignal.asReadonly();

  getUserHistories(
    tracker: string,
    projects: string[],
    startDate: string,
    endDate: string,
    page: number,
    size: number
  ): Observable<IssueHistoryPage> {
    const url = `${environment.apiUrl}/api/v1/tracker/${tracker}/users/me/history`;

    let params = buildTrackerParams(projects, startDate, endDate);
    params = params.set('page', page).set('size', size);

    return this.httpClient.get<IssueHistory[]>(url, {
      params,
      observe: 'response'
    }).pipe(
      map(response  => {
        const parsedBody = autoMapSnakeToCamel<IssueHistory[]>(response.body || []);

        return {
          content: parsedBody,
          number: Number(response.headers.get('page-number')) || 0,
          size: Number(response.headers.get('page-size')) || 20,
          totalElements: Number(response.headers.get('total-elements')) || 0,
          totalPages: Number(response.headers.get('total-pages')) || 0
        };
      }),
      tap(historyPage => this.historySignal.set(historyPage))
    );
  }
}
