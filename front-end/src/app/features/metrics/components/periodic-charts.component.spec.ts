import { vi } from 'vitest';

vi.mock('chart.js', () => ({
  Chart: {
    register: vi.fn()
  },
  registerables: []
}));

import {ComponentRef, createComponent, EnvironmentInjector} from '@angular/core';
import { TestBed } from '@angular/core/testing';
import { PeriodicChartsComponent } from './periodic-charts.component';
import { UserMetrics } from '../models/userMetrics.model';

describe('PeriodicChartsComponent', () => {
  let component: PeriodicChartsComponent;
  let fixture: ComponentRef<PeriodicChartsComponent>;;

  const mockUserMetrics: UserMetrics = {
    from: '2026-01-01',
    to: '2026-07-10',
    averageCycleTime: '3d',
    averageReviewTime: '1d',
    numberOfReviewReopened: 1,
    numberOfIssueDone: 10,
    numberOfIssueStarted: 15,
    numberOfReviewDone: 8,
    deliverySuccessRate: 90,
    teamReviewParticipationRate: 85,
    pingPongReviewRate: 4,
    parallelIssuesInProgressRate: 12,
    issueRateByType: [
      { type: 'Story', rate: 70 },
      { type: 'Bug', rate: 30 }
    ],
    userMetricsByGranularity: [
      {
        label: 'Janvier',
        from: '2026-01-01',
        to: '2026-01-31',
        averageCycleTime: '3d',
        averageReviewTime: '1d',
        numberOfReviewReopened: 1,
        numberOfIssueDone: 10,
        numberOfIssueStarted: 15,
        numberOfReviewDone: 8,
        deliverySuccessRate: 90,
        teamReviewParticipationRate: 85,
        pingPongReviewRate: 4,
        parallelIssuesInProgressRate: 12,
        issueRateByType: []
      }
    ]
  };

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [PeriodicChartsComponent]
    });

    // 🎯 Création de l'instance via l'API officielle d'Angular sans passer par le TestBed standard
    const injector = TestBed.inject(EnvironmentInjector);
    fixture = createComponent(PeriodicChartsComponent, { environmentInjector: injector });
    component = fixture.instance;
  });

  it('should create and initialize options correctly', () => {
    fixture.setInput('userMetrics', mockUserMetrics);

    expect(component).toBeTruthy();
    expect(component.commonOptions?.responsive).toBeTruthy();
    expect(component.doughnutOptions?.responsive).toBeTruthy();
  });

  it('should calculate issuesData computed signal cleanly', () => {
    fixture.setInput('userMetrics', mockUserMetrics);

    const issues = component.issuesData();
    expect(issues.labels).toEqual(['Janvier']);
    expect(issues.datasets[0].data).toEqual([15]);
    expect(issues.datasets[1].data).toEqual([10]);
  });

  it('should calculate reviewsData computed signal cleanly', () => {
    fixture.setInput('userMetrics', mockUserMetrics);

    const reviews = component.reviewsData();
    expect(reviews.labels).toEqual(['Janvier']);
    expect(reviews.datasets[0].data).toEqual([8]);
    expect(reviews.datasets[1].data).toEqual([1]);
  });

  it('should calculate ratesData computed signal cleanly', () => {
    fixture.setInput('userMetrics', mockUserMetrics);

    const rates = component.ratesData();
    expect(rates.labels).toEqual(['Janvier']);
    expect(rates.datasets[0].data).toEqual([90]);
    expect(rates.datasets[1].data).toEqual([85]);
  });

  it('should calculate typeDistributionData computed signal cleanly', () => {
    fixture.setInput('userMetrics', mockUserMetrics);

    const distribution = component.typeDistributionData();
    expect(distribution.labels).toEqual(['Story', 'Bug']);
    expect(distribution.datasets[0].data).toEqual([70, 30]);
  });

  it('should fallback to empty array if issueRateByType is missing', () => {
    const incompleteMetrics = {
      ...mockUserMetrics,
      issueRateByType: undefined as unknown as UserMetrics['issueRateByType']
    };
    fixture.setInput('userMetrics', incompleteMetrics);

    const distribution = component.typeDistributionData();
    expect(distribution.labels).toEqual([]);
    expect(distribution.datasets[0].data).toEqual([]);
  });
});
