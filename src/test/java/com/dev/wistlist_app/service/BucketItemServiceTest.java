package com.dev.wistlist_app.service;

import static org.assertj.core.api.Assertions.*;

import java.time.LocalDateTime;

import org.hibernate.AssertionFailure;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.dev.wistlist_app.domain.bucket.service.BucketListService;
import com.dev.wistlist_app.domain.users.dto.UserRequestDto.JoinRequest;
import com.dev.wistlist_app.domain.users.entity.User;
import com.dev.wistlist_app.domain.users.repository.UserRepository;
import com.dev.wistlist_app.domain.users.service.UserService;
import com.dev.wistlist_app.domain.bucket.dto.BucketRequestDto.BucketListCreateRequest;
import com.dev.wistlist_app.domain.bucket.dto.BucketRequestDto.BucketItemCreateRequest;
import com.dev.wistlist_app.domain.bucket.entity.BucketItem;
import com.dev.wistlist_app.domain.bucket.entity.BucketList;
import com.dev.wistlist_app.domain.bucket.repository.BucketListRepository;
import com.dev.wistlist_app.domain.bucket.repository.BucketItemRepository;
import com.dev.wistlist_app.domain.bucket.service.BucketItemService;
import com.dev.wistlist_app.global.encrytion.EncryptPasswordEncoder;

@SpringBootTest
public class BucketItemServiceTest {

	@Autowired
	private BucketListService bucketListService;
	@Autowired
	private BucketItemService bucketItemService;
	@Autowired
	private UserService userService;
	@Autowired
	private BucketItemRepository wishRepo;
	@Autowired
	private UserRepository userRepo;
	@Autowired
	private BucketListRepository wishListRepo;
	@Autowired
	private EncryptPasswordEncoder encoder;

	@Test
	@DisplayName("위시 리스트 생성에 성공한다")
	void createBucketListSuccess() {
		JoinRequest request = new JoinRequest("test@email.com", "test1234", "조현우");
		userService.join(request);

		User joinedUser = userRepo.findByEmail(request.getEmail())
			.orElseThrow(() -> new AssertionFailure("존재하지 않는 사용자 입니다."));

		BucketListCreateRequest req = new BucketListCreateRequest("2026 목표", LocalDateTime.now());
		bucketListService.createBucketList(joinedUser.getId(), req);

		assertThat(wishListRepo.findAll()).hasSize(1);
	}
	@Test
	@DisplayName("위시 생성에 성공한다.")
	void createBucketItemSuccess() {
		// given : 회원 정보, 위시 리스트 정보
		JoinRequest request = new JoinRequest("test@email.com", "test1234", "조현우");
		userService.join(request);

		User joinedUser = userRepo.findByEmail(request.getEmail())
			.orElseThrow(() -> new AssertionFailure("존재하지 않는 사용자 입니다."));

		BucketListCreateRequest req = new BucketListCreateRequest("2026 목표", LocalDateTime.now());
		bucketListService.createBucketList(joinedUser.getId(), req);
		BucketList bucketList = wishListRepo.findAll().get(0);
		// when : 위시 생성 정보
		BucketItemCreateRequest wishReq = new BucketItemCreateRequest("여행가기");
		bucketItemService.createBucketItem(joinedUser.getId(), bucketList.getId() , wishReq);
		// then : 성공
		assertThat(wishRepo.findAll()).hasSize(1);
	}
	@Test
	@DisplayName("위시 수정에 성공한다")
	void updateBucketItemSuccess() {
		// given : 회원 정보, 위시 리스트 정보, 위시 수정 정보
		JoinRequest request = new JoinRequest("test@email.com", "test1234", "조현우");
		userService.join(request);

		User joinedUser = userRepo.findByEmail(request.getEmail())
			.orElseThrow(() -> new AssertionFailure("존재하지 않는 사용자 입니다."));

		BucketListCreateRequest req = new BucketListCreateRequest("2026 목표", LocalDateTime.now());
		bucketListService.createBucketList(joinedUser.getId(), req);
		BucketList bucketList = wishListRepo.findAll().getFirst();

		BucketItemCreateRequest wishReq = new BucketItemCreateRequest("여행가기");
		bucketItemService.createBucketItem(joinedUser.getId(), bucketList.getId() , wishReq);

		// when : 위시 수정 정보
		BucketItem bucketItem = wishRepo.findByIdAndBucketList(1L, bucketList);
		BucketItemCreateRequest updateReq =  new BucketItemCreateRequest("공부하기");
		bucketItem.updateBucketItem(updateReq.getContent());

		assertThat(joinedUser.getId()).isEqualTo(bucketItem.getUser().getId());
		assertThat(wishRepo.findAll()).hasSize(1);
		System.out.println(bucketItem.getContent());
		assertThat(bucketItem.getContent()).isEqualTo("공부하기");
	}

	@Test
	@DisplayName("위시 삭제에 성공한다.")
	void deleteWishSuccess() {
		// given : 유저 정보, 위시 리스트
		JoinRequest request = new JoinRequest("test@email.com", "test1234", "조현우");
		userService.join(request);

		User joinedUser = userRepo.findByEmail(request.getEmail())
			.orElseThrow(() -> new AssertionFailure("존재하지 않는 사용자 입니다."));

		BucketListCreateRequest req = new BucketListCreateRequest("2026 목표", LocalDateTime.now());
		bucketListService.createBucketList(joinedUser.getId(), req);
		BucketList bucketList = wishListRepo.findAll().getFirst();

		BucketItemCreateRequest wishReq = new BucketItemCreateRequest("여행가기");
		bucketItemService.createBucketItem(joinedUser.getId(), bucketList.getId() , wishReq);
		assertThat(wishRepo.findAll()).hasSize(1);
		BucketItem bucketItem = wishRepo.findByIdAndBucketList(1L, bucketList);
		// then : 삭제 행동
		bucketItemService.deleteMyBucketItem(joinedUser.getId(), bucketList.getId(), bucketItem.getId());
		// when : 성공
		assertThat(wishRepo.findAll()).isEmpty();
	}
}
