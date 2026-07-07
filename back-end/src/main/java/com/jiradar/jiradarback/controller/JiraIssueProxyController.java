package com.jiradar.jiradarback.controller;

import com.jiradar.jiradarback.model.JiraIssue;
import com.jiradar.jiradarback.service.JiraService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/jira/issues")
public class JiraIssueProxyController {

	private final JiraService jiraService;

	@GetMapping("/{issueKey}")
	public JiraIssue getIssue(@PathVariable("issueKey") String issueKey) {
		return jiraService.getIssueByKey(issueKey);
	}
}
