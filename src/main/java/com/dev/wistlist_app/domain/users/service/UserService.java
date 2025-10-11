package com.dev.wistlist_app.domain.users.service;

import static com.dev.wistlist_app.global.constant.SessionConst.*;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dev.wistlist_app.domain.users.repository.UserProfileRepository;
import com.dev.wistlist_app.domain.users.repository.UserRepository;
import com.dev.wistlist_app.UserRequestDto;
import com.dev.wistlist_app.UserRequestDto.JoinRequest;
import com.dev.wistlist_app.domain.users.entity.User;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {
	private final UserRepository userRepo;
	private final UserProfileRepository userProfileRepo;


	@Transactional
	public void join(JoinRequest req) {
		User user = User.builder()
			.email(req.getEmail())
			.password(req.getPassword())
			.username(req.getUsername())
			.build();
		userRepo.save(user);
	}
}
