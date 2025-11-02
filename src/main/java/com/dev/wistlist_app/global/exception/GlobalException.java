package com.dev.wistlist_app.global.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class GlobalException extends RuntimeException {
	private ErrorCode errorCode;

	@Override
	public String getMessage() {
		return errorCode.getErrorMessage();
	}
}
