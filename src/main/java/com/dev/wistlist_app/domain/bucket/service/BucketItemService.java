package com.dev.wistlist_app.domain.bucket.service;

import static com.dev.wistlist_app.domain.bucket.dto.BucketResponseDto.*;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dev.wistlist_app.domain.users.entity.User;
import com.dev.wistlist_app.domain.users.repository.UserRepository;
import com.dev.wistlist_app.domain.bucket.dto.BucketRequestDto.BucketListCreateRequest;
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
	private final BucketListRepository wishListRepo;
	private final BucketItemRepository wishRepo;
	private final UserRepository userRepo;

	@Transactional(readOnly = true)
	public List<BucketListResponse> getMyWishList(Long userId) {
		User user = userRepo.findById(userId).orElseThrow(() -> new GlobalException(ErrorCode.USER_NOT_FOUND));

		List<BucketList> bucketLists = wishListRepo.findAllByUser(user);
		List<BucketListResponse> res = new ArrayList<>();
		for (BucketList bucketList : bucketLists) {
			res.add(
				BucketListResponse.builder()
					.listId(bucketList.getId())
					.userId(user.getId())
					.title(bucketList.getTitle())
					.dueDate(bucketList.getDueDate())
					.build()
			);
		}
		return res;
	}

	@Transactional(readOnly = true)
	public List<BucketItemResponse> getMyWishes(Long userId, Long listId) {
		User user = userRepo.findById(userId).orElseThrow(() -> new GlobalException(ErrorCode.USER_NOT_FOUND));

		BucketList bucketList = wishListRepo.findByIdAndUser(listId, user);
		if (bucketList == null) {
			throw new GlobalException(ErrorCode.WISHLIST_NOT_FOUND);
		}

		List<BucketItem> bucketItems = bucketList.getBucketItems();
		if (bucketItems.isEmpty()) {
			throw new GlobalException(ErrorCode.WISH_NOT_FOUND);
		}
		List<BucketItemResponse> res = new ArrayList<>();
		for (BucketItem bucketItem : bucketItems) {
			res.add(BucketItemResponse.builder()
				.wishId(bucketItem.getId())
				.content(bucketItem.getContent())
				.createdAt(bucketItem.getCreatedAt())
				.build());
		}
		return res;
	}

	@Transactional(readOnly = true)
	public BucketItemResponse getMyWish(Long userId, Long listId, Long wishId) {
		User user = userRepo.findById(userId).orElseThrow(() -> new GlobalException(ErrorCode.USER_NOT_FOUND));

		BucketList bucketList = wishListRepo.findByIdAndUser(listId, user);
		if (bucketList == null) {
			throw new GlobalException(ErrorCode.WISHLIST_NOT_FOUND);
		}

		BucketItem bucketItem = wishRepo.findByIdAndWishList(wishId, bucketList);
		if (bucketItem == null) {
			throw new GlobalException(ErrorCode.WISH_NOT_FOUND);
		}
		return BucketItemResponse.builder()
			.content(bucketItem.getContent())
			.status(bucketItem.getStatus())
			.createdAt(bucketItem.getCreatedAt())
			.updatedAt(bucketItem.getUpdatedAt())
			.build();
	}

	@Transactional
	public void createWishList(Long userId, BucketListCreateRequest request) {
		User user = userRepo.findById(userId).orElseThrow(() -> new GlobalException(ErrorCode.USER_NOT_FOUND));

		BucketList wishes = BucketList.builder()
			.user(user)
			.title(request.getTitle())
			.dueDate(request.getDuedate())
			.build();
		wishListRepo.save(wishes);
	}

	@Transactional
	public void createWish(Long userId, Long listId, BucketItemCreateRequest request) {
		User user = userRepo.findById(userId).orElseThrow(() -> new GlobalException(ErrorCode.USER_NOT_FOUND));

		BucketList bucketList = wishListRepo.findById(listId)
			.orElseThrow(() -> new GlobalException(ErrorCode.WISHLIST_NOT_FOUND));
		if (!bucketList.getUser().equals(user)) {
			throw new GlobalException(ErrorCode.UNAUTHORIZED);
		}

		BucketItem bucketItem = BucketItem.builder().user(user)
			.bucketList(bucketList)
			.content(request.getContent())
			.build();
		wishRepo.save(bucketItem);
	}

	@Transactional
	public void updateWish(Long userId, Long listId, Long wishId, BucketItemCreateRequest request) {
		User user = userRepo.findById(userId).orElseThrow(() -> new GlobalException(ErrorCode.USER_NOT_FOUND));

		BucketList bucketList = wishListRepo.findById(listId)
			.orElseThrow(() -> new GlobalException(ErrorCode.WISHLIST_NOT_FOUND));
		if (!bucketList.getUser().equals(user)) {
			throw new GlobalException(ErrorCode.UNAUTHORIZED);
		}

		BucketItem bucketItem = wishRepo.findById(wishId).orElseThrow(() -> new GlobalException(ErrorCode.WISH_NOT_FOUND));
		bucketItem.updateWish(request.getContent());
	}

	@Transactional
	public void updateWishStatus(Long userId, Long listId, Long wishId, BucketItemStatusRequest request) {
		User user = userRepo.findById(userId).orElseThrow(() -> new GlobalException(ErrorCode.USER_NOT_FOUND));

		BucketList bucketList = wishListRepo.findById(listId)
			.orElseThrow(() -> new GlobalException(ErrorCode.WISHLIST_NOT_FOUND));
		if (!bucketList.getUser().equals(user)) {
			throw new GlobalException(ErrorCode.UNAUTHORIZED);
		}

		BucketItem bucketItem = wishRepo.findById(wishId).orElseThrow(() -> new GlobalException(ErrorCode.WISH_NOT_FOUND));
		bucketItem.updateWishStatus(request.getStatus());
	}

	@Transactional
	public void deleteMyWish(Long userId, Long listId, Long wishId) {
		User user = userRepo.findById(userId).orElseThrow(() -> new GlobalException(ErrorCode.USER_NOT_FOUND));

		BucketList bucketList = wishListRepo.findByIdAndUser(listId, user);
		if (bucketList == null) {
			throw new GlobalException(ErrorCode.WISHLIST_NOT_FOUND);
		}

		BucketItem bucketItem = wishRepo.findByIdAndWishList(wishId, bucketList);
		if (bucketItem == null) {
			throw new GlobalException(ErrorCode.WISH_NOT_FOUND);
		}
		wishRepo.deleteById(wishId);
	}
}
