import { Component, input } from '@angular/core';
import {History} from '@features/history/models/history.model';
import {DatePipe, JsonPipe, NgClass} from '@angular/common';

@Component({
  selector: 'app-history-list',
  standalone: true,
  imports: [
    NgClass,
    DatePipe
  ],
  templateUrl: './history-list.component.html'
})
export class HistoryListComponent {
  readonly data = input<History[]>();
}
