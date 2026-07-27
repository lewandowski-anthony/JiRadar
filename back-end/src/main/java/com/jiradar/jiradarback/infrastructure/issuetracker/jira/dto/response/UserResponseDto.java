package com.jiradar.jiradarback.infrastructure.issuetracker.jira.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserResponseDto {
    private String accountId;
	private String avatarUrl;
    private String emailAddress;
    private String displayName;
    private boolean active;

	@JsonProperty("avatarUrls")
	private void unpackAvatarUrl(Map<String, String> avatarUrls) {
		if (avatarUrls != null) {
			this.avatarUrl = avatarUrls.get("48x48");
		}
	}

}
