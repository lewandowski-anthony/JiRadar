import type {User} from '@core/models/user';

export interface UserHistoryEventDto {
  issueKey: string;
  issueSummary: string;
  issueType: string;
  date: string;
  transitionType: string;
  issueAssignee?: User;
}
