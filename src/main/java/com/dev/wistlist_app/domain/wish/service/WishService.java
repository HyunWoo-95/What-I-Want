package com.dev.wistlist_app.domain.wish.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dev.wistlist_app.domain.users.entity.User;
import com.dev.wistlist_app.domain.users.repository.UserRepository;
import com.dev.wistlist_app.domain.wish.dto.WishRequestDto;
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

	@Transactional
	public void createWishList(Long userId, WishRequestDto.WishListRequest request) {
		User user = userRepo.findById(userId).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원 입니다."));

		WishList wishes = WishList.builder()
			.user(user)
			.title(request.getTitle())
			.dueDate(request.getDuedate())
			.build();
		wishListRepo.save(wishes);
	}
}
