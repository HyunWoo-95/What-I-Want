package com.dev.wistlist_app.domain.bucket.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dev.wistlist_app.domain.bucket.dto.BucketRequestDto;
import com.dev.wistlist_app.domain.bucket.dto.BucketResponseDto;
import com.dev.wistlist_app.domain.bucket.dto.BucketResponseDto.BucketItemResponse;
import com.dev.wistlist_app.domain.bucket.dto.BucketResponseDto.BucketListResponse;
import com.dev.wistlist_app.domain.bucket.entity.BucketItem;
import com.dev.wistlist_app.domain.bucket.entity.BucketList;
import com.dev.wistlist_app.domain.bucket.repository.BucketListRepository;
import com.dev.wistlist_app.domain.users.entity.User;
import com.dev.wistlist_app.domain.users.repository.UserRepository;
import com.dev.wistlist_app.global.exception.ErrorCode;
import com.dev.wistlist_app.global.exception.GlobalException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BucketListService {

	private final BucketListRepository bucketListRepo;
	private final UserRepository userRepo;

	@Transactional(readOnly = true)
	public List<BucketListResponse> getMyBucketList(Long userId) {
		User user = userRepo.findById(userId).orElseThrow(() -> new GlobalException(ErrorCode.USER_NOT_FOUND));

		List<BucketList> bucketLists = bucketListRepo.findAllByUser(user);
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
	public List<BucketItemResponse> getMyBucketItems(Long userId, Long listId) {
		User user = userRepo.findById(userId).orElseThrow(() -> new GlobalException(ErrorCode.USER_NOT_FOUND));

		BucketList bucketList = bucketListRepo.findByIdAndUser(listId, user);
		if (bucketList == null) {
			throw new GlobalException(ErrorCode.BUCKETLIST_NOT_FOUND);
		}

		List<BucketItem> bucketItems = bucketList.getBucketItems();
		if (bucketItems.isEmpty()) {
			throw new GlobalException(ErrorCode.BUCKETITEM_NOT_FOUND);
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



	@Transactional
	public void createBucketList(Long userId, BucketRequestDto.BucketListCreateRequest request) {
		User user = userRepo.findById(userId).orElseThrow(() -> new GlobalException(ErrorCode.USER_NOT_FOUND));

		BucketList bucketList = BucketList.builder()
			.user(user)
			.title(request.getTitle())
			.dueDate(request.getDuedate())
			.build();
		bucketListRepo.save(bucketList);
	}
}
