package com.dev.wistlist_app.domain.users.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dev.wistlist_app.domain.users.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

	User findByEmailAndPassword(String email, String password);

	boolean existsByPassword(String password);

	boolean existsByEmail(String email);

	Optional<User> findByEmail(String email);
}
