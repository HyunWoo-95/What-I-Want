package com.dev.wistlist_app.domain.users.entity;


import com.dev.wistlist_app.domain.BaseTimeEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "users")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends BaseTimeEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(nullable = false)
	private String username;
	@Column(nullable = false)
	private String email;
	@Column(nullable = false)
	private String password;

	@Builder
	public User(String username, String email, String password) {
		this.username = username;
		this.email = email;
		this.password = password;
	}
}
