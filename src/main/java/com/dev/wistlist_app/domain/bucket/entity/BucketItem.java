package com.dev.wistlist_app.domain.bucket.entity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.dev.wistlist_app.domain.BaseTimeEntity;
import com.dev.wistlist_app.domain.cheer.entity.Cheer;
import com.dev.wistlist_app.domain.comment.entity.Comment;
import com.dev.wistlist_app.domain.users.entity.UserProfile;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
// @Table(name = "bucket_item", indexes = {
// 	@Index(name = "idx_category_created_at", columnList = "category, created_at")
// })
public class BucketItem extends BaseTimeEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@JoinColumn(name = "profile_id")
	@ManyToOne(fetch = FetchType.LAZY)
	private UserProfile profile;

	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	private BucketCategory category;

	@Column(nullable = false)
	private String content;

	@Enumerated(value = EnumType.STRING)
	private BucketItemStatus status;

	private LocalDate dueDate;

	@OneToMany(mappedBy = "bucketItem")
	private List<Cheer> cheers = new ArrayList<>();

	@OneToMany(mappedBy = "bucketItem")
	private List<Comment> comments = new ArrayList<>();

	private Long cheerCount;

	private Long commentCount;

	private BucketItem(Long id, UserProfile profile, String content, BucketItemStatus status, LocalDate dueDate,
		List<Cheer> cheers) {
		this.id = id;
		this.profile = profile;
		this.content = content;
		this.status = status;
		this.dueDate = dueDate;
		this.cheers = cheers;
	}

	@Builder
	public BucketItem(UserProfile profile, String content, BucketCategory category, LocalDate dueDate) {
		this.profile = profile;
		this.content = content;
		this.category = category;
		this.status = BucketItemStatus.OPEN;
		this.dueDate = dueDate;
	}

	public void updateBucketItem(String content) {
		this.content = content;
	}

	public void updateBucketItemStatus(BucketItemStatus status) {
		this.status = status;
	}

	public void incrementCheerCount() {
		this.cheerCount = this.cheerCount + 1;
	}

	public void decrementCheerCount() {
		this.cheerCount = this.cheerCount - 1;
	}

	public void incrementCommentCount() {
		this.commentCount = this.commentCount + 1;
	}

	public void decrementCommentCount() {
		this.commentCount = this.commentCount - 1;
	}
}
