package com.dev.wistlist_app.domain.follow.entity;

import com.dev.wistlist_app.domain.BaseTimeEntity;
import com.dev.wistlist_app.domain.users.entity.UserProfile;
import com.dev.wistlist_app.global.exception.ErrorCode;
import com.dev.wistlist_app.global.exception.GlobalException;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Follow extends BaseTimeEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "follower_id")
	private UserProfile follower; // 주체

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "following_id")
	private UserProfile following; // 대상

	@Builder
	public Follow(UserProfile follower, UserProfile following) {
		if (following.equals(follower)) {
			// TODO : 적합한 예외처리로 변경 핋요
			throw new GlobalException(ErrorCode.BUCKETITEM_NOT_FOUND);
		}
		this.follower = follower;
		this.following = following;
	}

}
