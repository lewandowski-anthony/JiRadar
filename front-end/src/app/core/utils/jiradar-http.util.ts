import { HttpParams } from '@angular/common/http';
import {ApiParams} from '@core/constants/api-params.constant';

export function buildTrackerParams(projects: string[], startDate?: string, endDate?: string): HttpParams {
  let params = new HttpParams();

  projects.forEach(project => {
    params = params.append(ApiParams.TRACKER.PROJECT_KEYS, project);
  });

  if (startDate) params = params.set(ApiParams.TRACKER.START_DATE, startDate);
  if (endDate) params = params.set(ApiParams.TRACKER.END_DATE, endDate);

  return params;
}
