package com.dev.wistlist_app.domain.bucket.service;

import static com.dev.wistlist_app.domain.bucket.dto.BucketResponseDto.*;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dev.wistlist_app.domain.users.entity.User;
import com.dev.wistlist_app.domain.users.entity.UserProfile;
import com.dev.wistlist_app.domain.users.repository.UserProfileRepository;
import com.dev.wistlist_app.domain.users.repository.UserRepository;
import com.dev.wistlist_app.domain.bucket.dto.BucketRequestDto.BucketItemCreateRequest;
import com.dev.wistlist_app.domain.bucket.dto.BucketRequestDto.BucketItemStatusRequest;
import com.dev.wistlist_app.domain.bucket.entity.BucketItem;
import com.dev.wistlist_app.domain.bucket.repository.BucketItemRepository;
import com.dev.wistlist_app.global.exception.ErrorCode;
import com.dev.wistlist_app.global.exception.GlobalException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BucketItemService {
	private final BucketItemRepository bucketRepo;
	private final UserRepository userRepo;
	private final UserProfileRepository profileRepo;

	@Transactional
	public void createBucketItem(Long userId, BucketItemCreateRequest request) {
		User user = userRepo.findById(userId).orElseThrow(() -> new GlobalException(ErrorCode.USER_NOT_FOUND));
		UserProfile profile = profileRepo.findByUser(user)
			.orElseThrow(() -> new GlobalException(ErrorCode.USER_NOT_FOUND));

		BucketItem bucketItem = BucketItem.builder()
			.profile(profile)
			.content(request.getContent())
			.category(request.getBucketCategory())
			.dueDate(request.getDueDate())
			.build();
		bucketRepo.save(bucketItem);
	}

	@Transactional(readOnly = true)
	public List<BucketItemResponse> getMyBucketItemList(Long userId) {
		User user = userRepo.findById(userId).orElseThrow(() -> new GlobalException(ErrorCode.USER_NOT_FOUND));
		UserProfile profile = profileRepo.findByUser(user)
			.orElseThrow(() -> new GlobalException(ErrorCode.USER_NOT_FOUND));
		List<BucketItem> items = bucketRepo.findAllByProfile(profile);
		List<BucketItemResponse> res = new ArrayList<>();
		for (BucketItem bucketItem : items) {
			res.add(BucketItemResponse.builder()
				.bucketId(bucketItem.getId())
				.content(bucketItem.getContent())
				.createdAt(bucketItem.getCreatedAt())
				.build());
		}
		return res;
	}

	@Transactional(readOnly = true)
	public BucketItemResponse getMyBucketItem(Long userId, Long bucketId) {
		User user = userRepo.findById(userId).orElseThrow(() -> new GlobalException(ErrorCode.USER_NOT_FOUND));
		UserProfile profile = profileRepo.findByUser(user)
			.orElseThrow(() -> new GlobalException(ErrorCode.USER_NOT_FOUND));
		BucketItem bucketItem = bucketRepo.findByIdAndProfile(bucketId, profile).orElseThrow(() ->
			new GlobalException(ErrorCode.BUCKETITEM_NOT_FOUND));

		return BucketItemResponse.builder()
			.profileId(bucketItem.getProfile().getId())
			.nickname(bucketItem.getProfile().getNickname())
			.content(bucketItem.getContent())
			.status(bucketItem.getStatus())
			.dueDate(bucketItem.getDueDate())
			.createdAt(bucketItem.getCreatedAt())
			.updatedAt(bucketItem.getUpdatedAt())
			.build();
	}

	@Transactional
	public void updateBucketItem(Long userId, Long bucketId, BucketItemCreateRequest request) {
		User user = userRepo.findById(userId).orElseThrow(() -> new GlobalException(ErrorCode.USER_NOT_FOUND));
		UserProfile profile = profileRepo.findByUser(user)
			.orElseThrow(() -> new GlobalException(ErrorCode.USER_NOT_FOUND));
		BucketItem bucketItem = bucketRepo.findByIdAndProfile(bucketId, profile).orElseThrow(() ->
			new GlobalException(ErrorCode.BUCKETITEM_NOT_FOUND));

		bucketItem.updateBucketItem(request.getContent());
	}

	@Transactional
	public void updateBucketItemStatus(Long userId, Long bucketId, BucketItemStatusRequest request) {
		User user = userRepo.findById(userId).orElseThrow(() -> new GlobalException(ErrorCode.USER_NOT_FOUND));
		UserProfile profile = profileRepo.findByUser(user)
			.orElseThrow(() -> new GlobalException(ErrorCode.USER_NOT_FOUND));
		BucketItem bucketItem = bucketRepo.findByIdAndProfile(bucketId, profile)
			.orElseThrow(() -> new GlobalException(ErrorCode.BUCKETITEM_NOT_FOUND));
		bucketItem.updateBucketItemStatus(request.getStatus());
	}

	@Transactional
	public void deleteMyBucketItem(Long userId, Long bucketId) {
		User user = userRepo.findById(userId).orElseThrow(() -> new GlobalException(ErrorCode.USER_NOT_FOUND));
		UserProfile profile = profileRepo.findByUser(user)
			.orElseThrow(() -> new GlobalException(ErrorCode.USER_NOT_FOUND));

		if (!bucketRepo.existsByIdAndProfile(bucketId, profile)) {
			throw new GlobalException(ErrorCode.BUCKETITEM_NOT_FOUND);
		}
		bucketRepo.deleteById(bucketId);
	}

	@Transactional(readOnly = true)
	public List<BucketItemResponse> getAllBucketItems() {
		List<BucketItem> bucketItems = bucketRepo.findAll();
		List<BucketItemResponse> res = new ArrayList<>();
		for (BucketItem bucketItem : bucketItems) {
			res.add(
				BucketItemResponse.builder()
					.profileId(bucketItem.getProfile().getId())
					.nickname(bucketItem.getProfile().getNickname())
					.bucketId(bucketItem.getId())
					.content(bucketItem.getContent())
					.status(bucketItem.getStatus())
					.cheerCount(bucketItem.getCheerCount())
					.commentCount(bucketItem.getCommentCount())
					.createdAt(bucketItem.getCreatedAt())
					.updatedAt(bucketItem.getUpdatedAt())
					.build()
			);
		}
		return res;
	}

	@Transactional(readOnly = true)
	public List<BucketItemResponse> getAllBuckItemPageBySearch(String content) {
		List<BucketItem> bucketItems = bucketRepo.searchBucketItems(content);

		return bucketItems.stream().map(this::toBucketItemResponse).toList();
	}

	private BucketItemResponse toBucketItemResponse(BucketItem bucketItem) {
		return BucketItemResponse.builder()
			.profileId(bucketItem.getProfile().getId())
			.nickname(bucketItem.getProfile().getNickname())
			.bucketId(bucketItem.getId())
			.content(bucketItem.getContent())
			.status(bucketItem.getStatus())
			.cheerCount(bucketItem.getCheerCount())
			.commentCount(bucketItem.getCommentCount())
			.createdAt(bucketItem.getCreatedAt())
			.updatedAt(bucketItem.getUpdatedAt())
			.build();
	}
}
