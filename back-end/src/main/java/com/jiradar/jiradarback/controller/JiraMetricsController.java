package com.jiradar.jiradarback.controller;

import com.jiradar.jiradarback.controller.dto.DevPerformanceResponseDto;
import com.jiradar.jiradarback.service.JiraService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/v1/metrics")
@RequiredArgsConstructor
public class JiraMetricsController {

	private final JiraService jiraService;

	@GetMapping("/performance")
	public DevPerformanceResponseDto getDeveloperPerformance(
			@RequestParam("developer_email") String developerEmail){

		return null;
	}
}
