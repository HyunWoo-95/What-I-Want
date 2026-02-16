package com.dev.wistlist_app.domain.users.entity;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.dev.wistlist_app.domain.BaseTimeEntity;
import com.dev.wistlist_app.domain.follow.entity.Follow;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserProfile extends BaseTimeEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@JoinColumn(name = "user_id")
	@OneToOne(fetch = FetchType.LAZY)
	private User user;

	private String nickname;

	private String interest;

	private String profileUrl;
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "follower")
	private List<Follow> followers = new ArrayList<>();
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "following")
	private List<Follow> followIngs = new ArrayList<>();

	// 변수의 수는 적으나 확장의 가능성이 존재하여 Builder 패턴 이용
	@Builder
	public UserProfile(User user, String nickname, String interest) {
		this.user = user;
		this.nickname = nickname;
		this.interest = interest;
	}

	public String updateProfileUrl(String profileUrl) {
		this.profileUrl = profileUrl;
		return profileUrl;
	}
}
