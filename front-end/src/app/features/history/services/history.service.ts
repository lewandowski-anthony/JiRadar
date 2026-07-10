import {inject, Service, signal} from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { map, Observable, tap } from 'rxjs';

import { autoMapSnakeToCamel } from '@core/utils/case-mapper';
import { buildTrackerParams } from '@core/utils/jiradar-http.util';
import {IssueHistory} from '@features/history/models/history.model';
import {ApiParams} from '@core/constants/api-params.constant';
import {Page} from '@core/models/page.model';

@Service()
export class HistoryService {

  private readonly httpClient = inject(HttpClient);

  private readonly historySignal = signal<Page<IssueHistory> | null>(null);

  readonly history = this.historySignal.asReadonly();

  getUserHistories(
    tracker: string,
    projects: string[],
    startDate: string,
    endDate: string,
    page: number,
    size: number
  ): Observable<Page<IssueHistory>> {

    const url = `/api/v1/tracker/${tracker}/users/me/history`;

    let params = buildTrackerParams(projects, startDate, endDate);
    params = params.set(ApiParams.PAGINATION.PAGE, page).set(ApiParams.PAGINATION.SIZE, size);

    return this.httpClient.get<IssueHistory[]>(url, {
      params,
      observe: 'response'
    }).pipe(
      map(response  => {
        const parsedBody = autoMapSnakeToCamel<IssueHistory[]>(response.body || []);

        return {
          content: parsedBody,
          number: Number(response.headers.get(ApiParams.HEADERS.PAGE_NUMBER)) || 0,
          size: Number(response.headers.get(ApiParams.HEADERS.PAGE_SIZE)) || 20,
          totalElements: Number(response.headers.get(ApiParams.HEADERS.TOTAL_ELEMENTS)) || 0,
          totalPages: Number(response.headers.get(ApiParams.HEADERS.TOTAL_PAGES)) || 0
        };
      }),
      tap(historyPage => this.historySignal.set(historyPage))
    );
  }
}
