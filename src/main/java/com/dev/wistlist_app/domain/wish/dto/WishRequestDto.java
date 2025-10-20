package com.dev.wistlist_app.domain.wish.dto;

import java.time.LocalDateTime;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class WishRequestDto {
	@Getter
	@AllArgsConstructor
	@NoArgsConstructor
	public static class WishListRequest {
		@NotBlank(message = "제목은 필수입니다")
		private String title;
		@NotBlank(message = "마감일은 필수 입니다.")
		private LocalDateTime duedate;
	}
}
