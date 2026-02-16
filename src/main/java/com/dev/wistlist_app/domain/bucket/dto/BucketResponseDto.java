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
		private Long bucketId;
		private String content;
		private BucketItemStatus status;
		private LocalDate dueDate;
		private Long cheerCount;
		private Long commentCount;
		private LocalDateTime createdAt;
		private LocalDateTime updatedAt;

	}
}
