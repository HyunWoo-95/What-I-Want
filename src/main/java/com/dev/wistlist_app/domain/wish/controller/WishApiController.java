package com.dev.wistlist_app.domain.wish.controller;

import static com.dev.wistlist_app.domain.wish.dto.WishRequestDto.*;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dev.wistlist_app.domain.wish.dto.WishRequestDto;
import com.dev.wistlist_app.domain.wish.dto.WishRequestDto.WishRequest;
import com.dev.wistlist_app.domain.wish.service.WishService;
import com.dev.wistlist_app.global.annotation.Login;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/wishlists")
@RequiredArgsConstructor
public class WishApiController {

	private final WishService wishService;

	@GetMapping
	public void getMyWishList(@Login Long userId){

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
		@PathVariable(name = "wishId") Long wishId,
		@RequestBody @Valid WishRequest request) {
		wishService.updateWish(userId, listId, wishId, request);
	}

	@PatchMapping("/{id}/wishes/{wishId}")
	public void updateWishStatus(@Login Long userId, @PathVariable(name = "id") Long listId,
		@PathVariable(name = "wishId") Long wishId,
		@RequestBody @Valid WishStatusRequest request) {
		wishService.updateWishStatus(userId, listId, wishId, request);
	}

}
