package com.dev.wistlist_app.domain.comment.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dev.wistlist_app.domain.bucket.entity.BucketItem;
import com.dev.wistlist_app.domain.comment.entity.Comment;

public interface CommentRepository extends JpaRepository<Comment, Long> {
	Optional<Comment> findByIdAndBucketItem(Long commentId, BucketItem bucketItem);
}
