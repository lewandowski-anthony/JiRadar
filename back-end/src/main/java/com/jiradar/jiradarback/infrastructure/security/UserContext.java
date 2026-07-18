package com.jiradar.jiradarback.infrastructure.security;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UserContext {

    private static final ThreadLocal<String> authorizationHolder = new InheritableThreadLocal<>();

    public static void setAuthorization(String token) {
        authorizationHolder.set(token);
    }

    public static String getAuthorization() {
        return authorizationHolder.get();
    }

    public static void clear() {
        authorizationHolder.remove();
    }
}