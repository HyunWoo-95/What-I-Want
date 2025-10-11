package com.dev.wistlist_app.global.interceptor;

import static com.dev.wistlist_app.global.constant.SessionConst.*;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;


import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class LoginCheckInterceptor implements HandlerInterceptor {
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws
		Exception {

		/**
		 * getSession() or getSession(true/ default) : 세션이 있으면 해당 세션 그대로 반환 없다면 새로 새성
		 * getSession(false) : 세션이 있으면 해당 세션 그대로 반환 없다면 Null 반환
		 */
		log.info("인증 체크 인터셉터 실행 {}", request.getRequestURI());
		HttpSession session = request.getSession(false);
		if (session == null || session.getAttribute(LOGIN_USER) == null) {
			log.info("미인증 사용자 요청");
			return false;
		}
		return true;
	}
}
