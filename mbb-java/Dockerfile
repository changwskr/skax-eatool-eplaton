# MBB Java - AI Coding Workflow Dockerfile
FROM openjdk:11-jre-slim

# 메타데이터
LABEL maintainer="SKAX Team <dev@skax.com>"
LABEL description="MBB Java - AI Coding Workflow Application"
LABEL version="1.0.0"

# 작업 디렉토리 설정
WORKDIR /app

# 애플리케이션 JAR 파일 복사
COPY target/mbb-java-*.jar app.jar

# 포트 노출
EXPOSE 8086

# 환경 변수 설정
ENV JAVA_OPTS="-Xmx512m -Xms256m"
ENV SPRING_PROFILES_ACTIVE="docker"

# 헬스체크 설정
HEALTHCHECK --interval=30s --timeout=3s --start-period=60s --retries=3 \
  CMD curl -f http://localhost:8086/mbb/actuator/health || exit 1

# 애플리케이션 실행
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar app.jar"] 