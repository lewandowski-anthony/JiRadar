package com.jiradar.jiradarback.client.config;

import com.jiradar.jiradarback.client.jira.JiraServiceClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

@Configuration
public class ClientConfig {

    @Bean
    public JiraServiceClient userServiceClient(HttpServiceProxyFactory factory) {
        return factory.createClient(JiraServiceClient.class);
    }
}