import { TestBed } from '@angular/core/testing';

import { MetricsService } from './metricsService';

describe('Metrics', () => {
  let service: MetricsService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(MetricsService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
