package com.jiradar.jiradarback.infrastructure.jira.gateway.mapper;

import com.jiradar.jiradarback.core.model.enums.TransitionType;
import com.jiradar.jiradarback.infrastructure.jira.config.JiraProperties;
import com.jiradar.jiradarback.infrastructure.jira.dto.response.ChangelogItemResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class JiraTransitionResolver {

	private final JiraProperties jiraProperties;

    public TransitionType mapTransitionType(ChangelogItemResponseDto itemDto) {
        if (itemDto == null || !"status".equalsIgnoreCase(itemDto.getField())) {
            return TransitionType.OTHER;
        }

        String newValue = itemDto.getToString();
        String oldValue = itemDto.getFromString();

        var mapping = jiraProperties.getStatuses();
        if (mapping == null) return TransitionType.OTHER;

        if (isStatusIn(newValue, mapping.getStartDevelopment())) {
            return TransitionType.START_DEVELOPMENT;
        } else if (isStatusIn(newValue, mapping.getRequestReview())) {
            return TransitionType.REQUEST_REVIEW;
        } else if (isStatusIn(oldValue, mapping.getRequestReview()) && !isStatusIn(newValue, mapping.getRequestReview())) {
            return TransitionType.END_REVIEW;
        } else if (isStatusIn(newValue, mapping.getDone())) {
            return TransitionType.DONE;
        }

        return TransitionType.OTHER;
    }

    private boolean isStatusIn(String value, List<String> configuredStatuses) {
        if (value == null || configuredStatuses == null) return false;
        return configuredStatuses.stream().anyMatch(value::equalsIgnoreCase);
    }
}