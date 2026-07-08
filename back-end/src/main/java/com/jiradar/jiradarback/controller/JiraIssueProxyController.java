package com.jiradar.jiradarback.controller;

import com.jiradar.jiradarback.model.issuetracker.Issue;
import com.jiradar.jiradarback.client.service.JiraService;
import com.jiradar.jiradarback.service.IssueTrackerService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/jira/issues")
public class JiraIssueProxyController {

	private final IssueTrackerService jiraService;

	@GetMapping("/{issueKey}")
	public Issue getIssue(@PathVariable("issueKey") String issueKey) {
		return jiraService.getIssueByKey(issueKey);
	}
}
