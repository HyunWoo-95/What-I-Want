package com.dev.wistlist_app.domain.bucket.service;

import static com.dev.wistlist_app.domain.bucket.dto.BucketResponseDto.*;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dev.wistlist_app.domain.bucket.entity.BucketItemStatus;
import com.dev.wistlist_app.domain.users.entity.User;
import com.dev.wistlist_app.domain.users.repository.UserRepository;
import com.dev.wistlist_app.domain.bucket.dto.BucketRequestDto.BucketItemCreateRequest;
import com.dev.wistlist_app.domain.bucket.dto.BucketRequestDto.BucketItemStatusRequest;
import com.dev.wistlist_app.domain.bucket.entity.BucketItem;
import com.dev.wistlist_app.domain.bucket.entity.BucketList;
import com.dev.wistlist_app.domain.bucket.repository.BucketListRepository;
import com.dev.wistlist_app.domain.bucket.repository.BucketItemRepository;
import com.dev.wistlist_app.global.exception.ErrorCode;
import com.dev.wistlist_app.global.exception.GlobalException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BucketItemService {
	private final BucketListRepository bucketListRepo;
	private final BucketItemRepository bucketRepo;
	private final UserRepository userRepo;

	@Transactional(readOnly = true)
	public BucketItemResponse getMyBucketItem(Long userId, Long listId, Long bucketId) {
		User user = userRepo.findById(userId).orElseThrow(() -> new GlobalException(ErrorCode.USER_NOT_FOUND));

		BucketList bucketList = bucketListRepo.findByIdAndUser(listId, user);
		if (bucketList == null) {
			throw new GlobalException(ErrorCode.BUCKETLIST_NOT_FOUND);
		}

		BucketItem bucketItem = bucketRepo.findByIdAndBucketList(bucketId, bucketList);
		if (bucketItem == null) {
			throw new GlobalException(ErrorCode.BUCKETITEM_NOT_FOUND);
		}
		return BucketItemResponse.builder()
			.content(bucketItem.getContent())
			.status(bucketItem.getStatus())
			.createdAt(bucketItem.getCreatedAt())
			.updatedAt(bucketItem.getUpdatedAt())
			.build();
	}

	@Transactional
	public void createBucketItem(Long userId, Long listId, BucketItemCreateRequest request) {
		User user = userRepo.findById(userId).orElseThrow(() -> new GlobalException(ErrorCode.USER_NOT_FOUND));

		BucketList bucketList = bucketListRepo.findById(listId)
			.orElseThrow(() -> new GlobalException(ErrorCode.BUCKETLIST_NOT_FOUND));
		if (!bucketList.getUser().equals(user)) {
			throw new GlobalException(ErrorCode.UNAUTHORIZED);
		}

		BucketItem bucketItem = BucketItem.builder().user(user)
			.bucketList(bucketList)
			.content(request.getContent())
			.build();
		bucketRepo.save(bucketItem);
	}

	@Transactional
	public void updateBucketItem(Long userId, Long listId, Long bucketId, BucketItemCreateRequest request) {
		User user = userRepo.findById(userId).orElseThrow(() -> new GlobalException(ErrorCode.USER_NOT_FOUND));

		BucketList bucketList = bucketListRepo.findById(listId)
			.orElseThrow(() -> new GlobalException(ErrorCode.BUCKETLIST_NOT_FOUND));
		if (!bucketList.getUser().equals(user)) {
			throw new GlobalException(ErrorCode.UNAUTHORIZED);
		}

		BucketItem bucketItem = bucketRepo.findById(bucketId)
			.orElseThrow(() -> new GlobalException(ErrorCode.BUCKETITEM_NOT_FOUND));
		bucketItem.updateBucketItem(request.getContent());
	}

	@Transactional
	public void updateBucketItemStatus(Long userId, Long listId, Long bucketId, BucketItemStatusRequest request) {
		User user = userRepo.findById(userId).orElseThrow(() -> new GlobalException(ErrorCode.USER_NOT_FOUND));

		BucketList bucketList = bucketListRepo.findById(listId)
			.orElseThrow(() -> new GlobalException(ErrorCode.BUCKETLIST_NOT_FOUND));
		if (!bucketList.getUser().equals(user)) {
			throw new GlobalException(ErrorCode.UNAUTHORIZED);
		}

		BucketItem bucketItem = bucketRepo.findById(bucketId)
			.orElseThrow(() -> new GlobalException(ErrorCode.BUCKETITEM_NOT_FOUND));
		bucketItem.updatebucketItemStatus(request.getStatus());
	}

	@Transactional
	public void deleteMyBucketItem(Long userId, Long listId, Long bucketId) {
		User user = userRepo.findById(userId).orElseThrow(() -> new GlobalException(ErrorCode.USER_NOT_FOUND));

		BucketList bucketList = bucketListRepo.findByIdAndUser(listId, user);
		if (bucketList == null) {
			throw new GlobalException(ErrorCode.BUCKETLIST_NOT_FOUND);
		}

		BucketItem bucketItem = bucketRepo.findByIdAndBucketList(bucketId, bucketList);
		if (bucketItem == null) {
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
					.bucketId(bucketItem.getId())
					.content(bucketItem.getContent())
					.status(bucketItem.getStatus())
					.createdAt(bucketItem.getCreatedAt())
					.updatedAt(bucketItem.getUpdatedAt())
					.build()
			);
		}
		return res;
	}
	@Transactional(readOnly = true)
	public List<BucketItemResponse> getAllBucketItemBySearch(String content) {
		List<BucketItem> bucketItems = bucketRepo.searchBucketItems(content);
		List<BucketItemResponse> res = new ArrayList<>();
		for (BucketItem bucketItem : bucketItems) {
			res.add(
				BucketItemResponse.builder()
					.bucketId(bucketItem.getId())
					.content(bucketItem.getContent())
					.status(bucketItem.getStatus())
					.createdAt(bucketItem.getCreatedAt())
					.updatedAt(bucketItem.getUpdatedAt())
					.build()
			);
		}
		return res;
	}
}
