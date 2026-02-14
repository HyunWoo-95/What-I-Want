package com.dev.wistlist_app.domain.follow.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dev.wistlist_app.domain.follow.entity.Follow;
import com.dev.wistlist_app.domain.follow.repository.FollowRepository;
import com.dev.wistlist_app.domain.users.entity.User;
import com.dev.wistlist_app.domain.users.entity.UserProfile;
import com.dev.wistlist_app.domain.users.repository.UserProfileRepository;
import com.dev.wistlist_app.domain.users.repository.UserRepository;
import com.dev.wistlist_app.global.exception.ErrorCode;
import com.dev.wistlist_app.global.exception.GlobalException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FollowService {
	private final FollowRepository followRepo;
	private final UserRepository userRepo;
	private final UserProfileRepository profileRepo;

	@Transactional
	public void sendFollow(Long userId, Long targetUserId) {
		User user = userRepo.findById(userId).orElseThrow(() -> new GlobalException(ErrorCode.USER_NOT_FOUND));
		User tagetUser = userRepo.findById(targetUserId)
			.orElseThrow(() -> new GlobalException(ErrorCode.USER_NOT_FOUND));

		UserProfile follower = profileRepo.findByUser(user)
			.orElseThrow(() -> new GlobalException(ErrorCode.USER_NOT_FOUND));
		UserProfile following = profileRepo.findByUser(tagetUser)
			.orElseThrow(() -> new GlobalException(ErrorCode.USER_NOT_FOUND));
		if (followRepo.existsByFollowerAndFollowing(follower, following)) {
			//TODO : 적합한 예외처리로 변경
			throw new GlobalException(ErrorCode.COMMENT_NOT_FOUND);
		}
		followRepo.save(
			Follow.builder()
				.follower(follower)
				.following(following)
				.build()
		);
	}
}
