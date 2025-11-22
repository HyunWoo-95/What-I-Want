package com.dev.wistlist_app.domain.wish.service;

import static com.dev.wistlist_app.domain.wish.dto.WishResponseDto.*;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dev.wistlist_app.domain.users.entity.User;
import com.dev.wistlist_app.domain.users.repository.UserRepository;
import com.dev.wistlist_app.domain.wish.dto.WishRequestDto.WishListRequest;
import com.dev.wistlist_app.domain.wish.dto.WishRequestDto.WishRequest;
import com.dev.wistlist_app.domain.wish.dto.WishRequestDto.WishStatusRequest;
import com.dev.wistlist_app.domain.wish.entity.Wish;
import com.dev.wistlist_app.domain.wish.entity.WishList;
import com.dev.wistlist_app.domain.wish.repository.WishListRepository;
import com.dev.wistlist_app.domain.wish.repository.WishRepository;
import com.dev.wistlist_app.global.exception.ErrorCode;
import com.dev.wistlist_app.global.exception.GlobalException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class WishService {
	private final WishListRepository wishListRepo;
	private final WishRepository wishRepo;
	private final UserRepository userRepo;

	@Transactional(readOnly = true)
	public List<WishListResponse> getMyWishList(Long userId) {
		User user = userRepo.findById(userId).orElseThrow(() -> new GlobalException(ErrorCode.USER_NOT_FOUND));

		List<WishList> wishLists = wishListRepo.findAllByUser(user);
		List<WishListResponse> res = new ArrayList<>();
		for (WishList wishList : wishLists) {
			res.add(
				WishListResponse.builder()
					.listId(wishList.getId())
					.userId(user.getId())
					.title(wishList.getTitle())
					.dueDate(wishList.getDueDate())
					.build()
			);
		}
		return res;
	}

	@Transactional(readOnly = true)
	public List<WishResponse> getMyWishes(Long userId, Long listId) {
		User user = userRepo.findById(userId).orElseThrow(() -> new GlobalException(ErrorCode.USER_NOT_FOUND));

		WishList wishList = wishListRepo.findByIdAndUser(listId, user);
		if (wishList == null) {
			throw new GlobalException(ErrorCode.WISHLIST_NOT_FOUND);
		}

		List<Wish> wishes = wishList.getWishes();
		if (wishes.isEmpty()) {
			throw new GlobalException(ErrorCode.WISH_NOT_FOUND);
		}
		List<WishResponse> res = new ArrayList<>();
		for (Wish wish : wishes) {
			res.add(WishResponse.builder()
				.wishId(wish.getId())
				.content(wish.getContent())
				.createdAt(wish.getCreatedAt())
				.build());
		}
		return res;
	}

	@Transactional(readOnly = true)
	public WishResponse getMyWish(Long userId, Long listId, Long wishId) {
		User user = userRepo.findById(userId).orElseThrow(() -> new GlobalException(ErrorCode.USER_NOT_FOUND));

		WishList wishList = wishListRepo.findByIdAndUser(listId, user);
		if (wishList == null) {
			throw new GlobalException(ErrorCode.WISHLIST_NOT_FOUND);
		}

		Wish wish = wishRepo.findByIdAndWishList(wishId, wishList);
		if (wish == null) {
			throw new GlobalException(ErrorCode.WISH_NOT_FOUND);
		}
		return WishResponse.builder()
			.content(wish.getContent())
			.status(wish.getStatus())
			.createdAt(wish.getCreatedAt())
			.updatedAt(wish.getUpdatedAt())
			.build();
	}

	@Transactional
	public void createWishList(Long userId, WishListRequest request) {
		User user = userRepo.findById(userId).orElseThrow(() -> new GlobalException(ErrorCode.USER_NOT_FOUND));

		WishList wishes = WishList.builder()
			.user(user)
			.title(request.getTitle())
			.dueDate(request.getDuedate())
			.build();
		wishListRepo.save(wishes);
	}

	@Transactional
	public void createWish(Long userId, Long listId, WishRequest request) {
		User user = userRepo.findById(userId).orElseThrow(() -> new GlobalException(ErrorCode.USER_NOT_FOUND));

		WishList wishList = wishListRepo.findById(listId)
			.orElseThrow(() -> new GlobalException(ErrorCode.WISHLIST_NOT_FOUND));
		if (!wishList.getUser().equals(user)) {
			throw new GlobalException(ErrorCode.UNAUTHORIZED);
		}

		Wish wish = Wish.builder().user(user)
			.wishList(wishList)
			.content(request.getContent())
			.build();
		wishRepo.save(wish);
	}

	@Transactional
	public void updateWish(Long userId, Long listId, Long wishId, WishRequest request) {
		User user = userRepo.findById(userId).orElseThrow(() -> new GlobalException(ErrorCode.USER_NOT_FOUND));

		WishList wishList = wishListRepo.findById(listId)
			.orElseThrow(() -> new GlobalException(ErrorCode.WISHLIST_NOT_FOUND));
		if (!wishList.getUser().equals(user)) {
			throw new GlobalException(ErrorCode.UNAUTHORIZED);
		}

		Wish wish = wishRepo.findById(wishId).orElseThrow(() -> new GlobalException(ErrorCode.WISH_NOT_FOUND));
		wish.updateWish(request.getContent());
	}

	@Transactional
	public void updateWishStatus(Long userId, Long listId, Long wishId, WishStatusRequest request) {
		User user = userRepo.findById(userId).orElseThrow(() -> new GlobalException(ErrorCode.USER_NOT_FOUND));

		WishList wishList = wishListRepo.findById(listId)
			.orElseThrow(() -> new GlobalException(ErrorCode.WISHLIST_NOT_FOUND));
		if (!wishList.getUser().equals(user)) {
			throw new GlobalException(ErrorCode.UNAUTHORIZED);
		}

		Wish wish = wishRepo.findById(wishId).orElseThrow(() -> new GlobalException(ErrorCode.WISH_NOT_FOUND));
		wish.updateWishStatus(request.getStatus());
	}

	@Transactional
	public void deleteMyWish(Long userId, Long listId, Long wishId) {
		User user = userRepo.findById(userId).orElseThrow(() -> new GlobalException(ErrorCode.USER_NOT_FOUND));

		WishList wishList = wishListRepo.findByIdAndUser(listId, user);
		if (wishList == null) {
			throw new GlobalException(ErrorCode.WISHLIST_NOT_FOUND);
		}

		Wish wish = wishRepo.findByIdAndWishList(wishId, wishList);
		if (wish == null) {
			throw new GlobalException(ErrorCode.WISH_NOT_FOUND);
		}
		wishRepo.deleteById(wishId);
	}
}
