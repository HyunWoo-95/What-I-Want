package com.dev.wistlist_app.domain.users.controller;

import static com.dev.wistlist_app.domain.users.dto.UserResponseDto.*;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dev.wistlist_app.domain.users.dto.UserRequestDto;
import com.dev.wistlist_app.domain.users.dto.UserRequestDto.JoinRequest;
import com.dev.wistlist_app.domain.users.dto.UserRequestDto.LoginRequest;
import com.dev.wistlist_app.domain.users.dto.UserRequestDto.ProfileRequest;
import com.dev.wistlist_app.domain.users.dto.UserResponseDto;
import com.dev.wistlist_app.domain.users.service.UserService;
import com.dev.wistlist_app.domain.users.service.SessionLoginService;
import com.dev.wistlist_app.global.annotation.Login;
import com.dev.wistlist_app.global.response.ApiResponse;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Validated
@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserApiController {

	private final UserService userService;
	private final SessionLoginService loginService;

	@PostMapping("/join")
	public void addUser(@RequestBody @Valid JoinRequest req) {
		userService.join(req);
	}

	@PostMapping("/login")
	public void login(@RequestBody @Valid LoginRequest req) {
		loginService.login(req);
	}

	@GetMapping("/profile")
	public ApiResponse<ProfileRespone> getProfile(@Login Long userId) {
		return ApiResponse.success(userService.getProfile(userId));
	}

	@PostMapping("/profile")
	public void saveProfile(@Login Long userId, @RequestBody ProfileRequest req) {
		userService.saveProfile(userId, req);
	}
}
