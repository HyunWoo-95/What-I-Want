package com.dev.wistlist_app.domain.users.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dev.wistlist_app.UserRequestDto;
import com.dev.wistlist_app.UserRequestDto.JoinRequest;
import com.dev.wistlist_app.UserRequestDto.LoginRequest;
import com.dev.wistlist_app.domain.users.service.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserApiController {

	private final UserService userService;

	@PostMapping("/join")
	public void addUser(@RequestBody JoinRequest req) {
		userService.join(req);
	}

	@PostMapping("/login")
	public void login(@RequestBody LoginRequest req) {
		userService.login(req);
	}

}
