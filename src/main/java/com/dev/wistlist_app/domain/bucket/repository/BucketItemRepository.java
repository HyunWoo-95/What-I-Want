package com.dev.wistlist_app.domain.bucket.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dev.wistlist_app.domain.bucket.entity.BucketItem;
import com.dev.wistlist_app.domain.bucket.entity.BucketList;

@Repository
public interface BucketItemRepository extends JpaRepository<BucketItem, Long> {
	BucketItem findByIdAndWishList(Long id, BucketList list);


}
