package com.dev.wistlist_app.domain.users.controller;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dev.wistlist_app.UserRequestDto.JoinRequest;
import com.dev.wistlist_app.UserRequestDto.LoginRequest;
import com.dev.wistlist_app.domain.users.service.UserService;
import com.dev.wistlist_app.domain.users.service.SessionLoginService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Validated
@RestController
@RequestMapping("/users")
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
}
