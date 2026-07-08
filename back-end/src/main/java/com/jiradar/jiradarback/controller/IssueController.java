package com.jiradar.jiradarback.controller;

import com.jiradar.jiradarback.core.model.issuetracker.Issue;
import com.jiradar.jiradarback.core.IssueTrackerService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/tracker/{issueTracker}/issues")
public class IssueController {

	@GetMapping("/{issueKey}")
	public Issue getIssue(@PathVariable("issueKey") String issueKey, IssueTrackerService jiraService) {
		return jiraService.getIssueByKey(issueKey);
	}
}
