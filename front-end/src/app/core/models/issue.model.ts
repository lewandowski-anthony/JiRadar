import {ChangeLog} from './changelog.model';

export interface Issue {
  id: string;
  key: string;
  summary: string;
  status: string;
  created: string;
  updated: string;
  changelog: ChangeLog[];
}
