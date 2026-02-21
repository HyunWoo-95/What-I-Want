package com.dev.wistlist_app.domain.bucket.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.dev.wistlist_app.domain.bucket.entity.BucketItem;
import com.dev.wistlist_app.domain.users.entity.UserProfile;

@Repository
public interface BucketItemRepository extends JpaRepository<BucketItem, Long> {
	Optional<BucketItem> findByIdAndProfile(Long id, UserProfile profile);

	@Query("SELECT bi FROM BucketItem bi " +
		"WHERE (:content IS NULL OR LOWER(bi.content) LIKE LOWER(CONCAT('%', :content, '%'))) " +
		"ORDER BY bi.createdAt DESC")
	List<BucketItem> searchBucketItems(
		@Param("content") String content);

	List<BucketItem> findAllByProfile(UserProfile profile);

	boolean existsByIdAndProfile(Long id, UserProfile profile);
}
