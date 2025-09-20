package com.dev.wistlist_app;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Interest {
	SELF_IMPROVEMENT("자기계발"),
	TRAVEL("아웃도어/여행"),
	FINANCIAL_MANAGEMENT("재테크/투자"),
	FASHION("패션/미용"),
	HEALTH("운동/건강"),
	CAREER("직무"),
	SOCIAL("사교");

	private final String name;
}
