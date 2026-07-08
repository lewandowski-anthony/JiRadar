import {IssueTypeRate} from './issueTypeRate.model';

export interface UserMetrics {
  from: string;
  to: string;

  averageCycleTime: string;
  averageReviewTime: string;

  numberOfReviewReopened: number;
  numberOfIssueDone: number;
  numberOfIssueStarted: number;
  numberOfReviewDone: number;

  deliverySuccessRate: number;
  teamReviewParticipationRate: number;
  pingPongReviewRate: number;
  parallelIssuesInProgressRate: number;

  issueRateByType: IssueTypeRate[];
}
