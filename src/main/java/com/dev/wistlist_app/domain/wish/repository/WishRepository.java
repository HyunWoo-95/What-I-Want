package com.dev.wistlist_app.domain.wish.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dev.wistlist_app.domain.wish.entity.Wish;

@Repository
public interface WishRepository extends JpaRepository<Wish, Long> {
}
