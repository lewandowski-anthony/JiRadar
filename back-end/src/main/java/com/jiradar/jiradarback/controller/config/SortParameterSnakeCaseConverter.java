package com.jiradar.jiradarback.controller.config;

import com.google.common.base.CaseFormat;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

@Configuration
public class SortParameterSnakeCaseConverter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        Map<String, String[]> formattedParams = request.getParameterMap().keySet().stream()
                .filter(param -> request.getParameterValues(param) != null)
                .collect(Collectors.toMap(
                        param -> param,
                        param -> param.equals("sort")
                                ? Arrays.stream(request.getParameterValues(param))
                                .map(value -> CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, value))
                                .toArray(String[]::new)
                                : request.getParameterValues(param)
                ));

        filterChain.doFilter(new CustomResponseWrapper(request, formattedParams), response);

    }

    class CustomResponseWrapper extends HttpServletRequestWrapper {

        private Map<String, String[]> params;

        public CustomResponseWrapper(HttpServletRequest request, Map<String, String[]> params) {
            super(request);
            this.params = params;
        }

        @Override
        public Map<String, String[]> getParameterMap() {
            return params;
        }

        @Override
        public String[] getParameterValues(String name) {
            return params.get(name);
        }

    }
}
