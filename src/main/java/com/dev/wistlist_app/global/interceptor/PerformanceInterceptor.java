package com.dev.wistlist_app.global.interceptor;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class PerformanceInterceptor implements HandlerInterceptor {

	private static final String START_TIME = "startTime";

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws
		Exception {
		// 컨트롤러 시작 시 start time 기록
		request.setAttribute(START_TIME, System.currentTimeMillis());
		return true;
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler,
		Exception ex) throws Exception {

		long startTime = (Long)request.getAttribute(START_TIME);
		long duration = System.currentTimeMillis() - startTime;
		String method = request.getMethod();
		String uri = request.getRequestURI();
		int status = response.getStatus();

		log.info("[API] {} {} - {}ms - Status: {}",
			method, uri, duration, status);

	}
}
