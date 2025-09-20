package com.dev.wistlist_app;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

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
public class WishList {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@JoinColumn(name = "user_id")
	@ManyToOne(fetch = FetchType.LAZY)
	private User user;

	private String title;

	private LocalDateTime dueDate;

	@OneToMany(mappedBy = "wishList", fetch = FetchType.LAZY)
	private Set<Wish> wishes = new HashSet<>();

	private LocalDateTime createdAt;

	private LocalDateTime updatedAt;

	@Builder
	public WishList(User user, String title, LocalDateTime dueDate) {
		this.user = user;
		this.title = title;
		this.dueDate = dueDate;
		this.createdAt = LocalDateTime.now();
		this.updatedAt = LocalDateTime.now();
	}
}
