package com.dev.wistlist_app.domain.comment.entity;

import java.util.ArrayList;
import java.util.List;

import com.dev.wistlist_app.domain.BaseTimeEntity;
import com.dev.wistlist_app.domain.bucket.entity.BucketItem;
import com.dev.wistlist_app.domain.users.entity.User;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Comment extends BaseTimeEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id", nullable = false)
	private User user;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "bucket_item_id", nullable = false)
	private BucketItem bucketItem;

	@Column(nullable = false)
	private String content;

	private Comment(Long id, User user, BucketItem bucketItem, String content) {
		this.id = id;
		this.user = user;
		this.bucketItem = bucketItem;
		this.content = content;
	}

	@Builder
	public Comment(User user, BucketItem bucketItem, String content) {
		this.user = user;
		this.bucketItem = bucketItem;
		this.content = content;
	}

}
