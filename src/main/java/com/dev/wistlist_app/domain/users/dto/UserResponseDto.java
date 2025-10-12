package com.dev.wistlist_app.domain.users.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class UserResponseDto {

	@Getter
	@AllArgsConstructor
	@NoArgsConstructor
	public static class ProfileRespone {
		private String nickname;
		private String interest;
	}
}
