export type Granularity = 'NONE' | 'DAY' | 'WEEK' | 'MONTH' | 'YEAR';

export interface DashboardFilters {
  projectKey: string;
  startDate: string;
  endDate: string;
  granularity: Granularity;
}

export interface IssueRateByTypeDto {
  type: string;
  rate: number;
}

export interface BaseMetrics {
  from: string;
  to: string;
  numberOfIssueStarted: number;
  numberOfIssueDone: number;
  averageCycleTime: string;
  averageReviewTime: string;
  numberOfReviewDone: number;
  numberOfReviewReopened: number;
  teamReviewParticipationRate: number;
  deliverySuccessRate: number;
  pingPongReviewRate: number;
  parallelIssuesInProgressRate: number;
  issueRateByType: IssueRateByTypeDto[];
}

export interface PeriodicUserMetricsDto extends BaseMetrics {
  label: string;
}

export interface UserMetricsDto extends BaseMetrics {
  userMetricsByGranularity: PeriodicUserMetricsDto[];
}
