package com.dev.wistlist_app.domain.follow.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dev.wistlist_app.domain.follow.entity.Follow;
import com.dev.wistlist_app.domain.follow.repository.FollowRepository;
import com.dev.wistlist_app.domain.users.dto.UserResponseDto.FollowResponseDto;
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

	@Transactional
	public void unFollow(Long userId, Long targetUserId) {
		User user = userRepo.findById(userId).orElseThrow(() -> new GlobalException(ErrorCode.USER_NOT_FOUND));
		User tagetUser = userRepo.findById(targetUserId)
			.orElseThrow(() -> new GlobalException(ErrorCode.USER_NOT_FOUND));

		UserProfile follower = profileRepo.findByUser(user)
			.orElseThrow(() -> new GlobalException(ErrorCode.USER_NOT_FOUND));
		UserProfile following = profileRepo.findByUser(tagetUser)
			.orElseThrow(() -> new GlobalException(ErrorCode.USER_NOT_FOUND));
		if (!followRepo.existsByFollowerAndFollowing(follower, following)) {
			//TODO : 적합한 예외처리로 변경
			throw new GlobalException(ErrorCode.COMMENT_NOT_FOUND);
		}
		Follow follow = followRepo.findByFollowerAndFollowing(follower, following);
		followRepo.delete(follow);
	}

	@Transactional(readOnly = true)
	public List<FollowResponseDto> getFollowers(Long userId) {
		User user = userRepo.findById(userId).orElseThrow(() -> new GlobalException(ErrorCode.USER_NOT_FOUND));
		UserProfile me = profileRepo.findByUser(user)
			.orElseThrow(() -> new GlobalException(ErrorCode.USER_NOT_FOUND));
		List<Follow> followers = me.getFollowers();
		return followers.stream().map(this::toFollowerResponseDto).toList();
	}

	@Transactional(readOnly = true)
	public List<FollowResponseDto> getFollowings(Long userId) {
		User user = userRepo.findById(userId).orElseThrow(() -> new GlobalException(ErrorCode.USER_NOT_FOUND));
		UserProfile me = profileRepo.findByUser(user)
			.orElseThrow(() -> new GlobalException(ErrorCode.USER_NOT_FOUND));
		List<Follow> followings = me.getFollowIngs();
		return followings.stream().map(this::toFollowIngResponseDto).toList();
	}

	private FollowResponseDto toFollowerResponseDto(Follow follow) {
		return FollowResponseDto.builder()
			.profileId(follow.getFollower().getId())
			.nickname(follow.getFollower().getNickname())
			.profileImgUrl(follow.getFollower().getProfileUrl())
			.build();
	}

	private FollowResponseDto toFollowIngResponseDto(Follow follow) {
		return FollowResponseDto.builder()
			.profileId(follow.getFollowing().getId())
			.nickname(follow.getFollowing().getNickname())
			.profileImgUrl(follow.getFollowing().getProfileUrl())
			.build();
	}

}
