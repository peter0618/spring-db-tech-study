#!/bin/bash

# 스크립트 파일이 위치한 디렉토리
SCRIPT_DIR="$(dirname "$0")"

# docker-compose.yml 파일이 있는 상대 경로 (예: 스크립트 파일의 상위 디렉토리)
COMPOSE_DIR="$SCRIPT_DIR/local-infra/database"

# 해당 디렉토리로 이동
cd "$COMPOSE_DIR" || {
  echo "디렉토리로 이동할 수 없습니다: $COMPOSE_DIR"
  exit 1
}

# docker-compose up -d 명령어 실행
docker-compose up -d

# 명령어 실행 결과 확인
if [ $? -eq 0 ]; then
  echo "docker-compose가 성공적으로 실행되었습니다."
else
  echo "docker-compose 실행 중 오류가 발생했습니다."
  exit 1
fi