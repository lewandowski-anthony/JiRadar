import { TestBed } from '@angular/core/testing';
import { HttpTestingController, provideHttpClientTesting } from '@angular/common/http/testing';
import { provideHttpClient } from '@angular/common/http';
import { MetricsService } from './metrics.service';

describe('MetricsService', () => {
  let service: MetricsService;
  let httpMock: HttpTestingController;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [
        MetricsService,
        provideHttpClient(),
        provideHttpClientTesting()
      ]
    });

    service = TestBed.inject(MetricsService);
    httpMock = TestBed.inject(HttpTestingController);
  });

  afterEach(() => {
    httpMock.verify();
  });

  it('should fetch user metrics and update the signal', () => {
    const mockResponse = { data: 'metrics-payload' };

    service.getUserMetrics('jira', ['PROJ'], '2026-01-01', '2026-07-10', 'MONTHLY').subscribe((res) => {
      expect(res).toEqual(mockResponse);
      expect(service.userMetrics()).toEqual(mockResponse);
    });

    const req = httpMock.expectOne((request) => request.url.includes('/metrics'));

    expect(req.request.method).toBe('GET');
    req.flush(mockResponse);
  });
});
