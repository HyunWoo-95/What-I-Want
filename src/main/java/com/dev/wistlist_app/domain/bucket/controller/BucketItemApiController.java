package com.dev.wistlist_app.domain.bucket.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dev.wistlist_app.domain.bucket.dto.BucketRequestDto;
import com.dev.wistlist_app.domain.bucket.dto.BucketResponseDto.BucketListResponse;
import com.dev.wistlist_app.domain.bucket.dto.BucketResponseDto.BucketItemResponse;
import com.dev.wistlist_app.domain.bucket.service.BucketItemService;
import com.dev.wistlist_app.global.annotation.Login;
import com.dev.wistlist_app.global.response.ApiResponse;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/bucketItems")
@RequiredArgsConstructor
public class BucketItemApiController {

	private final BucketItemService bucketItemService;

	@GetMapping
	public ApiResponse<List<BucketListResponse>> getMyBucketList(@Login Long userId) {
		return ApiResponse.success(bucketItemService.getMyBucketList(userId));
	}

	@GetMapping("/{id}")
	public ApiResponse<List<BucketItemResponse>> getMyBucketItems(@Login Long userId, @PathVariable(name = "id") Long listId) {
		return ApiResponse.success(bucketItemService.getMyBucketItems(userId, listId));
	}

	@GetMapping("/{id}/wishes/{bucketId}")
	public ApiResponse<BucketItemResponse> getMyBucketItem(@Login Long userId, @PathVariable(name = "id") Long listId,
		@PathVariable Long bucketId) {
		return ApiResponse.success(bucketItemService.getMyBucketItem(userId, listId, bucketId));
	}

	@PostMapping
	public void createBucketList(@Login Long userId, @RequestBody @Valid BucketRequestDto.BucketListCreateRequest request) {
		bucketItemService.createBucketList(userId, request);
	}

	@PostMapping("/{id}")
	public void createBucketItem(@Login Long userId, @PathVariable(name = "id") Long listId, @RequestBody @Valid
	BucketRequestDto.BucketItemCreateRequest request) {
		bucketItemService.createBucketItem(userId, listId, request);
	}

	@PatchMapping("/{id}/wishes/{wishId}")
	public void updateBucketItem(@Login Long userId, @PathVariable(name = "id") Long listId,
		@PathVariable Long wishId,
		@RequestBody @Valid BucketRequestDto.BucketItemCreateRequest request) {
		bucketItemService.updateBucketItem(userId, listId, wishId, request);
	}

	@PatchMapping("/{id}/wishes/{bucketId}/status")
	public void updateBucketItemStatus(@Login Long userId, @PathVariable(name = "id") Long listId,
		@PathVariable Long bucketId,
		@RequestBody @Valid BucketRequestDto.BucketItemStatusRequest request) {
		bucketItemService.updateBucketItemStatus(userId, listId, bucketId, request);
	}

	@DeleteMapping("/{id}/wishes/{bucketId}")
	public void deleteMyBucketItem(@Login Long userId, @PathVariable(name = "id") Long listId,
		@PathVariable Long bucketId) {
		bucketItemService.deleteMyBucketItem(userId, listId, bucketId);
	}
}
