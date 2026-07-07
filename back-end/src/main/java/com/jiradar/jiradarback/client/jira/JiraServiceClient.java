package com.jiradar.jiradarback.client.jira;

import com.jiradar.jiradarback.client.jira.dto.request.SearchRequestRequestDto;
import com.jiradar.jiradarback.client.jira.dto.response.JiraIssueResponseDto;
import com.jiradar.jiradarback.client.jira.dto.response.JiraStatusResponseDto;
import com.jiradar.jiradarback.client.jira.dto.response.SearchEnvelopeResponseDto;
import com.jiradar.jiradarback.client.jira.dto.response.UserResponseDto;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.PostExchange;

import java.util.List;

public interface JiraServiceClient {

	@GetExchange("/rest/api/3/myself")
	UserResponseDto getMyself();

	@PostExchange("/rest/api/3/search/jql")
	SearchEnvelopeResponseDto searchTickets(@RequestBody SearchRequestRequestDto request);

	@GetExchange("/rest/api/3/issue/{issueKey}")
	JiraIssueResponseDto getIssue(@PathVariable("issueKey") String issueKey, @RequestParam("expand") String expand);

	@GetExchange("/rest/api/3/status")
	List<JiraStatusResponseDto> getAllStatuses();

	@GetExchange("/rest/api/3/status/{statusId}")
	JiraStatusResponseDto getStatus(@PathVariable("statusId") String statusId);
}
