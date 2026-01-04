package com.dev.wistlist_app.domain.bucket.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.dev.wistlist_app.domain.BaseTimeEntity;
import com.dev.wistlist_app.domain.users.entity.User;

import jakarta.persistence.Entity;
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
public class BucketList extends BaseTimeEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@JoinColumn(name = "user_id")
	@ManyToOne(fetch = FetchType.LAZY)
	private User user;

	private String title;

	private LocalDateTime dueDate;

	@OneToMany(mappedBy = "bucketList", fetch = FetchType.LAZY)
	private List<BucketItem> bucketItems = new ArrayList<>();

	@Builder
	public BucketList(User user, String title, LocalDateTime dueDate) {
		this.user = user;
		this.title = title;
		this.dueDate = dueDate;
	}
}
