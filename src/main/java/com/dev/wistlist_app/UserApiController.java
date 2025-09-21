package com.dev.wistlist_app;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserApiController {

	@PostMapping("/join")
	public void addUser(String email, String password, String username, String nickname, Interest interest) {

	}

}
