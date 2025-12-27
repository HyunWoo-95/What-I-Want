package com.dev.wistlist_app.service;

import static org.assertj.core.api.Assertions.*;

import java.time.LocalDateTime;

import org.assertj.core.api.Assertions;
import org.hibernate.AssertionFailure;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.dev.wistlist_app.domain.users.dto.UserRequestDto;
import com.dev.wistlist_app.domain.users.dto.UserRequestDto.JoinRequest;
import com.dev.wistlist_app.domain.users.entity.User;
import com.dev.wistlist_app.domain.users.repository.UserRepository;
import com.dev.wistlist_app.domain.users.service.UserService;
import com.dev.wistlist_app.domain.wish.dto.WishRequestDto;
import com.dev.wistlist_app.domain.wish.dto.WishRequestDto.WishListRequest;
import com.dev.wistlist_app.domain.wish.dto.WishRequestDto.WishRequest;
import com.dev.wistlist_app.domain.wish.entity.Wish;
import com.dev.wistlist_app.domain.wish.entity.WishList;
import com.dev.wistlist_app.domain.wish.repository.WishListRepository;
import com.dev.wistlist_app.domain.wish.repository.WishRepository;
import com.dev.wistlist_app.domain.wish.service.WishService;
import com.dev.wistlist_app.global.encrytion.EncryptPasswordEncoder;
import com.dev.wistlist_app.global.encrytion.SHA256EncryptionService;

@SpringBootTest
public class WishServiceTest {

	@Autowired
	private WishService wishService;
	@Autowired
	private UserService userService;
	@Autowired
	private WishRepository wishRepo;
	@Autowired
	private UserRepository userRepo;
	@Autowired
	private WishListRepository wishListRepo;
	@Autowired
	private EncryptPasswordEncoder encoder;

	@Test
	@DisplayName("위시 리스트 생성에 성공한다")
	void createWishListSuccess() {
		JoinRequest request = new JoinRequest("test@email.com", "test1234", "조현우");
		userService.join(request);

		User joinedUser = userRepo.findByEmail(request.getEmail())
			.orElseThrow(() -> new AssertionFailure("존재하지 않는 사용자 입니다."));

		WishListRequest req = new WishListRequest("2026 목표", LocalDateTime.now());
		wishService.createWishList(joinedUser.getId(), req);

		assertThat(wishListRepo.findAll()).hasSize(1);
	}
	@Test
	@DisplayName("위시 생성에 성공한다.")
	void createWishSuccess() {
		// given : 회원 정보, 위시 리스트 정보
		JoinRequest request = new JoinRequest("test@email.com", "test1234", "조현우");
		userService.join(request);

		User joinedUser = userRepo.findByEmail(request.getEmail())
			.orElseThrow(() -> new AssertionFailure("존재하지 않는 사용자 입니다."));

		WishListRequest req = new WishListRequest("2026 목표", LocalDateTime.now());
		wishService.createWishList(joinedUser.getId(), req);
		WishList wishList = wishListRepo.findAll().get(0);
		// when : 위시 생성 정보
		WishRequest wishReq = new WishRequest("여행가기");
		wishService.createWish(joinedUser.getId(),wishList.getId() , wishReq);
		// then : 성공
		assertThat(wishRepo.findAll()).hasSize(1);
	}
	@Test
	@DisplayName("위시 수정에 성공한다")
	void updateWishSuccess() {
		// given : 회원 정보, 위시 리스트 정보, 위시 수정 정보
		JoinRequest request = new JoinRequest("test@email.com", "test1234", "조현우");
		userService.join(request);

		User joinedUser = userRepo.findByEmail(request.getEmail())
			.orElseThrow(() -> new AssertionFailure("존재하지 않는 사용자 입니다."));

		WishListRequest req = new WishListRequest("2026 목표", LocalDateTime.now());
		wishService.createWishList(joinedUser.getId(), req);
		WishList wishList = wishListRepo.findAll().getFirst();

		WishRequest wishReq = new WishRequest("여행가기");
		wishService.createWish(joinedUser.getId(),wishList.getId() , wishReq);

		// when : 위시 수정 정보
		Wish wish = wishRepo.findByIdAndWishList(1L, wishList);
		WishRequest updateReq =  new WishRequest("공부하기");
		wish.updateWish(updateReq.getContent());

		assertThat(joinedUser.getId()).isEqualTo(wish.getUser().getId());
		assertThat(wishRepo.findAll()).hasSize(1);
		System.out.println(wish.getContent());
		assertThat(wish.getContent()).isEqualTo("공부하기");
	}

	@Test
	@DisplayName("위시 삭제에 성공한다.")
	void deleteWishSuccess() {
		// given : 유저 정보, 위시 리스트
		JoinRequest request = new JoinRequest("test@email.com", "test1234", "조현우");
		userService.join(request);

		User joinedUser = userRepo.findByEmail(request.getEmail())
			.orElseThrow(() -> new AssertionFailure("존재하지 않는 사용자 입니다."));

		WishListRequest req = new WishListRequest("2026 목표", LocalDateTime.now());
		wishService.createWishList(joinedUser.getId(), req);
		WishList wishList = wishListRepo.findAll().getFirst();

		WishRequest wishReq = new WishRequest("여행가기");
		wishService.createWish(joinedUser.getId(),wishList.getId() , wishReq);
		assertThat(wishRepo.findAll()).hasSize(1);
		Wish wish = wishRepo.findByIdAndWishList(1L, wishList);
		// then : 삭제 행동
		wishService.deleteMyWish(joinedUser.getId(), wishList.getId(),wish.getId());
		// when : 성공
		assertThat(wishRepo.findAll()).isEmpty();
	}
}
