package com.dev.wistlist_app.global.interceptor;

import java.util.UUID;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class RequestLogInterceptor implements HandlerInterceptor {

	private static final String LOG_ID = "logId";
	private static final String START_TIME = "startTime";

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
		Object handler) throws Exception {

		String requestURI = request.getRequestURI();
		String httpMethod = request.getMethod();
		String uuid = UUID.randomUUID().toString().substring(0, 8); // 짧게

		// 요청 시작 시간 기록
		long startTime = System.currentTimeMillis();

		request.setAttribute(LOG_ID, uuid);
		request.setAttribute(START_TIME, startTime);

		// 요청 로그
		log.info("[{}] {} {} - START", uuid, httpMethod, requestURI);

		return true;
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response,
		Object handler, Exception ex) throws Exception {

		String requestURI = request.getRequestURI();
		String httpMethod = request.getMethod();
		String logId = (String) request.getAttribute(LOG_ID);
		Long startTime = (Long) request.getAttribute(START_TIME);

		// 응답 시간 계산
		long duration = System.currentTimeMillis() - startTime;
		int statusCode = response.getStatus();

		// 응답 로그
		if (ex != null) {
			log.error("[{}] {} {} - ERROR [{}ms] status={}",
				logId, httpMethod, requestURI, duration, statusCode, ex);
		} else {
			log.info("[{}] {} {} - END [{}ms] status={}",
				logId, httpMethod, requestURI, duration, statusCode);
		}
	}
}