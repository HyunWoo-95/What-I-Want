-- ============================================
-- 간소화 버전: 프로시저 없이 CTE만 사용
-- ============================================
-- cte 문제 발생시
-- SHOW VARIABLES LIKE 'cte_max_recursion_depth'; cte_max 확인
-- SET SESSION cte_max_recursion_depth = 1000000; cte_max 조정


-- 1. 유저 생성 (2만 명)
INSERT INTO users (username, email, password, created_at, updated_at)
WITH RECURSIVE numbers AS (
    SELECT 1 AS n UNION ALL SELECT n + 1 FROM numbers WHERE n < 20000
)
SELECT CONCAT('testuser', LPAD(n, 5, '0')), CONCAT('testuser', LPAD(n, 5, '0'), '@test.com'),
       '$2a$10$dummyhashedpassword1234567890123456789012',
       DATE_SUB(NOW(), INTERVAL FLOOR(RAND() * 365) DAY), NOW()
FROM numbers;

-- 2. 프로필 생성 (2만 개)
INSERT INTO user_profile (user_id, nickname, interest, created_at, updated_at)
SELECT u.id, CONCAT('닉네임', u.id),
       ELT(1 + MOD(u.id, 7), 'SELF_IMPROVEMENT', 'TRAVEL', 'FINANCIAL_MANAGEMENT', 'FASHION', 'HEALTH', 'CAREER', 'SOCIAL'),
       DATE_SUB(NOW(), INTERVAL FLOOR(RAND() * 365) DAY), NOW()
FROM users u WHERE u.username LIKE 'testuser%';

-- 3. 버킷리스트 생성 (4만 개 - 유저당 2개)
INSERT INTO bucket_list (user_id, title, due_date, created_at, updated_at)
SELECT u.id,
       CONCAT(ELT(1 + MOD(u.id, 5), '2025년', '2026년', '올해', '내년', '평생'), ' 버킷리스트 ', list_num),
       DATE_ADD(NOW(), INTERVAL FLOOR(30 + RAND() * 700) DAY),
       DATE_SUB(NOW(), INTERVAL FLOOR(RAND() * 180) DAY), NOW()
FROM users u
         CROSS JOIN (SELECT 1 AS list_num UNION SELECT 2) lists
WHERE u.username LIKE 'testuser%';

-- 4. 버킷아이템 생성 (100만 개씩 7번 실행하여 총 700만 개)
-- 첫 번째 100만 개
INSERT INTO bucket_item (user_id, list_id, content, status, created_at, updated_at)
WITH RECURSIVE numbers AS (
    SELECT 1 AS n UNION ALL SELECT n + 1 FROM numbers WHERE n < 1000000
)
SELECT bl.user_id, bl.id,
       ELT(1 + MOD(n, 30), '마라톤 완주하기', '외국어 배우기', '악기 연주하기', '책 100권 읽기', '해외여행 가기',
           '다이어트 성공하기', '자격증 취득하기', '번지점프 하기', '스쿠버다이빙 배우기', '요리 배우기',
           '봉사활동 하기', '반려동물 키우기', '새로운 취미 시작하기', '운동 습관 만들기', '저축 목표 달성하기',
           '창업하기', '블로그 시작하기', 'SNS 팔로워 1000명', '유튜브 채널 만들기', '전시회 가기',
           '콘서트 관람하기', '캠핑 가기', '등산하기', '자전거 여행', '사진 전시회 열기',
           '명상 습관 들이기', '독서 모임 참여', '운동 대회 출전', '재테크 공부', '영화 100편 보기'),
       CASE WHEN RAND() < 0.10 THEN 'DONE' WHEN RAND() < 0.35 THEN 'IN_PROGRESS' WHEN RAND() < 0.95 THEN 'OPEN' ELSE 'NEXT' END,
       DATE_SUB(NOW(), INTERVAL FLOOR(RAND() * 180) DAY), NOW()
FROM numbers n JOIN bucket_list bl ON bl.id = (1 + MOD(n - 1, 40000));

-- 위 쿼리를 6번 더 실행하여 총 700만 개 생성

SELECT '데이터 생성 완료!' AS status;