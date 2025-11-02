package com.dev.wistlist_app.global.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.dev.wistlist_app.global.response.ApiResponse;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
@RestControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler {
	@ExceptionHandler(GlobalException.class)
	public ResponseEntity<ApiResponse> application(GlobalException e) {
		log.error("error occurs --> {}", e.getMessage());
		return ResponseEntity.status(e.getErrorCode().getHttpStatus())
			.body(ApiResponse.fail(e.getErrorCode().toString(), e.getMessage()));
	}
}
