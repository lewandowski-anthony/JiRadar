import { Component, input, output } from '@angular/core';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-history-list',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './history-list.component.html'
})
export class HistoryListComponent {
  readonly data = input<any>();

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
