import {User} from '@core/models/user.model';

export interface IssueHistory {
  issueKey: string;
  issueSummary: string;
  issueType: string;
  date: string;
  transitionType: string;
  issueAssignee: User;
}
