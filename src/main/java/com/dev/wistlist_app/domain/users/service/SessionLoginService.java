package com.dev.wistlist_app.domain.users.service;

import static com.dev.wistlist_app.global.constant.SessionConst.*;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dev.wistlist_app.domain.users.dto.UserRequestDto.LoginRequest;
import com.dev.wistlist_app.domain.users.entity.User;
import com.dev.wistlist_app.domain.users.repository.UserRepository;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class SessionLoginService {

	private final UserRepository userRepo;
	private final HttpSession httpSession;
	private final BCryptPasswordEncoder encoder;

	@Transactional
	public void login(LoginRequest req) {
		User user = userRepo.findByEmail(req.getEmail())
			.orElseThrow(() -> new IllegalArgumentException("회원을 찾을 수 없습니다."));
		String encode = encoder.encode(req.getPassword());
		if (!encoder.matches(req.getPassword(), user.getPassword())) {
			throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
		}
		httpSession.setAttribute(LOGIN_USER, user.getId());
	}
}