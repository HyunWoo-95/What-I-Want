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
	public ApiResponse<List<BucketListResponse>> getMyWishList(@Login Long userId) {
		return ApiResponse.success(bucketItemService.getMyWishList(userId));
	}

	@GetMapping("/{id}")
	public ApiResponse<List<BucketItemResponse>> getMyWishes(@Login Long userId, @PathVariable(name = "id") Long listId) {
		return ApiResponse.success(bucketItemService.getMyWishes(userId, listId));
	}

	@GetMapping("/{id}/wishes/{wishId}")
	public ApiResponse<BucketItemResponse> getMyWish(@Login Long userId, @PathVariable(name = "id") Long listId,
		@PathVariable Long wishId) {
		return ApiResponse.success(bucketItemService.getMyWish(userId, listId, wishId));
	}

	@PostMapping
	public void createWishList(@Login Long userId, @RequestBody @Valid BucketRequestDto.BucketListCreateRequest request) {
		bucketItemService.createWishList(userId, request);
	}

	@PostMapping("/{id}")
	public void createWish(@Login Long userId, @PathVariable(name = "id") Long listId, @RequestBody @Valid
	BucketRequestDto.BucketItemCreateRequest request) {
		bucketItemService.createWish(userId, listId, request);
	}

	@PatchMapping("/{id}/wishes/{wishId}")
	public void updateWish(@Login Long userId, @PathVariable(name = "id") Long listId,
		@PathVariable Long wishId,
		@RequestBody @Valid BucketRequestDto.BucketItemCreateRequest request) {
		bucketItemService.updateWish(userId, listId, wishId, request);
	}

	@PatchMapping("/{id}/wishes/{wishId}/status")
	public void updateWishStatus(@Login Long userId, @PathVariable(name = "id") Long listId,
		@PathVariable Long wishId,
		@RequestBody @Valid BucketRequestDto.BucketItemStatusRequest request) {
		bucketItemService.updateWishStatus(userId, listId, wishId, request);
	}

	@DeleteMapping("/{id}/wishes/{wishId}")
	public void deleteMyWish(@Login Long userId, @PathVariable(name = "id") Long listId,
		@PathVariable Long wishId) {
		bucketItemService.deleteMyWish(userId, listId, wishId);
	}
}
