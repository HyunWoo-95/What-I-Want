package com.dev.wistlist_app;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dev.wistlist_app.UserRequestDto.JoinRequest;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserApiController {

	private final UserService userService;
	@PostMapping("/join")
	public void addUser(JoinRequest req) {
		userService.join(req);

	}

}
