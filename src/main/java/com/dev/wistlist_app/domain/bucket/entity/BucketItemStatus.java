package com.dev.wistlist_app.domain.bucket.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum BucketItemStatus {

	DONE("성공"),
	IN_PROGRESS("진행중"),
	OPEN("대기"),
	NEXT("다음에...");

	private final String name;

}
