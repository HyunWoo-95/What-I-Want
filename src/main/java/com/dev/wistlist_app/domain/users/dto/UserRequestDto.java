package com.dev.wistlist_app.domain.users.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class UserRequestDto {

	@Getter
	@AllArgsConstructor
	@NoArgsConstructor
	public static class JoinRequest {
		@Email
		@NotBlank(message = "email은 필수 입력값 입니다.")
		private String email;
		@NotBlank(message = "비밀번호를 입력해주세요")
		@Pattern(regexp = "^[a-z0-9]{8,20}$", message = "소문자와 숫자(0~9)를 포함하여 8 ~ 20자의 문자열을 입력해주세요")
		private String password;
		@NotBlank(message = "이름은 필수 입력값 입니다.")
		private String username;
	}

	@Getter
	@AllArgsConstructor
	@NoArgsConstructor
	public static class LoginRequest {
		@Email
		@NotBlank(message = "email은 필수 입력값 입니다.")
		private String email;
		@NotBlank(message = "비밀번호를 입력해주세요")
		@Pattern(regexp = "^[a-z0-9]{8,20}$", message = "소문자와 숫자(0~9)를 포함하여 8 ~ 20자의 문자열을 입력해주세요")
		private String password;
	}
}
