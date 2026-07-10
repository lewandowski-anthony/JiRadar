import { HttpParams } from '@angular/common/http';

export function buildTrackerParams(projects: string[], startDate?: string, endDate?: string): HttpParams {
  let params = new HttpParams();

  projects.forEach(project => {
    params = params.append('project_keys', project);
  });

  if (startDate) params = params.set('start_date', startDate);
  if (endDate) params = params.set('end_date', endDate);

  return params;
}
