package com.dev.wistlist_app.global.annotation;

import org.springframework.context.annotation.Bean;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import com.dev.wistlist_app.domain.users.service.SessionLoginService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class LoginUserArgumentResolver implements HandlerMethodArgumentResolver {
	private final SessionLoginService loginService;

	@Override
	public boolean supportsParameter(MethodParameter parameter) {

		log.info("support parameter 실행");

		boolean hasLoginAnnotation = parameter.hasParameterAnnotation(Login.class);
		boolean hasLongType = Long.class.isAssignableFrom(parameter.getParameterType());

		return hasLoginAnnotation && hasLongType;
	}

	@Override
	public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
		NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {

		log.info("ArgumentResolver 실행");

		return loginService.getLoginUser();
	}
}
