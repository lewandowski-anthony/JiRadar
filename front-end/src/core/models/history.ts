import type {User} from '@core/models/user.ts';

export interface UserHistoryEventDto {
  issueKey: string;
  issueSummary: string;
  issueType: string;
  date: string;
  transitionType: string;
  issueAssignee?: User;
}
