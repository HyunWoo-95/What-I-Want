package com.dev.wistlist_app.domain.wish.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dev.wistlist_app.domain.wish.dto.WishRequestDto;
import com.dev.wistlist_app.domain.wish.service.WishService;
import com.dev.wistlist_app.global.annotation.Login;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/wish")
@RequiredArgsConstructor
public class WishApiController {

	private final WishService wishService;

	@PostMapping
	public void createWishList(@Login Long userId, @RequestBody @Valid WishRequestDto.WishListRequest request) {
		wishService.createWishList(userId, request);
	}


}
