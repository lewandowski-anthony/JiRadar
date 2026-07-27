package com.jiradar.jiradarback.infrastructure.ai.common.model.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;
import java.util.stream.Collectors;

@Getter
@RequiredArgsConstructor
public enum DeveloperTitle {

	BUG_FIXER("Focuses heavily on resolving defects, stability, and fixing bugs efficiently."),
	STORIES_GENIUS("Excels at delivering user stories, feature specs, and business requirements."),
	CODE_REVIEW_CHAMP("Actively reviews peers' code, provides high-quality feedback, and drives engineering standards."),
	TEAM_PLAYER("Demonstrates strong collaboration, high PR review participation, and supports teammates."),
	SOLO_CRUSADER("Works independently with high throughput, but operates mostly in isolation."),
	QUALITY_GUARDIAN("Prioritizes test coverage, documentation, refactoring, and long-term code health."),
	SHIPPING_MACHINE("Delivers a massive volume of code/tasks at very high velocity and minimal cycle time.");

	private final String description;

	public static String getFormattedTitlesWithDescriptions() {
		return Arrays.stream(values())
				.map(title -> "- " + title.name() + ": " + title.getDescription())
				.collect(Collectors.joining("\n"));
	}
}