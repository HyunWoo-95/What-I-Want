package com.dev.wistlist_app.domain.wish.dto;

import java.time.LocalDateTime;

import com.dev.wistlist_app.domain.wish.entity.WishStatus;

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

	@Getter
	@AllArgsConstructor
	@NoArgsConstructor
	public static class WishRequest {
		@NotBlank(message = "내용은 필수입니다")
		private String content;
	}

	@Getter
	@AllArgsConstructor
	@NoArgsConstructor
	public static class WishStatusRequest {
		@NotBlank(message = "상태 선택은 필수입니다")
		private WishStatus status;
	}
}
