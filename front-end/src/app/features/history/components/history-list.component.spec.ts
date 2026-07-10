import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HistoryListComponent } from './history-list.component';
import { IssueHistory } from '@features/history/models/history.model';

describe('HistoryListComponent', () => {
  let component: HistoryListComponent;
  let fixture: ComponentFixture<HistoryListComponent>;

  const mockHistoryData = {
    content: [
      {
        issueKey: 'JIRA-123',
        issueSummary: 'Test Summary',
        issueType: 'Story',
        date: '2026-07-10',
        transitionType: 'In Progress',
        issueAssignee: { id: '1', name: 'Alex', email: 'alex@test.com', avatarUrl: '' }
      }
    ] as IssueHistory[],
    number: 1,
    size: 5,
    totalElements: 10,
    totalPages: 2
  };

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [HistoryListComponent]
    }).compileComponents();

    fixture = TestBed.createComponent(HistoryListComponent);
    component = fixture.componentInstance;
  });

  it('should create the component', () => {
    fixture.componentRef.setInput('data', mockHistoryData);
    fixture.detectChanges();
    expect(component).toBeTruthy();
  });

  it('should emit pageChange when onPageChange is called', () => {
    fixture.componentRef.setInput('data', mockHistoryData);
    fixture.detectChanges();

    let emittedPage: number | undefined;
    component.pageChange.subscribe(page => emittedPage = page);

    component.onPageChange(2);

    expect(emittedPage).toBe(2);
  });

  it('should emit sizeChange and reset page to 0 when onSizeChange is called', () => {
    fixture.componentRef.setInput('data', mockHistoryData);
    fixture.detectChanges();

    let emittedSize: number | undefined;
    let emittedPage: number | undefined;

    component.sizeChange.subscribe(size => emittedSize = size);
    component.pageChange.subscribe(page => emittedPage = page);

    const mockEvent = {
      target: {
        value: '10'
      }
    } as unknown as Event;

    component.onSizeChange(mockEvent);

    expect(emittedSize).toBe(10);
    expect(emittedPage).toBe(0);
  });
});
