package com.dev.wistlist_app.domain.bucket.entity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.dev.wistlist_app.domain.BaseTimeEntity;
import com.dev.wistlist_app.domain.cheer.entity.Cheer;
import com.dev.wistlist_app.domain.users.entity.User;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BucketItem extends BaseTimeEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@JoinColumn(name = "user_id")
	@ManyToOne(fetch = FetchType.LAZY)
	private User user;

	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	private BucketCategory category;

	@Column(nullable = false)
	private String content;

	@Column(nullable = true)
	private String tags;

	@Enumerated(value = EnumType.STRING)
	private BucketItemStatus status;

	private LocalDate dueDate;

	@OneToMany(mappedBy = "bucketItem")
	private List<Cheer> cheers = new ArrayList<>();

	private BucketItem(Long id, User user, String content, BucketItemStatus status, LocalDate dueDate,
		List<Cheer> cheers) {
		this.id = id;
		this.user = user;
		this.content = content;
		this.status = status;
		this.dueDate = dueDate;
		this.cheers = cheers;
	}

	@Builder
	public BucketItem(User user, String content, LocalDate dueDate) {
		this.user = user;
		this.content = content;
		this.status = BucketItemStatus.OPEN;
		this.dueDate = dueDate;
	}


	public void updateBucketItem(String content) {
		this.content = content;
	}

	public void updateBucketItemStatus(BucketItemStatus status) {
		this.status = status;
	}
}
