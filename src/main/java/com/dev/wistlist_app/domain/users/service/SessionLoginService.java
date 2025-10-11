package com.dev.wistlist_app.domain.users.service;

import static com.dev.wistlist_app.global.constant.SessionConst.*;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dev.wistlist_app.UserRequestDto.LoginRequest;
import com.dev.wistlist_app.domain.users.entity.User;
import com.dev.wistlist_app.domain.users.repository.UserRepository;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SessionLoginService {

	private final UserRepository userRepo;
	private final HttpSession httpSession;

	@Transactional
	public void login(LoginRequest req) {
		checkedEmail(req.getEmail());
		checkedPassword(req.getPassword());
		User user = userRepo.findByEmailAndPassword(req.getEmail(), req.getPassword());
		httpSession.setAttribute(LOGIN_USER, user.getId());
	}

	@Transactional(readOnly = true)
	public void checkedPassword(String password) {
		if (!userRepo.existsByPassword(password)) {
			throw new IllegalArgumentException("패스워드가 일치하지 않습니다.");
		}
	}

	@Transactional(readOnly = true)
	public void checkedEmail(String email) {
		if (!userRepo.existsByEmail(email)) {
			throw new IllegalArgumentException("존재하지 않는 사용자 번호 입니다.");
		}
	}
}