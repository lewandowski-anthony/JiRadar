import { ComponentFixture, TestBed } from '@angular/core/testing';
import { MetricsDisplayComponent } from './metrics-display.component';
import { UserMetrics } from '@features/metrics/models/userMetrics.model';

describe('MetricsDisplayComponent', () => {
  let component: MetricsDisplayComponent;
  let fixture: ComponentFixture<MetricsDisplayComponent>;

  const mockUserMetrics: UserMetrics = {
    from: '2026-01-01',
    to: '2026-07-10',
    averageCycleTime: '2d',
    averageReviewTime: '1d',
    numberOfReviewReopened: 2,
    numberOfIssueDone: 45,
    numberOfIssueStarted: 12,
    numberOfReviewDone: 30,
    deliverySuccessRate: 88,
    teamReviewParticipationRate: 95,
    pingPongReviewRate: 5,
    parallelIssuesInProgressRate: 10,
    issueRateByType: [],
    userMetricsByGranularity: []
  };

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [MetricsDisplayComponent]
    }).compileComponents();

    fixture = TestBed.createComponent(MetricsDisplayComponent);
    component = fixture.componentInstance;
  });

  it('should create the component and receive required input data', () => {
    fixture.componentRef.setInput('data', mockUserMetrics);
    fixture.detectChanges();

    expect(component).toBeTruthy();
    expect(component.data()).toEqual(mockUserMetrics);
    expect(component.data().deliverySuccessRate).toBe(88);
  });
});
