import { Component, input, output } from '@angular/core';
import { CommonModule } from '@angular/common';
import {IssueHistory} from '@features/history/models/history.model';

@Component({
  selector: 'app-history-list',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './history-list.component.html'
})
export class HistoryListComponent {
  readonly data = input.required<{
    content: IssueHistory[];
    number: number;
    size: number;
    totalElements: number;
    totalPages: number;
  }>();

  readonly pageChange = output<number>();
  readonly sizeChange = output<number>();

  onPageChange(newPage: number): void {
    this.pageChange.emit(newPage);
  }

  onSizeChange(event: Event): void {
    const selectElement = event.target as HTMLSelectElement;
    const newSize = Number(selectElement.value);

    this.sizeChange.emit(newSize);

    this.pageChange.emit(0);
  }
}
