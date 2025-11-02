package com.dev.wistlist_app.domain.users.service;

import static com.dev.wistlist_app.global.constant.SessionConst.*;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dev.wistlist_app.domain.users.dto.UserRequestDto.LoginRequest;
import com.dev.wistlist_app.domain.users.entity.User;
import com.dev.wistlist_app.domain.users.repository.UserRepository;
import com.dev.wistlist_app.global.constant.SessionConst;
import com.dev.wistlist_app.global.encrytion.SHA256EncryptionService;
import com.dev.wistlist_app.global.exception.ErrorCode;
import com.dev.wistlist_app.global.exception.GlobalException;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class SessionLoginService {

	private final UserRepository userRepo;
	private final HttpSession httpSession;
	private final SHA256EncryptionService encoder;

	@Transactional
	public void login(LoginRequest req) {
		User user = userRepo.findByEmail(req.getEmail())
			.orElseThrow(() -> new GlobalException(ErrorCode.EMAIL_NOT_FOUND));
		String encode = encoder.encode(req.getPassword());
		if (!encode.equals(user.getPassword())) {
			throw new GlobalException(ErrorCode.PASSWORD_MISS_MATCH);
		}
		httpSession.setAttribute(LOGIN_USER, user.getId());

	}

	public Long getLoginUser() {
		log.info(String.valueOf(httpSession.getAttribute(SessionConst.LOGIN_USER)));
		return (Long)httpSession.getAttribute(LOGIN_USER);
	}
}