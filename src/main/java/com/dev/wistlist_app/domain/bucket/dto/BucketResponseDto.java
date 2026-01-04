package com.dev.wistlist_app.domain.bucket.dto;

import java.time.LocalDateTime;

import com.dev.wistlist_app.domain.bucket.entity.BucketItemStatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class BucketResponseDto {

	@Getter
	@Builder
	@AllArgsConstructor
	@NoArgsConstructor
	public static class BucketListResponse {
		private Long listId;
		private Long userId;
		private String title;
		private LocalDateTime dueDate;
	}

	@Getter
	@Builder
	@AllArgsConstructor
	@NoArgsConstructor
	public static class BucketItemResponse {
		private Long wishId;
		private String content;
		private BucketItemStatus status;
		private LocalDateTime createdAt;
		private LocalDateTime updatedAt;

		public BucketItemResponse(String content, BucketItemStatus status, LocalDateTime createdAt, LocalDateTime updatedAt) {
			this.content = content;
			this.status = status;
			this.createdAt = createdAt;
			this.updatedAt = updatedAt;
		}

		public BucketItemResponse(Long wishId, String content, BucketItemStatus status, LocalDateTime createdAt) {
			this.wishId = wishId;
			this.content = content;
			this.status = status;
			this.createdAt = createdAt;
		}
	}
}
