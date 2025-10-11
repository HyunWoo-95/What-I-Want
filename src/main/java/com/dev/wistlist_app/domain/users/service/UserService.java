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
	private final HttpSession httpSession;

	@Transactional
	public void join(JoinRequest req) {
		User user = User.builder()
			.email(req.getEmail())
			.password(req.getPassword())
			.username(req.getUsername())
			.build();
		userRepo.save(user);
	}

	@Transactional
	public void login(UserRequestDto.LoginRequest req) {
		if (!userRepo.existsByEmail(req.getEmail())) {
			throw new IllegalArgumentException("존재하지 않는 사용자 번호 입니다.");
		}
		if (!userRepo.existsByPassword(req.getPassword())) {
			throw new IllegalArgumentException("패스워드가 일치하지 않습니다.");
		}
		User user = userRepo.findByEmailAndPassword(req.getEmail(), req.getPassword());
		httpSession.setAttribute(LOGIN_USER, user.getId());
	}
}
