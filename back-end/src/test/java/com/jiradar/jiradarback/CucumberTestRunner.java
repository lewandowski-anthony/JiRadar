package com.jiradar.jiradarback;

import org.junit.platform.suite.api.IncludeEngines;
import org.junit.platform.suite.api.SelectClasspathResource;
import org.junit.platform.suite.api.SelectPackages;
import org.junit.platform.suite.api.Suite;

@Suite
@IncludeEngines("cucumber")
@SelectPackages("com.jiradar.jiradarback")
@SelectClasspathResource("features")
public class CucumberTestRunner {
}