package com.dev.wistlist_app;

import static com.dev.wistlist_app.global.constant.SessionConstant.*;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dev.wistlist_app.UserRequestDto.JoinRequest;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

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
		UserProfile profile = UserProfile.builder()
			.user(user)
			.nickname(req.getNickname())
			.interest(req.getInterest().getName())
			.build();
		userProfileRepo.save(profile);
	}

	@Transactional
	public void lgoin(UserRequestDto.LoginRequest req) {
		if (!userRepo.findByEmail(req.getEmail())) {
			throw new IllegalArgumentException("존재하지 않는 사용자 번호 입니다.");
		}
		if (!userRepo.findByPassword(req.getPassword())) {
			throw new IllegalArgumentException("패스워드가 일치하지 않습니다.");
		}
		User user = userRepo.findByEmailAndPassword(req.getEmail(), req.getPassword());
		httpSession.setAttribute(LOGIN_USER, user.getId());
	}
}
