package com.dev.wistlist_app;

import com.dev.wistlist_app.domain.users.entity.Interest;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class UserRequestDto {

	@Getter
	@AllArgsConstructor
	@NoArgsConstructor
	public static class JoinRequest {
		private String email;
		private String password;
		private String username;
	}

	@Getter
	@AllArgsConstructor
	@NoArgsConstructor
	public static class LoginRequest {
		private String email;
		private String password;
	}
}
