package com.dev.wistlist_app.domain.bucket.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.dev.wistlist_app.domain.bucket.entity.BucketItem;
import com.dev.wistlist_app.domain.bucket.entity.BucketItemStatus;
import com.dev.wistlist_app.domain.bucket.entity.BucketList;

@Repository
public interface BucketItemRepository extends JpaRepository<BucketItem, Long> {
	BucketItem findByIdAndBucketList(Long id, BucketList list);

	@Query("SELECT bi FROM BucketItem bi " +
		"WHERE (:content IS NULL OR LOWER(bi.content) LIKE LOWER(CONCAT('%', :content, '%'))) " +
		"AND (:status IS NULL OR bi.status = :status) " +
		"ORDER BY bi.createdAt DESC")
	List<BucketItem> searchBucketItems(
		@Param("content") String content,
		@Param("status") BucketItemStatus status
	);

}
