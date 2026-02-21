package com.dev.wistlist_app.domain.cheer.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dev.wistlist_app.domain.bucket.entity.BucketItem;
import com.dev.wistlist_app.domain.cheer.entity.Cheer;
import com.dev.wistlist_app.domain.users.entity.UserProfile;

public interface CheerRepository extends JpaRepository<Cheer, Long> {
	Cheer findByProfileAndBucketItem(UserProfile profile, BucketItem bucketItem);
}
