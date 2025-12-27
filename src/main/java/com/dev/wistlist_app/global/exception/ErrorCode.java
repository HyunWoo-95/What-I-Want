package com.dev.wistlist_app.global.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {

	PASSWORD_MISS_MATCH(HttpStatus.BAD_REQUEST, "40001", "비밀번호를 다시 확인해 주세요."),

	EMAIL_NOT_FOUND(HttpStatus.NOT_FOUND, "40401", "등록되지 않은 이메일 입니다."),
	USER_NOT_FOUND(HttpStatus.NOT_FOUND, "40402", "존재하지 않는 사용자 입니다."),
	WISHLIST_NOT_FOUND(HttpStatus.NOT_FOUND, "40403", "위시리스트가 존재하지 않습니다."),
	WISH_NOT_FOUND(HttpStatus.NOT_FOUND, "40404", "위시가 존재하지 않습니다."),
	PROFILE_NOT_EXIST(HttpStatus.NOT_FOUND, "40405", "작성된 프로필이 존재하지 않습니다."),
	DUPLICATED_EMAIL(HttpStatus.CONFLICT, "40301", "중복된 이메일 입니다."),
	DUPLICATED_NICKNAME(HttpStatus.CONFLICT, "40302", "중복된 닉네임 입니다."),

	UNAUTHENTICATED_USER(HttpStatus.UNAUTHORIZED, "40101", "로그인 후 이용 가능합니다."),
	UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "40102", "해당 요청에 대한 권한이 없습니다."),
	;

	private final HttpStatus httpStatus;
	private final String code;
	private final String errorMessage;
}
