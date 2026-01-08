package com.dev.wistlist_app.domain.cheer.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dev.wistlist_app.domain.cheer.entity.Cheer;

public interface CheerRepository extends JpaRepository<Cheer, Long> {
}
