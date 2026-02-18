package com.dev.wistlist_app.domain.cheer.service;

import static com.dev.wistlist_app.domain.cheer.entity.Cheer.*;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dev.wistlist_app.domain.bucket.entity.BucketItem;
import com.dev.wistlist_app.domain.bucket.repository.BucketItemRepository;
import com.dev.wistlist_app.domain.cheer.entity.Cheer;
import com.dev.wistlist_app.domain.cheer.repository.CheerRepository;
import com.dev.wistlist_app.domain.users.entity.User;
import com.dev.wistlist_app.domain.users.entity.UserProfile;
import com.dev.wistlist_app.domain.users.repository.UserProfileRepository;
import com.dev.wistlist_app.domain.users.repository.UserRepository;
import com.dev.wistlist_app.global.exception.ErrorCode;
import com.dev.wistlist_app.global.exception.GlobalException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CheerService {

	private final CheerRepository cheerRepo;
	private final BucketItemRepository bucketRepo;
	private final UserRepository userRepo;
	private final UserProfileRepository profileRepo;

	@Transactional
	public void sendCheer(Long userId, Long bucketId) {
		User user = userRepo.findById(userId).orElseThrow(() -> new GlobalException(ErrorCode.USER_NOT_FOUND));
		UserProfile profile = profileRepo.findByUser(user)
			.orElseThrow(() -> new GlobalException(ErrorCode.USER_NOT_FOUND));

		BucketItem bucketItem = bucketRepo.findById(bucketId)
			.orElseThrow(() -> new GlobalException(ErrorCode.BUCKETITEM_NOT_FOUND));

		cheerRepo.save(new Cheer(profile, bucketItem));
	}

	@Transactional
	public void deleteCheer(Long userId, Long bucketId) {
		User user = userRepo.findById(userId).orElseThrow(() -> new GlobalException(ErrorCode.USER_NOT_FOUND));
		UserProfile profile = profileRepo.findByUser(user)
			.orElseThrow(() -> new GlobalException(ErrorCode.USER_NOT_FOUND));
		BucketItem bucketItem = bucketRepo.findById(bucketId)
			.orElseThrow(() -> new GlobalException(ErrorCode.BUCKETITEM_NOT_FOUND));
		Cheer cheer = cheerRepo.findByProfileAndBucketItem(profile, bucketItem);
		cheerRepo.delete(cheer);
		bucketItem.decrementCheerCount();
	}
}
