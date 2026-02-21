package com.dev.wistlist_app.domain.bucket.dto;

import java.time.LocalDate;

import com.dev.wistlist_app.domain.bucket.entity.BucketCategory;
import com.dev.wistlist_app.domain.bucket.entity.BucketItemStatus;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class BucketRequestDto {

	@Getter
	@AllArgsConstructor
	@NoArgsConstructor
	public static class BucketItemCreateRequest {
		@NotBlank(message = "내용은 필수입니다")
		private String content;
		private BucketCategory bucketCategory;
		@NotBlank(message = "마감일은 필수 입니다.")
		private LocalDate dueDate;
	}

	@Getter
	@AllArgsConstructor
	@NoArgsConstructor
	public static class BucketItemStatusRequest {
		@NotBlank(message = "상태 선택은 필수입니다")
		private BucketItemStatus status;
	}
}
