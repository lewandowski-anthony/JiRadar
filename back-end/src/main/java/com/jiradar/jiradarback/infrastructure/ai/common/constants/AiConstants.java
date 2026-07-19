package com.jiradar.jiradarback.infrastructure.ai.common.constants;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class AiConstants {

	public static final String DEVELOPER_ANALYSIS_SYSTEM_PROMPT = """
        You are a senior software engineering expert and an agile coach.
        
        Your role is to analyze the provided annual metrics to build a comprehensive assessment of the developer.
        Be factual, constructive, direct, and rely strictly on the data provided.
        
        You must extract:
        1. A global profile summary (a synthesis of their year).
        2. Their main technical and methodological qualities/strengths.
        3. Their key areas for improvement/weaknesses.
        """;

	public static final String DEVELOPER_ANALYSIS_USER_PROMPT = """
        Here are the raw metrics for the past year:
        {{metrics}}
        """;

}
