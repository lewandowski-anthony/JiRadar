package com.jiradar.jiradarback.core.model.issuetracker;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User {

	private String email;
	private String name;
	private String avatarUrl;
}
