package com.dev.wistlist_app.domain.bucket.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dev.wistlist_app.domain.bucket.dto.BucketRequestDto;
import com.dev.wistlist_app.domain.bucket.dto.BucketRequestDto.BucketListCreateRequest;
import com.dev.wistlist_app.domain.bucket.dto.BucketResponseDto;
import com.dev.wistlist_app.domain.bucket.dto.BucketResponseDto.BucketItemResponse;
import com.dev.wistlist_app.domain.bucket.dto.BucketResponseDto.BucketListResponse;
import com.dev.wistlist_app.domain.bucket.service.BucketListService;
import com.dev.wistlist_app.global.annotation.Login;
import com.dev.wistlist_app.global.response.ApiResponse;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/bucket-lists")
@RequiredArgsConstructor
public class BucketListApiContorller {

	private final BucketListService bucketListService;

	@GetMapping
	public ApiResponse<List<BucketListResponse>> getMyBucketList(@Login Long userId) {
		return ApiResponse.success(bucketListService.getMyBucketList(userId));
	}

	@GetMapping("/{listId}")
	public ApiResponse<List<BucketItemResponse>> getMyBucketItems(@Login Long userId, @PathVariable Long listId) {
		return ApiResponse.success(bucketListService.getMyBucketItems(userId, listId));
	}

	@PostMapping
	public void createBucketList(@Login Long userId, @RequestBody @Valid BucketListCreateRequest request) {
		bucketListService.createBucketList(userId, request);
	}
}
