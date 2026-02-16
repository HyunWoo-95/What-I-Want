package com.dev.wistlist_app.domain.users.controller;

import static com.dev.wistlist_app.domain.users.dto.UserResponseDto.*;

import java.util.List;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.dev.wistlist_app.domain.follow.service.FollowService;
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
	private final FollowService followService;

	@PostMapping("/join")
	public void addUser(@RequestBody @Valid JoinRequest req) {
		userService.join(req);
	}

	@PostMapping("/login")
	public void login(@RequestBody @Valid LoginRequest req) {
		loginService.login(req);
	}

	@GetMapping("/me/profile")
	public ApiResponse<ProfileResponse> getMyProfile(@Login Long userId) {
		return ApiResponse.success(userService.getMyProfile(userId));
	}

	@PostMapping("/me/profile")
	public void saveProfile(@Login Long userId, @RequestBody ProfileRequest req) {
		userService.saveProfile(userId, req);
	}

	@PatchMapping("/me/profile")
	public ApiResponse<String> updateProfile(@Login Long userId, @RequestParam String profileImgUrl) {
		return ApiResponse.success(userService.updateProfileImg(userId, profileImgUrl));
	}

	@GetMapping("/{profileId}/prfoile")
	public ApiResponse<ProfileResponse> getProfile(@Login Long userId, @PathVariable Long profileId) {
		return ApiResponse.success(userService.getProfile(profileId));
	}

	@PostMapping("/{profileId}/follow")
	public void followUser(@Login Long userId, @PathVariable Long profileId) {
		followService.sendFollow(userId, profileId);
	}

	@DeleteMapping("/{profileId}/follow")
	public void unfollowUser(@Login Long userId, @PathVariable Long profileId) {
		followService.unFollow(userId, profileId);
	}

	@GetMapping("/follower")
	public ApiResponse<List<FollowResponseDto>> getFollowers(@Login Long userId) {
		return ApiResponse.success(followService.getFollowers(userId));
	}

	@GetMapping("/following")
	public ApiResponse<List<FollowResponseDto>> getFollowings(@Login Long userId) {
		return ApiResponse.success(followService.getFollowings(userId));
	}
}
