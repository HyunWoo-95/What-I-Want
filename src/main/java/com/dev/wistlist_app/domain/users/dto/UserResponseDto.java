package com.dev.wistlist_app.domain.users.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class UserResponseDto {

	@Getter
	@Builder
	@AllArgsConstructor
	@NoArgsConstructor
	public static class ProfileResponse {
		private String nickname;
		private String interest;
		private String profileImgUrl;
		private LocalDateTime createdAt;
	}

	@Getter
	@Builder
	@AllArgsConstructor
	@NoArgsConstructor
	public static class FollowResponseDto {
		private Long profileId;
		private String nickname;
		private String profileImgUrl;
	}
}
