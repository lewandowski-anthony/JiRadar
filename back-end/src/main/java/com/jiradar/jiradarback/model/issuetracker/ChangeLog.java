package com.jiradar.jiradarback.model.issuetracker;

import com.jiradar.jiradarback.model.enums.TransitionType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;

import java.time.ZonedDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ChangeLog {

	private User author;
	private ZonedDateTime date;
	private TransitionType transitionType;

	public boolean isStartedChange() {
		return transitionType == TransitionType.START_DEVELOPMENT;
	}

	public boolean isDoneChange() {
		return transitionType == TransitionType.DONE;
	}

	public boolean isReviewRequested() {
		return TransitionType.REQUEST_REVIEW == transitionType;
	}

	public boolean isReviewActionBy(String devEmail) {
		return StringUtils.isNotBlank(this.author.getEmail())
				&& StringUtils.isNotBlank(devEmail)
				&& this.author.getEmail().equalsIgnoreCase(devEmail)
				&& TransitionType.END_REVIEW == this.transitionType;
	}

	private boolean hasHappenedBetweenPeriod(ZonedDateTime start, ZonedDateTime end) {
		return date.isBefore(end) && date.isAfter(start);
	}
}
