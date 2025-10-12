package com.dev.wistlist_app.global.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

		http
			// 1. CSRF 비활성화 (REST API 환경에서는 일반적으로 비활성화)
			.csrf(csrf -> csrf.disable())

			// 2. 인증 및 인가 규칙 설정
			.authorizeHttpRequests(auth -> auth
				// 로그인 API 엔드포인트는 인증 없이 접근 허용
				.requestMatchers("/users/join", "/users/login").permitAll()
				// 기타 모든 요청은 인증 필요
				.anyRequest().authenticated()
			)

			// 3. 폼 로그인 (Form Login) 기능 비활성화 ⬅️ 핵심 설정
			// 이렇게 하면 Spring Security가 제공하는 기본 로그인 페이지 및 처리 로직이 동작하지 않음
			.formLogin(form -> form.disable())

			// 4. HTTP Basic 인증 비활성화 (선택 사항)
			.httpBasic(httpBasic -> httpBasic.disable());
		return http.build();
	}

	@Bean
	public BCryptPasswordEncoder encodePassword() {
		return new BCryptPasswordEncoder();
	}
}
