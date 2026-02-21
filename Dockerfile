#FROM amazoncorretto:21
#WORKDIR /app
#COPY build/libs/*.jar app.jar
#ENTRYPOINT ["java", "-jar", "app.jar"]

# ============================================
# Stage 1: Build
# ============================================
FROM gradle:8.5-jdk21-alpine AS build
WORKDIR /app

# Gradle 캐시 활용 (dependencies 먼저)
COPY build.gradle settings.gradle ./
COPY gradle ./gradle
RUN gradle dependencies --no-daemon || return 0

# 소스 코드 복사
COPY src ./src

# 빌드 (테스트 제외)
RUN gradle clean build -x test --no-daemon

# ============================================
# Stage 2: Run
# ============================================
FROM amazoncorretto:21-alpine
WORKDIR /app

# 타임존 설정
RUN apk add --no-cache tzdata && \
    ln -snf /usr/share/zoneinfo/Asia/Seoul /etc/localtime && \
    echo "Asia/Seoul" > /etc/timezone

# 빌드된 JAR 복사
COPY --from=build /app/build/libs/*.jar app.jar

# 헬스체크용 curl 설치
RUN apk add --no-cache curl

# 헬스체크
HEALTHCHECK --interval=30s --timeout=3s --start-period=40s --retries=3 \
  CMD curl -f http://localhost:8080/actuator/health || exit 1

# 실행
ENTRYPOINT ["java", "-jar", "app.jar"]