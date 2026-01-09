package com.dev.wistlist_app.domain.bucket.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.dev.wistlist_app.domain.bucket.dto.BucketRequestDto.BucketItemCreateRequest;
import com.dev.wistlist_app.domain.bucket.dto.BucketRequestDto.BucketItemStatusRequest;
import com.dev.wistlist_app.domain.bucket.dto.BucketResponseDto.BucketItemResponse;
import com.dev.wistlist_app.domain.bucket.service.BucketItemService;
import com.dev.wistlist_app.domain.cheer.service.CheerService;
import com.dev.wistlist_app.global.annotation.Login;
import com.dev.wistlist_app.global.response.ApiResponse;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/bucket-items")
@RequiredArgsConstructor
public class BucketItemApiController {

	private final BucketItemService bucketItemService;
	private final CheerService cheerService;

	@GetMapping
	public ApiResponse<List<BucketItemResponse>> getAllBucketItems() {
		return ApiResponse.success(bucketItemService.getAllBucketItems());
	}

	@GetMapping("/search")
	public ApiResponse<List<BucketItemResponse>> getAllBucketItemBySearch(
		@RequestParam(required = false) String keyword) {
		return ApiResponse.success(bucketItemService.getAllBucketItemBySearch(keyword));
	}

	@GetMapping("/{listId}/items/{bucketId}")
	public ApiResponse<BucketItemResponse> getMyBucketItem(@Login Long userId, @PathVariable Long listId,
		@PathVariable Long bucketId) {
		return ApiResponse.success(bucketItemService.getMyBucketItem(userId, listId, bucketId));
	}

	@PostMapping("/{listId}")
	public void createBucketItem(@Login Long userId, @PathVariable Long listId, @RequestBody @Valid
	BucketItemCreateRequest request) {
		bucketItemService.createBucketItem(userId, listId, request);
	}

	@PatchMapping("/{listId}/items/{bucketId}")
	public void updateBucketItem(@Login Long userId, @PathVariable Long listId,
		@PathVariable Long bucketId,
		@RequestBody @Valid BucketItemCreateRequest request) {
		bucketItemService.updateBucketItem(userId, listId, bucketId, request);
	}

	@PatchMapping("/{listId}/items/{bucketId}/status")
	public void updateBucketItemStatus(@Login Long userId, @PathVariable Long listId,
		@PathVariable Long bucketId,
		@RequestBody @Valid BucketItemStatusRequest request) {
		bucketItemService.updateBucketItemStatus(userId, listId, bucketId, request);
	}

	@DeleteMapping("/{listId}/items/{bucketId}")
	public void deleteMyBucketItem(@Login Long userId, @PathVariable Long listId,
		@PathVariable Long bucketId) {
		bucketItemService.deleteMyBucketItem(userId, listId, bucketId);
	}

	@PostMapping("/{bucketId}/cheers")
	public void sendCheer(@Login Long userId, @PathVariable Long bucketId) {
		cheerService.sendCheer(userId, bucketId);
	}

	@DeleteMapping("/{bucketId}/cheers")
	public void deleteCheer(@Login Long userId, @PathVariable Long bucketId) {
		cheerService.deleteCheer(userId, bucketId);
	}
}
