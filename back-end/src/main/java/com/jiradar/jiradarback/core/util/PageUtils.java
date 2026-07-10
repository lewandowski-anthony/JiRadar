package com.jiradar.jiradarback.core.util;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.Collections;
import java.util.List;

public final class PageUtils {

    private PageUtils() {
        // Utility class static access only
    }

    public static <E> Page<E> toPage(List<E> content, Pageable pageable) {
        if (pageable.getOffset() >= content.size()) {
           return new PageImpl<>(Collections.emptyList(), pageable, content.size());
        }
        List<E> pagedContent = content.stream()
              .skip(pageable.getOffset())
              .limit(pageable.getPageSize())
              .toList();
        return new PageImpl<>(pagedContent, pageable, content.size());
    }
}