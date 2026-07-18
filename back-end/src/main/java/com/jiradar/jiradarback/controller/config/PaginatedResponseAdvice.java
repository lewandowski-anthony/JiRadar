package com.jiradar.jiradarback.controller.config;

import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;
import org.springframework.core.MethodParameter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.ArrayList;
import java.util.List;

@NullMarked
@RestControllerAdvice
public class PaginatedResponseAdvice implements ResponseBodyAdvice<Object> {

	private static final String ACCESS_CONTROL_EXPOSE_HEADERS = "Access-Control-Expose-Headers";
	private static final String ACCESS_CONTROL_EXPOSE_HEADERS_VALUE = "Link,Page-Number,Page-Size,Total-Elements,Total-Pages,Content-Range";
	private static final String LINK_HEADER = "Link";
	private static final String PAGE_NUMBER_HEADER = "Page-Number";
	private static final String PAGE_SIZE_HEADER = "Page-Size";
	private static final String TOTAL_ELEMENTS_HEADER = "Total-Elements";
	private static final String TOTAL_PAGES_HEADER = "Total-Pages";
	private static final String CONTENT_RANGE_HEADER = "Content-Range";

	@Override
	public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
		return Page.class.isAssignableFrom(returnType.getParameterType());
	}

	@Override
	public @Nullable Object beforeBodyWrite(@Nullable Object body, MethodParameter returnType, MediaType selectedContentType, Class<? extends HttpMessageConverter<?>> selectedConverterType,
			ServerHttpRequest request, ServerHttpResponse response) {

		if (!(body instanceof PageImpl<?> page)) {
			return body;
		}

		HttpHeaders headers = response.getHeaders();
		headers.set(ACCESS_CONTROL_EXPOSE_HEADERS, ACCESS_CONTROL_EXPOSE_HEADERS_VALUE);

		String links = this.buildPaginationLinks(page, request);
		if (StringUtils.hasText(links)) {
			headers.set(LINK_HEADER, links);
		}

		int pageNumber = page.getNumber();
		int pageSize = page.getSize();
		long totalElements = page.getTotalElements();
		int totalPages = page.getTotalPages();

		headers.set(PAGE_NUMBER_HEADER, String.valueOf(pageNumber));
		headers.set(PAGE_SIZE_HEADER, String.valueOf(pageSize));
		headers.set(TOTAL_ELEMENTS_HEADER, String.valueOf(totalElements));
		headers.set(TOTAL_PAGES_HEADER, String.valueOf(totalPages));

		int eltMax = (long) (pageNumber + 1) * pageSize > totalElements ? (int) (totalElements - 1L) : (pageNumber + 1) * pageSize - 1;

		headers.set(CONTENT_RANGE_HEADER, pageNumber * pageSize + "-" + eltMax + "/" + totalElements);

		final HttpStatus httpStatus = totalPages > 1 ? HttpStatus.PARTIAL_CONTENT : HttpStatus.OK;
		response.setStatusCode(httpStatus);

		return page.getContent();
	}

	private String buildPaginationLinks(PageImpl<?> page, ServerHttpRequest request) {
		List<String> links = new ArrayList<>();
		String path = request.getURI().getPath();
		String query = request.getURI().getQuery();
		UriComponentsBuilder builder = UriComponentsBuilder.fromPath(path).query(query);

		UriComponentsBuilder uriComponentsBuilder;
		if (!page.isFirst()) {
			uriComponentsBuilder = this.replacePageAndSize(builder, page.getPageable().first());
			links.add('<' + uriComponentsBuilder.toUriString() + ">; rel=\"first\"");
		}

		if (page.hasPrevious()) {
			uriComponentsBuilder = this.replacePageAndSize(builder, page.previousPageable());
			links.add('<' + uriComponentsBuilder.toUriString() + ">; rel=\"prev\"");
		}

		if (page.hasNext()) {
			uriComponentsBuilder = this.replacePageAndSize(builder, page.nextPageable());
			links.add('<' + uriComponentsBuilder.toUriString() + ">; rel=\"next\"");
		}

		if (!page.isLast()) {
			uriComponentsBuilder = builder.cloneBuilder();
			uriComponentsBuilder.replaceQueryParam("page", page.getTotalPages());
			uriComponentsBuilder.replaceQueryParam("size", page.getSize());
			links.add('<' + uriComponentsBuilder.toUriString() + ">; rel=\"last\"");
		}

		return String.join(",", links);
	}

	private UriComponentsBuilder replacePageAndSize(UriComponentsBuilder uriComponentsBuilder, Pageable page) {
		UriComponentsBuilder builder = uriComponentsBuilder.cloneBuilder();
		builder.replaceQueryParam("page", page.getPageNumber());
		builder.replaceQueryParam("size", page.getPageSize());
		return builder;
	}
}