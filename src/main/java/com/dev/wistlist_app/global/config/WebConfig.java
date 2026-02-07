package com.dev.wistlist_app.global.config;

import java.util.List;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.dev.wistlist_app.global.annotation.LoginUserArgumentResolver;
import com.dev.wistlist_app.global.interceptor.LoginCheckInterceptor;
import com.dev.wistlist_app.global.interceptor.RequestLogInterceptor;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer {
	private final LoginCheckInterceptor loginCheckInterceptor;
	private final LoginUserArgumentResolver loginUserArgumentResolver;
	private final RequestLogInterceptor requestLogInterceptor;

	@Override
	public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
		resolvers.add(loginUserArgumentResolver);
	}

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(requestLogInterceptor)
			.order(1)
			.addPathPatterns("/**")
			.excludePathPatterns("/api/health",           // Health Check 제외
				"/api/actuator/**");
		registry.addInterceptor(loginCheckInterceptor)
			.addPathPatterns("/**")
			.excludePathPatterns("/error", "/api/v1/users/join", "/api/v1/users/login"
				, "/api/v1/bucket-items/search");
	}
}
