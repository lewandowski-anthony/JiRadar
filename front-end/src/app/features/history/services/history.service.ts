import { inject, Injectable, signal } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { map, Observable, tap } from 'rxjs';
import { environment } from '@env/environment';
import { buildTrackerParams } from '@core/utils/jiradar-http.util';
import { autoMapSnakeToCamel } from '@core/utils/case-mapper';
import {List} from 'postcss/lib/list';
import {History} from '@features/history/models/history.model';

@Injectable({ providedIn: 'root' })
export class HistoryService {

  private readonly httpClient = inject(HttpClient);
  private readonly historySignal = signal<History[] | null>(null);

  readonly history = this.historySignal.asReadonly();

  getUserHistories(tracker: string, projects: string[], startDate: string, endDate: string): Observable<History[]> {
    const url = `${environment.apiUrl}/api/v1/tracker/${tracker}/users/me/history`;
    const params = buildTrackerParams(projects, startDate, endDate);

    return this.httpClient.get<unknown>(url, {
      params,
      observe: 'response'
    }).pipe(
      tap((response: HttpResponse<unknown>) => {
        const totalCount = response.headers.get('total-elements');
        const pageSize = response.headers.get('page-size');
        const pageNumber = response.headers.get('page-number');

        console.info('Total:', totalCount);
        console.info('Page Size:', pageSize);
        console.info('Page Number:', pageNumber);
        console.info('Tous les headers:', response.headers.keys());
      }),
      map(response => autoMapSnakeToCamel<History[]>(response.body)),
      tap(historyPage => this.historySignal.set(historyPage))
    );
  }
}
