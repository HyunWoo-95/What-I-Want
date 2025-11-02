package com.dev.wistlist_app.domain.users.service;

import static com.dev.wistlist_app.domain.users.dto.UserRequestDto.*;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dev.wistlist_app.domain.users.dto.UserResponseDto.ProfileRespone;
import com.dev.wistlist_app.domain.users.entity.UserProfile;
import com.dev.wistlist_app.domain.users.repository.UserProfileRepository;
import com.dev.wistlist_app.domain.users.repository.UserRepository;
import com.dev.wistlist_app.domain.users.dto.UserRequestDto.JoinRequest;
import com.dev.wistlist_app.domain.users.entity.User;
import com.dev.wistlist_app.global.encrytion.SHA256EncryptionService;
import com.dev.wistlist_app.global.exception.ErrorCode;
import com.dev.wistlist_app.global.exception.GlobalException;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {
	private final UserRepository userRepo;
	private final UserProfileRepository profileRepo;
	private final SHA256EncryptionService encoder;

	@Transactional
	public void join(JoinRequest req) {
		User user = User.builder()
			.email(req.getEmail())
			.password(encoder.encode(req.getPassword()))
			.username(req.getUsername())
			.build();
		log.info("암호화 비밀번호 : " + encoder.encode(req.getPassword()));
		userRepo.save(user);
	}

	@Transactional(readOnly = true)
	public ProfileRespone getProfile(Long userId) {
		User user = userRepo.findById(userId).orElseThrow(
			() -> new GlobalException(ErrorCode.USER_NOT_FOUND));

		UserProfile profile = profileRepo.findByUser(user);
		return new ProfileRespone(
			profile.getNickname(),
			profile.getInterest(),
			profile.getCreatedAt()
		);
	}

	@Transactional
	public void saveProfile(Long userId, ProfileRequest req) {
		User user = userRepo.findById(userId).orElseThrow(() -> new IllegalArgumentException("일치하는 회원이 없습니다."));
		UserProfile profile = UserProfile.builder()
			.user(user)
			.nickname(req.getNickname())
			.interest(req.getInterest().getName())
			.build();
		profileRepo.save(profile);
	}
}
