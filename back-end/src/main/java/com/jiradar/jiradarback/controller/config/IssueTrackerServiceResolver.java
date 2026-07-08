package com.jiradar.jiradarback.controller.config;

import com.jiradar.jiradarback.core.IssueTrackerService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import org.springframework.web.servlet.HandlerMapping;

import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class IssueTrackerServiceResolver implements HandlerMethodArgumentResolver {

    private final List<IssueTrackerService> issueTrackerServices;

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return IssueTrackerService.class.isAssignableFrom(parameter.getParameterType());
    }

    @Override
    @SuppressWarnings("unchecked")
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
                                  NativeWebRequest webRequest, WebDataBinderFactory binderFactory) {

        HttpServletRequest request = (HttpServletRequest) webRequest.getNativeRequest();
        Map<String, String> pathVariables = (Map<String, String>) request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);

        String provider = pathVariables.get("issueTracker");

        return issueTrackerServices.stream()
                .filter(service -> service.supports(provider))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Tracker provider is not supported : " + provider));
    }
}