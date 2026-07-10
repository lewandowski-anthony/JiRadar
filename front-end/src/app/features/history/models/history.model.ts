import {User} from '@core/models/user.model';

export interface History {
  issueKey: string;
  issueSummary: string;
  issueType: string;
  date: string;
  transitionType: string;
  issueAssignee: User;
}
