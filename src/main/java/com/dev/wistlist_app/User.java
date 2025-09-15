package com.dev.wistlist_app;

import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "users")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String username;

	private String email;

	private String password;

	private String userNumber;

	private LocalDateTime created_at;

	private LocalDateTime updated_at;

	private User(String username, String email, String password) {
		this.username = username;
		this.email = email;
		this.password = password;
		this.created_at = LocalDateTime.now();
		this.updated_at = LocalDateTime.now();
	}

	@Builder
	public static User addUser(String username, String email, String password) {
		return User.builder()
			.username(username)
			.email(email)
			.password(password)
			.build();
	}
}
