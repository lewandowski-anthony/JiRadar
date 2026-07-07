package com.jiradar.jiradarback.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class DevPerformanceResponseDto {

	private int totalIssuesDone;
	private double totalStoryPointsDone;
	private long totalTimeSpentSeconds;
	private long totalTimeEstimatedSeconds;
	private double estimationAccuracyRatio;
	private Map<String, Long> timePerStatusSeconds;
	private long totalLeadTimeSeconds;
}
