package com.dev.wistlist_app;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {
	private final UserRepository userRepo;
	private final UserProfileRepository userProfileRepo;

	@Transactional
	public void join(String email, String password, String username, String nickname, Interest interest) {
		User user = User.builder().email(email).password(username).password(password).build();
		userRepo.save(user);
		UserProfile profile = UserProfile.builder().user(user).nickname(nickname).interest(interest.getName()).build();
		userProfileRepo.save(profile);
	}
}
