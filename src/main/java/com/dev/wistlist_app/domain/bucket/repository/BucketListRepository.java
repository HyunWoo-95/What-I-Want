package com.dev.wistlist_app.domain.bucket.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dev.wistlist_app.domain.users.entity.User;
import com.dev.wistlist_app.domain.bucket.entity.BucketList;

@Repository
public interface BucketListRepository extends JpaRepository<BucketList, Long> {
	List<BucketList> findAllByUser(User user);

	BucketList findByIdAndUser(Long id, User user);
}
