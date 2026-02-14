package com.dev.wistlist_app.domain.follow.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dev.wistlist_app.domain.follow.entity.Follow;
import com.dev.wistlist_app.domain.users.entity.UserProfile;

public interface FollowRepository extends JpaRepository<Follow, Long> {

	boolean existsByFollowerAndFollowing(UserProfile follower, UserProfile following);
}
