package com.dev.wistlist_app.service;

import static org.assertj.core.api.Assertions.*;

import java.util.List;

import org.assertj.core.api.Assertions;
import org.hibernate.AssertionFailure;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.dev.wistlist_app.domain.users.dto.UserRequestDto;
import com.dev.wistlist_app.domain.users.dto.UserRequestDto.JoinRequest;
import com.dev.wistlist_app.domain.users.entity.Interest;
import com.dev.wistlist_app.domain.users.entity.User;
import com.dev.wistlist_app.domain.users.entity.UserProfile;
import com.dev.wistlist_app.domain.users.repository.UserProfileRepository;
import com.dev.wistlist_app.domain.users.repository.UserRepository;
import com.dev.wistlist_app.domain.users.service.UserService;
import com.dev.wistlist_app.global.encrytion.EncryptPasswordEncoder;
import com.dev.wistlist_app.global.exception.ErrorCode;
import com.dev.wistlist_app.global.exception.GlobalException;
@SpringBootTest
public class UserServiceTest {

	@Autowired
	private UserService userService;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private EncryptPasswordEncoder encryptionService;

	@Autowired
	private UserProfileRepository userProfileRepository;

	@Test
	@DisplayName("중복 회원 존재로 가입에 실패한다.")
	void join_fail() {
		//given
		JoinRequest request = new JoinRequest("test@email.com", "test1234", "조현우");
		userService.join(request);

		//when & then
		Assertions.assertThatExceptionOfType(GlobalException.class)
			.isThrownBy(() -> userService.join(request))
			.satisfies(e ->
				assertThat(e.getErrorCode()).isEqualTo(ErrorCode.DUPLICATED_EMAIL));
	}

	@Test
	@DisplayName("회원가입에 성공한다")
	void join_success() {
		//given
		JoinRequest request = new JoinRequest("test@email.com", "test1234", "조현우");
		userService.join(request);

		User joinedUser = userRepository.findByEmail(request.getEmail())
			.orElseThrow(() -> new AssertionFailure("등록된 회원을 찾을 수 없습니다."));

		assertThat(joinedUser.getEmail()).isEqualTo(request.getEmail());
		assertThat(joinedUser.getPassword()).isEqualTo(encryptionService.encode(request.getPassword()));
		assertThat(joinedUser.getUsername()).isEqualTo(request.getUsername());
	}

	@Test
	@DisplayName("프로필 조회에 실패한다 - 저장된 프로필이 없음")
	void getProfileFail() {
		JoinRequest request = new JoinRequest("test@email.com", "test1234", "조현우");
		userService.join(request);

		User user = userRepository.findByEmail(request.getEmail())
			.orElseThrow(() -> new AssertionFailure("등록된 회원을 찾을 수 없습니다."));

		Assertions.assertThatExceptionOfType(GlobalException.class)
			.isThrownBy(() -> userService.getProfile(user.getId()))
			.satisfies(e ->
				assertThat(e.getErrorCode()).isEqualTo(ErrorCode.PROFILE_NOT_EXIST));
	}

	@Test
	@DisplayName("프로필 저장에 성공한다")
	void saveProfileSuccess() {
		//given
		JoinRequest request = new JoinRequest("test@email.com", "test1234", "조현우");
		userService.join(request);

		User user = userRepository.findByEmail(request.getEmail())
			.orElseThrow(() -> new AssertionFailure("등록된 회원을 찾을 수 없습니다."));

		UserRequestDto.ProfileRequest req = new UserRequestDto.ProfileRequest("test", Interest.HEALTH);
		userService.saveProfile(user.getId(), req);

		UserProfile profile = userProfileRepository.findByUser(user);
		if (profile == null) {
			throw new IllegalArgumentException("등록된 프로필이 없습니다.");
		}
		assertThat(profile.getNickname()).isEqualTo(req.getNickname());
		assertThat(profile.getInterest()).isEqualTo(req.getInterest().getName());
	}

}
