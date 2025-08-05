✅ [Redis 환경 구성 Task] – RabbitMQ 형식 기반 절차
{
    "taskId": "local-docker-redis-setup",
    "title": "Redis 설치 및 Windows Docker 개발환경 구성",
    "category": "infra-setup",
    "priority": "high",
    "description": "Windows 환경에서 Docker를 사용하여 Redis 서버를 설치 및 실행하며, 개발환경에서 테스트 가능한 상태로 구성합니다.",
    "tags": ["redis", "docker", "windows", "infra", "cache"],
    "prerequisites": [
    "Docker Desktop이 Windows에 설치되어 있고 실행 중일 것",
    "6379 포트가 사용 중이지 않아야 함"
    ],
    "executionSteps": [
    {
    "stepNo": 1,
    "name": "Redis 이미지 다운로드",
    "description": "Docker Hub에서 Redis 이미지를 로컬로 pull",
    "command": "docker pull redis:7.2"
    },
    {
    "stepNo": 2,
    "name": "Redis 기본 컨테이너 실행",
    "description": "기본적인 포트 설정과 함께 Redis 실행",
    "command": "docker run -d --name redis-local -p 6379:6379 redis:7.2"
    },
    {
    "stepNo": 3,
    "name": "Redis 영속성 볼륨 적용 실행 (옵션)",
    "description": "데이터 보존을 위한 볼륨 마운트 및 AOF 설정으로 실행",
    "command": "docker run -d --name redis-vol -p 6379:6379 -v redis-data:/data redis:7.2 --appendonly yes"
    },
    {
    "stepNo": 4,
    "name": "비밀번호 설정 실행 (옵션)",
    "description": "Redis에 접속 비밀번호를 설정하여 보안 강화",
    "command": "docker run -d --name redis-auth -p 6380:6379 redis:7.2 redis-server --requirepass skcc-redis123"
    },
    {
    "stepNo": 5,
    "name": "실행 상태 확인",
    "description": "Redis 컨테이너가 정상 작동 중인지 확인",
    "command": "docker ps"
    },
    {
    "stepNo": 6,
    "name": "Redis CLI로 접속 테스트",
    "description": "Redis CLI를 통해 연결 확인 (비밀번호 설정 시 AUTH 필요)",
    "command": "docker exec -it redis-local redis-cli"
    }
    ],
    "env": {
    "REDIS_PORT": "6379",
    "REDIS_PASSWORD": "skcc-redis123 (옵션)",
    "REDIS_VOLUME": "redis-data"
    },
    "outputs": {
    "containerName": "redis-local",
    "exposedPort": 6379,
    "volume": "redis-data",
    "authInfo": "비밀번호 필요 시 AUTH 명령어로 인증"
    },
    "owner": "skcc-aa-team"
}

📌 주요 특징 요약
    항목	설명
    설치 방식	Docker 기반 Redis 설치
    실행 포트	6379 (기본), 6380 (auth 설정용)
    데이터 보존	-v redis-data:/data 볼륨 마운트로 구성
    보안 설정	--requirepass 옵션 사용 가능
    자동화 대상	Cursor AI, GitHub Workflow, 커스텀 DevOps 도구 등에 통합 가능

