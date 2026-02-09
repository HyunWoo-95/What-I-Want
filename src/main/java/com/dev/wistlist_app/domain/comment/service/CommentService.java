package com.dev.wistlist_app.domain.comment.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dev.wistlist_app.domain.bucket.entity.BucketItem;
import com.dev.wistlist_app.domain.bucket.repository.BucketItemRepository;
import com.dev.wistlist_app.domain.comment.dto.CommentRequestDto;
import com.dev.wistlist_app.domain.comment.dto.CommentResponseDto;
import com.dev.wistlist_app.domain.comment.entity.Comment;
import com.dev.wistlist_app.domain.comment.repository.CommentRepository;
import com.dev.wistlist_app.domain.users.entity.User;
import com.dev.wistlist_app.domain.users.repository.UserRepository;
import com.dev.wistlist_app.global.exception.ErrorCode;
import com.dev.wistlist_app.global.exception.GlobalException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CommentService {

	private final CommentRepository commentRepo;
	private final BucketItemRepository bucketRepo;
	private final UserRepository userRepo;

	@Transactional(readOnly = true)
	public List<CommentResponseDto> getComments(Long bucketId) {
		BucketItem bucketItem = bucketRepo.findById(bucketId).orElseThrow(() ->
			new GlobalException(ErrorCode.BUCKETITEM_NOT_FOUND));
		List<Comment> comments = bucketItem.getComments();

		return comments.stream().map(this::toCommentResponse).toList();
	}

	@Transactional(readOnly = true)
	public CommentResponseDto getComment(Long bucketId, Long commentId) {
		BucketItem bucketItem = bucketRepo.findById(bucketId).orElseThrow(() ->
			new GlobalException(ErrorCode.BUCKETITEM_NOT_FOUND));

		Comment comment = commentRepo.findByIdAndBucketItem(commentId, bucketItem)
			.orElseThrow(() -> new GlobalException(ErrorCode.COMMENT_NOT_FOUND));

		return toCommentResponse(comment);
	}

	@Transactional
	public void createComment(Long userId, Long bucketId, CommentRequestDto req) {
		User user = userRepo.findById(userId).orElseThrow(() -> new GlobalException(ErrorCode.USER_NOT_FOUND));

		BucketItem bucketItem = bucketRepo.findById(bucketId).orElseThrow(() ->
			new GlobalException(ErrorCode.BUCKETITEM_NOT_FOUND));

		Comment comment = Comment.builder().user(user).bucketItem(bucketItem).content(req.getContent()).build();
		commentRepo.save(comment);
		bucketItem.incrementCheerCount();
	}

	@Transactional
	public void updateComment(Long userId, Long bucketId, Long commentId, CommentRequestDto req) {
		User user = userRepo.findById(userId).orElseThrow(() -> new GlobalException(ErrorCode.USER_NOT_FOUND));

		BucketItem bucketItem = bucketRepo.findById(bucketId).orElseThrow(() ->
			new GlobalException(ErrorCode.BUCKETITEM_NOT_FOUND));
		Comment comment = commentRepo.findByIdAndBucketItem(commentId, bucketItem)
			.orElseThrow(() -> new GlobalException(ErrorCode.COMMENT_NOT_FOUND));
		if (!comment.getUser().getId().equals(user.getId())) {
			throw new GlobalException(ErrorCode.UNAUTHENTICATED_USER);
		}
		comment.updateContent(req.getContent());
	}

	@Transactional
	public void deleteComment(Long userId, Long bucketId, Long commentId) {
		User user = userRepo.findById(userId).orElseThrow(() -> new GlobalException(ErrorCode.USER_NOT_FOUND));
		BucketItem bucketItem = bucketRepo.findById(bucketId).orElseThrow(() ->
			new GlobalException(ErrorCode.BUCKETITEM_NOT_FOUND));
		Comment comment = commentRepo.findByIdAndBucketItem(commentId, bucketItem)
			.orElseThrow(() -> new GlobalException(ErrorCode.COMMENT_NOT_FOUND));
		if (!comment.getUser().getId().equals(user.getId())) {
			throw new GlobalException(ErrorCode.UNAUTHENTICATED_USER);
		}
		commentRepo.delete(comment);
	}

	private CommentResponseDto toCommentResponse(Comment comment) {
		return CommentResponseDto.builder().userId(comment.getUser().getId())
			.username(comment.getUser().getUsername())
			.commentId(comment.getId())
			.content(comment.getContent())
			.createdAt(comment.getCreatedAt())
			.updatedAt(comment.getUpdatedAt())
			.build();
	}

}
