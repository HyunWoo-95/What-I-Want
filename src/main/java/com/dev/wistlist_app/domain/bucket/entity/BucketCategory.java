package com.dev.wistlist_app.domain.bucket.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum BucketCategory {

	CAREER("커리어", "직업, 이직, 승진 관련"),
	LEARNING("학습", "공부, 자격증, 교육"),
	TRAVEL("여행", "국내외 여행"),
	HEALTH("건강", "운동, 식단, 건강관리"),
	HOBBY("취미", "취미 활동, 여가"),
	RELATIONSHIP("관계", "가족, 친구, 인간관계"),
	FINANCE("재정", "저축, 투자, 재테크"),
	OTHER("기타", "그 외");

	private final String displayName;
	private final String description;

}
