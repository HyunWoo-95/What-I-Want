package com.dev.wistlist_app.domain.bucket.dto;

import java.time.LocalDate;
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
	public static class BucketItemResponse {
		private Long userId;
		private String username;
		private Long listId;
		private Long bucketId;
		private String content;
		private BucketItemStatus status;
		private LocalDate dueDate;
		private LocalDateTime createdAt;
		private LocalDateTime updatedAt;

		public BucketItemResponse(Long userId, String username, Long listId, Long bucketId, String content,
			BucketItemStatus status,
			LocalDate dueDate,
			LocalDateTime createdAt) {
			this.userId = userId;
			this.username = username;
			this.listId = listId;
			this.bucketId = bucketId;
			this.content = content;
			this.status = status;
			this.dueDate = dueDate;
			this.createdAt = createdAt;
		}
	}
}
