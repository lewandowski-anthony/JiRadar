package com.jiradar.jiradarback.infrastructure.jira.dto.response;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserResponseDto {
    private String accountId;
    private String emailAddress;
    private String displayName;
    private boolean active;
}
