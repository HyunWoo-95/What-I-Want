package com.dev.wistlist_app.domain.comment.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dev.wistlist_app.domain.comment.entity.Comment;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
