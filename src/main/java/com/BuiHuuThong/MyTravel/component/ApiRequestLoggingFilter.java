package com.BuiHuuThong.MyTravel.component;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.AbstractRequestLoggingFilter;

@Component
public class ApiRequestLoggingFilter extends AbstractRequestLoggingFilter {
	@Override
	protected void beforeRequest(HttpServletRequest request, String message) {
		if (!logger.isInfoEnabled()) {
			return;
		}
		logger.info(String.format("%s %s", request.getMethod(), request.getRequestURI()));
	}

	@Override
	protected void afterRequest(HttpServletRequest request, String message) {}

}
