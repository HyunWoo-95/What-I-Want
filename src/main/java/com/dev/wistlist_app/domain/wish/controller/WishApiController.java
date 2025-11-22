package com.dev.wistlist_app.domain.wish.controller;

import static com.dev.wistlist_app.domain.wish.dto.WishRequestDto.*;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dev.wistlist_app.domain.wish.dto.WishRequestDto.WishRequest;
import com.dev.wistlist_app.domain.wish.dto.WishResponseDto;
import com.dev.wistlist_app.domain.wish.dto.WishResponseDto.WishListResponse;
import com.dev.wistlist_app.domain.wish.dto.WishResponseDto.WishResponse;
import com.dev.wistlist_app.domain.wish.service.WishService;
import com.dev.wistlist_app.global.annotation.Login;
import com.dev.wistlist_app.global.response.ApiResponse;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/wishlists")
@RequiredArgsConstructor
public class WishApiController {

	private final WishService wishService;

	@GetMapping
	public ApiResponse<List<WishListResponse>> getMyWishList(@Login Long userId) {
		return ApiResponse.success(wishService.getMyWishList(userId));
	}

	@GetMapping("/{id}")
	public ApiResponse<List<WishResponse>> getMyWishes(@Login Long userId, @PathVariable(name = "id") Long listId) {
		return ApiResponse.success(wishService.getMyWishes(userId, listId));
	}

	@GetMapping("/{id}/wishes/{wishId}")
	public ApiResponse<WishResponse> getMyWish(@Login Long userId, @PathVariable(name = "id") Long listId,
		@PathVariable Long wishId) {
		return ApiResponse.success(wishService.getMyWish(userId, listId, wishId));
	}

	@PostMapping
	public void createWishList(@Login Long userId, @RequestBody @Valid WishListRequest request) {
		wishService.createWishList(userId, request);
	}

	@PostMapping("/{id}")
	public void createWish(@Login Long userId, @PathVariable(name = "id") Long listId, @RequestBody @Valid
	WishRequest request) {
		wishService.createWish(userId, listId, request);
	}

	@PatchMapping("/{id}/wishes/{wishId}")
	public void updateWish(@Login Long userId, @PathVariable(name = "id") Long listId,
		@PathVariable Long wishId,
		@RequestBody @Valid WishRequest request) {
		wishService.updateWish(userId, listId, wishId, request);
	}

	@PatchMapping("/{id}/wishes/{wishId}/status")
	public void updateWishStatus(@Login Long userId, @PathVariable(name = "id") Long listId,
		@PathVariable Long wishId,
		@RequestBody @Valid WishStatusRequest request) {
		wishService.updateWishStatus(userId, listId, wishId, request);
	}

	@DeleteMapping("/{id}/wishes/{wishId}")
	public void deleteMyWish(@Login Long userId, @PathVariable(name = "id") Long listId,
		@PathVariable Long wishId) {
		wishService.deleteMyWish(userId, listId, wishId);
	}
}
