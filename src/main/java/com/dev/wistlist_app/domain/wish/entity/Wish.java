package com.dev.wistlist_app.domain.wish.entity;

import java.time.LocalDateTime;

import com.dev.wistlist_app.domain.BaseTimeEntity;
import com.dev.wistlist_app.domain.users.entity.User;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
public class Wish extends BaseTimeEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@JoinColumn(name = "user_id")
	@ManyToOne(fetch = FetchType.LAZY)
	private User user;

	@JoinColumn(name = "list_id")
	@ManyToOne(fetch = FetchType.LAZY)
	private WishList wishList;

	private String content;

	@Enumerated(value = EnumType.STRING)
	private WishStatus status;

	@Builder
	public Wish(User user, WishList wishList, String content, WishStatus status) {
		this.user = user;
		this.wishList = wishList;
		this.content = content;
		this.status = WishStatus.OPEN;
	}
}
