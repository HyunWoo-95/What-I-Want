package com.dev.wistlist_app.domain.wish.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dev.wistlist_app.domain.users.entity.User;
import com.dev.wistlist_app.domain.users.repository.UserRepository;
import com.dev.wistlist_app.domain.wish.dto.WishRequestDto;
import com.dev.wistlist_app.domain.wish.dto.WishRequestDto.WishListRequest;
import com.dev.wistlist_app.domain.wish.dto.WishRequestDto.WishRequest;
import com.dev.wistlist_app.domain.wish.dto.WishRequestDto.WishStatusRequest;
import com.dev.wistlist_app.domain.wish.entity.Wish;
import com.dev.wistlist_app.domain.wish.entity.WishList;
import com.dev.wistlist_app.domain.wish.repository.WishListRepository;
import com.dev.wistlist_app.domain.wish.repository.WishRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class WishService {
	private final WishListRepository wishListRepo;
	private final WishRepository wishRepo;
	private final UserRepository userRepo;

	@Transactional(readOnly = true)
	public void getMyWishList(Long userId) {
		User user = userRepo.findById(userId).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원 입니다."));

		List<WishList> wishLists = wishListRepo.findAllByUser(user);
	}

	@Transactional(readOnly = true)
	public void getMyWishes(Long userId, Long listId) {
		User user = userRepo.findById(userId).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원 입니다."));

		WishList wishList = wishListRepo.findByIdAndUser(listId, user);
		if (wishList == null){
			throw new IllegalArgumentException("위시 리스트가 존재하지 않습니다.");
		}

		List<Wish> wishes = wishList.getWishes();
		if (wishes.isEmpty()) {
			throw new IllegalArgumentException("위시를 작성해주세요.");
		}
	}

	@Transactional(readOnly = true)
	public void getMyWish(Long userId, Long listId, Long wishId){
		User user = userRepo.findById(userId).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원 입니다."));

		WishList wishList = wishListRepo.findByIdAndUser(listId, user);
		if (wishList == null){
			throw new IllegalArgumentException("위시 리스트가 존재하지 않습니다.");
		}

		Wish wish = wishRepo.findByIdAndWishList(wishId, wishList);
		if (wish == null){
			throw new IllegalArgumentException("위시가 존재하지 않습니다.");
		}

	}

	@Transactional
	public void createWishList(Long userId, WishListRequest request) {
		User user = userRepo.findById(userId).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원 입니다."));

		WishList wishes = WishList.builder()
			.user(user)
			.title(request.getTitle())
			.dueDate(request.getDuedate())
			.build();
		wishListRepo.save(wishes);
	}

	@Transactional
	public void createWish(Long userId, Long listId, WishRequest request) {
		User user = userRepo.findById(userId).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원 입니다."));

		WishList wishList = wishListRepo.findById(listId)
			.orElseThrow(() -> new IllegalArgumentException("위시리스트가 존재하지 않습니다."));
		if (!wishList.getUser().equals(user)) {
			throw new IllegalArgumentException("자신의 위시리스트에만 위시를 작성할 수 있습니다.");
		}

		Wish wish = Wish.builder().user(user)
			.wishList(wishList)
			.content(request.getContent())
			.build();
		wishRepo.save(wish);
	}

	@Transactional
	public void updateWish(Long userId, Long listId, Long wishId, WishRequest request) {
		User user = userRepo.findById(userId).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원 입니다."));

		WishList wishList = wishListRepo.findById(listId)
			.orElseThrow(() -> new IllegalArgumentException("위시리스트가 존재하지 않습니다."));
		if (!wishList.getUser().equals(user)) {
			throw new IllegalArgumentException("자신의 위시리스트에만 위시를 작성할 수 있습니다.");
		}

		Wish wish = wishRepo.findById(wishId).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 위시 입니다."));
		wish.updateWish(request.getContent());
	}

	@Transactional
	public void updateWishStatus(Long userId, Long listId, Long wishId, WishStatusRequest request) {
		User user = userRepo.findById(userId).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원 입니다."));

		WishList wishList = wishListRepo.findById(listId)
			.orElseThrow(() -> new IllegalArgumentException("위시리스트가 존재하지 않습니다."));
		if (!wishList.getUser().equals(user)) {
			throw new IllegalArgumentException("자신의 위시리스트에만 위시를 작성할 수 있습니다.");
		}

		Wish wish = wishRepo.findById(wishId).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 위시 입니다."));
		wish.updateWishStatus(request.getStatus());

	}
}
