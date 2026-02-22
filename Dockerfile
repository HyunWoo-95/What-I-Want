# ==========================================
# Stage 1: 빌드 환경 (Builder)
# ==========================================
FROM amazoncorretto:21 AS builder
WORKDIR /app

# 1. Gradle 실행에 필요한 파일들 먼저 복사 (캐싱 최적화)
COPY gradlew .
COPY gradle gradle
COPY build.gradle .
COPY settings.gradle .

# 2. 실제 소스 코드 복사
COPY src src

# 3. gradlew 실행 권한 부여 (윈도우/맥 환경 차이 방지)
RUN chmod +x ./gradlew

# 4. 테스트를 생략하고 애플리케이션 빌드 (.jar 파일 생성)
RUN ./gradlew clean build -x test

# ==========================================
# Stage 2: 실행 환경 (Runtime)
# ==========================================
FROM amazoncorretto:21
WORKDIR /app

# 5. Builder 단계에서 생성된 실행 가능한 jar 파일만 복사해옴
# (주의: *-plain.jar 복사를 방지하기 위해 SNAPSHOT 명시)
COPY --from=builder /app/build/libs/*SNAPSHOT.jar app.jar

# 6. 애플리케이션 실행
ENTRYPOINT ["java", "-jar", "app.jar"]
#FROM amazoncorretto:21
#WORKDIR /app
#COPY build/libs/*.jar app.jar
#ENTRYPOINT ["java", "-jar", "app.jar"]