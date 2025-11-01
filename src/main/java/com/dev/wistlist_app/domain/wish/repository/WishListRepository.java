package com.dev.wistlist_app.domain.wish.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dev.wistlist_app.domain.users.entity.User;
import com.dev.wistlist_app.domain.wish.entity.WishList;

@Repository
public interface WishListRepository extends JpaRepository<WishList, Long> {
	List<WishList> findAllByUser(User user);

	WishList findByIdAndUser(Long id, User user);
}
