import http from 'k6/http';
import { check, sleep } from 'k6';
import { Rate } from 'k6/metrics';

const errorRate = new Rate('errors');

export const options = {
  stages: [
    { duration: '30s', target: 10 },
    { duration: '1m', target: 30 },
    { duration: '2m', target: 30 },
    { duration: '30s', target: 50 },
    { duration: '1m', target: 50 },
    { duration: '30s', target: 0 },
  ],
  thresholds: {
    'http_req_duration': ['p(95)<500', 'p(99)<1000'],
    'http_req_failed': ['rate<0.01'],
    'errors': ['rate<0.1'],
  },
};

const BASE_URL = __ENV.BASE_URL || 'http://localhost:8080';

const searchKeywords = [
  '마라톤',
  '여행',
  '운동',
  '책',
  '영어',
  '',  // 전체 조회
  '자격증',
  '다이어트',
];

export default function () {
  const keyword = searchKeywords[Math.floor(Math.random() * searchKeywords.length)];

  const url = `${BASE_URL}/api/v1/bucket-items/search?content=${encodeURIComponent(keyword)}`;

  const response = http.get(url, {
    tags: { name: 'SearchBucketItems' },
  });

  // ✅ ApiResponse 구조에 맞게 수정
  const checkResult = check(response, {
    'status is 200': (r) => r.status === 200,
    'response time < 500ms': (r) => r.timings.duration < 500,
    'response time < 1000ms': (r) => r.timings.duration < 1000,
    'valid JSON': (r) => {
      try {
        JSON.parse(r.body);
        return true;
      } catch (e) {
        return false;
      }
    },
    'has code field': (r) => {  // ✅ code 필드 확인
      try {
        const body = JSON.parse(r.body);
        return body.hasOwnProperty('code');
      } catch (e) {
        return false;
      }
    },
    'code is 200': (r) => {  // ✅ code가 "200"인지 확인
      try {
        const body = JSON.parse(r.body);
        return body.code === '200';
      } catch (e) {
        return false;
      }
    },
    'has data field': (r) => {  // ✅ data 필드 확인
      try {
        const body = JSON.parse(r.body);
        return body.hasOwnProperty('data');
      } catch (e) {
        return false;
      }
    },
    'data is array': (r) => {  // ✅ data가 배열인지 확인
      try {
        const body = JSON.parse(r.body);
        return Array.isArray(body.data);
      } catch (e) {
        return false;
      }
    },
  });

  errorRate.add(!checkResult);

  sleep(Math.random() * 2 + 1);
}