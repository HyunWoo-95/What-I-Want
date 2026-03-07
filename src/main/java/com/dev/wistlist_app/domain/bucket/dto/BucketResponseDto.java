package com.dev.wistlist_app.domain.bucket.dto;

import java.time.LocalDateTime;

import com.dev.wistlist_app.domain.bucket.entity.BucketCategory;
import com.dev.wistlist_app.domain.bucket.entity.BucketItem;
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
		private Long profileId;
		private String nickname;
		private Long bucketId;
		private BucketCategory category;
		private String content;
		private BucketItemStatus status;
		private Long cheerCount;
		private Long commentCount;
		private LocalDateTime createdAt;
		private LocalDateTime updatedAt;

		public BucketItemResponse(BucketItem bucketItem) {
			this.profileId = bucketItem.getProfile().getId();
			this.nickname = bucketItem.getProfile().getNickname();
			this.bucketId = bucketItem.getId();
			this.category = bucketItem.getCategory();
			this.content = bucketItem.getContent();
			this.status = bucketItem.getStatus();
			this.cheerCount = bucketItem.getCheerCount();
			this.commentCount = bucketItem.getCommentCount();
			this.createdAt = bucketItem.getCreatedAt();
			this.updatedAt = bucketItem.getUpdatedAt();
		}
	}
}
