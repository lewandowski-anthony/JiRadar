import { TestBed } from '@angular/core/testing';
import { HttpTestingController, provideHttpClientTesting } from '@angular/common/http/testing';
import { provideHttpClient } from '@angular/common/http';
import { HistoryService } from './history.service';

describe('HistoryService', () => {
  let service: HistoryService;
  let httpMock: HttpTestingController;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [
        HistoryService,
        provideHttpClient(),
        provideHttpClientTesting()
      ]
    });

    service = TestBed.inject(HistoryService);
    httpMock = TestBed.inject(HttpTestingController);
  });

  afterEach(() => {
    httpMock.verify();
  });

  it('should fetch user histories and map snake_case to camelCase', () => {
    const mockRawResponse = [
      {
        issue_key: 'JIRA-123',
        issue_summary: 'Test summary',
        issue_type: 'Story',
        date: '2026-07-10',
        transition_type: 'In Progress',
        issue_assignee: { id: '1', name: 'Alex', email: 'alex@test.com', avatar_url: '' }
      }
    ];

    service.getUserHistories('jira', ['PROJ'], '2026-01-01', '2026-12-31', 0, 5).subscribe(page => {
      expect(page.content[0].issueKey).toBe('JIRA-123');
      expect(page.content[0].issueSummary).toBe('Test summary');
      expect(page.number).toBe(0);
    });

    const req = httpMock.expectOne(request => request.url.includes('/api/v1/tracker/jira/users/me/history'));
    expect(req.request.method).toBe('GET');

    req.flush(mockRawResponse, {
      headers: {
        'page-number': '0',
        'page-size': '5',
        'total-elements': '1',
        'total-pages': '1'
      }
    });
  });
});
