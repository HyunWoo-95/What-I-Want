package com.dev.wistlist_app.domain.cheer.entity;

import com.dev.wistlist_app.domain.BaseTimeEntity;
import com.dev.wistlist_app.domain.bucket.entity.BucketItem;
import com.dev.wistlist_app.domain.users.entity.User;
import com.dev.wistlist_app.domain.users.entity.UserProfile;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "cheers")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Cheer extends BaseTimeEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@JoinColumn(name = "profile_id")
	@ManyToOne(fetch = FetchType.LAZY)
	private UserProfile profile;

	@JoinColumn(name = "bucket_item_id")
	@ManyToOne(fetch = FetchType.LAZY)
	private BucketItem bucketItem;

	public Cheer(UserProfile profile, BucketItem bucketItem) {
		this.profile = profile;
		this.bucketItem = bucketItem;
	}
}
