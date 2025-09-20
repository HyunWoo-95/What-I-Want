package com.dev.wistlist_app;

import java.time.LocalDateTime;

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
public class Wish {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@JoinColumn(name = "list_id")
	@ManyToOne(fetch = FetchType.LAZY)
	private WishList wishList;

	private String content;

	@Enumerated(value = EnumType.STRING)
	private WishStatus status;

	private LocalDateTime createdAt;

	private LocalDateTime updatedAt;

	@Builder
	public Wish(WishList wishList, String content, WishStatus status) {
		this.wishList = wishList;
		this.content = content;
		this.status = WishStatus.OPEN;
		this.createdAt = LocalDateTime.now();
		this.updatedAt = LocalDateTime.now();
	}
}
