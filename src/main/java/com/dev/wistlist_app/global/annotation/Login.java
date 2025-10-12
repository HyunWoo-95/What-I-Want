package com.dev.wistlist_app.global.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Login 애노테이션 사용시 직접 생성 ArgumentResolver가 동작 자동으로 세션에 있는 로그인 회원을 조회
 * 세션이 없다면 null을 반환
 */
@Target(ElementType.PARAMETER) // 애노테이션 적용 위치
@Retention(RetentionPolicy.RUNTIME) // 애노테이션의 생명주기
public @interface Login {
}
