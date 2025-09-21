package com.dev.wistlist_app;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserProfile {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@JoinColumn(name = "user_id")
	@OneToOne(fetch = FetchType.LAZY)
	private User user;

	private String nickname;

	private String interest;

	// 변수의 수는 적으나 확장의 가능성이 존재하여 Builder 패턴 이용
	@Builder
	public UserProfile(User user, String nickname, String interest) {
		this.user = user;
		this.nickname = nickname;
		this.interest = interest;
	}
}
