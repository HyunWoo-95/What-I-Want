-- ============================================
-- 간소화 버전: 프로시저 없이 CTE만 사용
-- ============================================
-- cte 문제 발생시
-- SHOW VARIABLES LIKE 'cte_max_recursion_depth'; cte_max 확인
-- SET SESSION cte_max_recursion_depth = 1000000; cte_max 조정


-- ============================================
-- 전체 실행 스크립트 (순서대로 실행)
-- due_date 제거 버전
-- ============================================

-- 0. 준비
SET SESSION cte_max_recursion_depth = 1000000;
SET FOREIGN_KEY_CHECKS = 0;
SET UNIQUE_CHECKS = 0;
SET AUTOCOMMIT = 0;

-- 1. User (10만 명)
INSERT INTO users (username, email, password, role, created_at, updated_at)
WITH RECURSIVE numbers AS (
    SELECT 1 AS n UNION ALL SELECT n + 1 FROM numbers WHERE n < 100000
)
SELECT CONCAT('user', LPAD(n, 6, '0')),
       CONCAT('user', LPAD(n, 6, '0'), '@wiw.com'),
       '$2a$10$dXJ3SW6G7P50lGmMkkmwe.20cQQubK3.HZWzG3YB1tlRy.fqvM/BG',
       'USER',
       DATE_SUB(NOW(), INTERVAL FLOOR(RAND() * 365) DAY),
       NOW()
FROM numbers;
COMMIT;
SELECT '1. User 생성 완료' AS status, COUNT(*) AS count FROM users;

-- 2. UserProfile (10만 개)
INSERT INTO user_profile (user_id, nickname, interest, profile_url, created_at, updated_at)
SELECT u.id,
       CONCAT('닉네임', u.id),
       ELT(1 + MOD(u.id, 7), 'SELF_IMPROVEMENT', 'TRAVEL', 'FINANCIAL_MANAGEMENT', 'FASHION', 'HEALTH', 'CAREER', 'SOCIAL'),
       CONCAT('https://wiw-profiles.s3.amazonaws.com/default/', MOD(u.id, 10), '.jpg'),
       u.created_at,
       u.updated_at
FROM users u
WHERE u.username LIKE 'user%';
COMMIT;
SELECT '2. UserProfile 생성 완료' AS status, COUNT(*) AS count FROM user_profile;

-- 3. BucketItem (10만 개씩 10번 = 100만 개) - due_date 제거
-- 1차
INSERT INTO bucket_item (profile_id, category, content, status, cheer_count, comment_count, created_at, updated_at)
WITH RECURSIVE numbers AS (
    SELECT 1 AS n UNION ALL SELECT n + 1 FROM numbers WHERE n < 100000
)
SELECT 1 + MOD(n - 1, 100000),
       ELT(1 + MOD(n, 8), 'CAREER', 'LEARNING', 'TRAVEL', 'HEALTH', 'HOBBY', 'RELATIONSHIP', 'FINANCE', 'OTHER'),
       ELT(1 + MOD(n, 30), '세계 일주 하기', '마라톤 완주하기', '책 100권 읽기', '새로운 언어 배우기', '악기 마스터하기',
           '다이어트 10kg 감량', '연봉 2배 올리기', '창업하기', '에베레스트 등반', '번지점프 도전', '스쿠버 다이빙 자격증',
           '요리 마스터', '사진전 개최', '봉사활동 100시간', '부동산 투자', '주식 수익률 50%', '외국어 자격증 취득',
           '온라인 강의 개설', '유튜브 구독자 10만', '블로그 방문자 1만', '피아노 연주회', '그림 개인전', '소설 출판',
           '시 모음집 발간', '등산 100대 명산', '캠핑 50회', '전국 여행', '해외 배낭여행', '자격증 10개 취득', 'TED 강연하기'),
       CASE WHEN RAND() < 0.10 THEN 'DONE' WHEN RAND() < 0.35 THEN 'IN_PROGRESS' WHEN RAND() < 0.95 THEN 'OPEN' ELSE 'NEXT' END,
       0, 0,
       DATE_SUB(NOW(), INTERVAL FLOOR(RAND() * 180) DAY),
       NOW()
FROM numbers;
COMMIT;
SELECT '3-1. BucketItem 1차 완료 (100,000개)' AS status;

-- 2차 ~ 10차 동일하게 반복 (시작 번호만 변경)
-- 2차: 100001 ~ 200000
-- 3차: 200001 ~ 300000
-- ... 10차: 900001 ~ 1000000

-- 마무리
SET FOREIGN_KEY_CHECKS = 1;
SET UNIQUE_CHECKS = 1;
SET AUTOCOMMIT = 1;

-- 최종 통계
SELECT 'users' AS table_name, COUNT(*) AS count FROM users
UNION ALL SELECT 'user_profile', COUNT(*) FROM user_profile
          UNION ALL SELECT 'bucket_item', COUNT(*) FROM bucket_item
          UNION ALL SELECT 'comment', COUNT(*) FROM comment;

SELECT '완료!' AS status;