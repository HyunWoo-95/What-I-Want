package com.dev.wistlist_app.domain.wish.dto;

import java.time.LocalDateTime;

import com.dev.wistlist_app.domain.wish.entity.WishStatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class WishResponseDto {

	@Getter
	@Builder
	@AllArgsConstructor
	@NoArgsConstructor
	public static class WishListResponse {
		private Long listId;
		private Long userId;
		private String title;
		private LocalDateTime dueDate;
	}

	@Getter
	@Builder
	@AllArgsConstructor
	@NoArgsConstructor
	public static class WishResponse {
		private Long wishId;
		private String content;
		private WishStatus status;
		private LocalDateTime createdAt;
		private LocalDateTime updatedAt;

		public WishResponse(String content, WishStatus status, LocalDateTime createdAt, LocalDateTime updatedAt) {
			this.content = content;
			this.status = status;
			this.createdAt = createdAt;
			this.updatedAt = updatedAt;
		}

		public WishResponse(Long wishId, String content, WishStatus status, LocalDateTime createdAt) {
			this.wishId = wishId;
			this.content = content;
			this.status = status;
			this.createdAt = createdAt;
		}
	}
}
