import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ReactiveFormsModule } from '@angular/forms';
import { of, throwError } from 'rxjs';
import {vi, MockInstance} from 'vitest';
import { signal, WritableSignal } from '@angular/core';

import { DashboardComponent } from './dashboard.component';
import { MetricsService } from '../metrics/services/metrics.service';
import { HistoryService } from '../history/services/history.service';

interface MockMetricsService {
  userMetrics: WritableSignal<unknown>;
  getUserMetrics: MockInstance;
}

interface MockHistoryService {
  history: WritableSignal<unknown>;
  getUserHistories: MockInstance;
}

describe('DashboardComponent', () => {
  let component: DashboardComponent;
  let fixture: ComponentFixture<DashboardComponent>;
  let mockMetricsService: MockMetricsService;
  let mockHistoryService: MockHistoryService;

  beforeEach(async () => {
    mockMetricsService = {
      userMetrics: signal(null),
      getUserMetrics: vi.fn(() => of({}))
    };

    mockHistoryService = {
      history: signal(null),
      getUserHistories: vi.fn(() => of({}))
    };

    await TestBed.configureTestingModule({
      imports: [DashboardComponent, ReactiveFormsModule],
      providers: [
        { provide: MetricsService, useValue: mockMetricsService },
        { provide: HistoryService, useValue: mockHistoryService }
      ]
    }).compileComponents();

    fixture = TestBed.createComponent(DashboardComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create the component with default values', () => {
    expect(component).toBeTruthy();
    expect(component.isLoading()).toBeFalsy();
    expect(component.errorMessage()).toBeNull();
    expect(component.activeTab()).toBe('global');
    expect(component.historyPage).toBe(0);
    expect(component.historySize).toBe(5);
    expect(component.searchForm.invalid).toBeTruthy();
  });

  it('should not load data if form is invalid', () => {
    component.loadDashboardData();
    expect(mockMetricsService.getUserMetrics).not.toHaveBeenCalled();
    expect(mockHistoryService.getUserHistories).not.toHaveBeenCalled();
  });

  it('should load dashboard data successfully when form is valid', () => {
    component.searchForm.setValue({
      projectKey: 'JIRA',
      startDate: '2026-01-01',
      endDate: '2026-12-31',
      granularity: 'MONTHLY'
    });

    component.loadDashboardData();

    expect(component.isLoading()).toBeFalsy();
    expect(component.errorMessage()).toBeNull();
    expect(mockMetricsService.getUserMetrics).toHaveBeenCalledWith('jira', ['JIRA'], '2026-01-01', '2026-12-31', 'MONTHLY');
    expect(mockHistoryService.getUserHistories).toHaveBeenCalledWith('jira', ['JIRA'], '2026-01-01', '2026-12-31', 0, 5);
  });

  it('should reset active tab to global if granularity is missing and tab is periodic', () => {
    component.activeTab.set('periodic');
    component.searchForm.setValue({
      projectKey: 'JIRA',
      startDate: '',
      endDate: '',
      granularity: ''
    });

    component.loadDashboardData();

    expect(component.activeTab()).toBe('global');
  });

  it('should handle metrics error correctly', () => {
    mockMetricsService.getUserMetrics.mockImplementation(() => throwError(() => new Error('Metrics Error')));

    component.searchForm.setValue({
      projectKey: 'JIRA',
      startDate: '',
      endDate: '',
      granularity: ''
    });

    component.loadDashboardData();

    expect(component.isLoading()).toBeFalsy();
    expect(component.errorMessage()).toBe('Metrics Error');
  });

  it('should handle history error correctly', () => {
    mockHistoryService.getUserHistories.mockImplementation(() => throwError(() => new Error('History Error')));

    component.searchForm.setValue({
      projectKey: 'JIRA',
      startDate: '',
      endDate: '',
      granularity: ''
    });

    component.loadDashboardData();

    expect(component.errorMessage()).toBe('History Error');
  });

  it('should load requested history page', () => {
    component.searchForm.setValue({
      projectKey: 'ABC',
      startDate: '2026-07-10',
      endDate: '',
      granularity: ''
    });

    component.loadHistoryPage(3);

    expect(component.historyPage).toBe(3);
    expect(mockHistoryService.getUserHistories).toHaveBeenCalledWith('jira', ['ABC'], '2026-07-10', '', 3, 5);
  });

  it('should not load history page if form is invalid', () => {
    component.loadHistoryPage(2);
    expect(mockHistoryService.getUserHistories).not.toHaveBeenCalled();
  });

  it('should update history size and reload from page 0', () => {
    component.searchForm.setValue({
      projectKey: 'XYZ',
      startDate: '',
      endDate: '',
      granularity: ''
    });
    component.historyPage = 4;

    component.updateHistorySize(20);

    expect(component.historySize).toBe(20);
    expect(component.historyPage).toBe(0);
    expect(mockHistoryService.getUserHistories).toHaveBeenCalledWith('jira', ['XYZ'], '', '', 0, 20);
  });
});
