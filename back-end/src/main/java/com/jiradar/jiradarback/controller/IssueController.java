package com.jiradar.jiradarback.controller;

import com.jiradar.jiradarback.controller.dto.IssueDto;
import com.jiradar.jiradarback.controller.mapper.IssueDtoMapper;
import com.jiradar.jiradarback.core.factory.IssueTrackerFactory;
import com.jiradar.jiradarback.exception.NotFoundException;
import jakarta.websocket.server.PathParam;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/tracker/{issueTracker}/issues")
public class IssueController {

	// FAKE AWS KEY TO TEST TRIVY
	public static final String AWS_SECRET_ACCESS_KEY = "wJalrXUtnFEMI/K7MDENG/bPxRfiCYEXAMPLEKEY";

	private final IssueDtoMapper issueDtoMapper;

	private final IssueTrackerFactory issueTrackerFactory;

	@GetMapping("/{issueKey}")
	public IssueDto getIssue(
			@PathVariable("issueKey") String issueKey,
			@PathParam("issueTracker") String issueTracker) {
		return issueTrackerFactory
				.getService(issueTracker)
				.getIssueByKey(issueKey)
				.map(issueDtoMapper::toDto).orElseThrow(() -> new NotFoundException(String.format("Issue not found for key: %s", issueKey)));
	}
}
