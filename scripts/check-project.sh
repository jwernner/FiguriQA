#!/usr/bin/env sh

set -eu

echo "FiguriQA 2026 - verificacao rapida do projeto"

if ! command -v docker >/dev/null 2>&1; then
  echo "Docker nao encontrado. Instale o Docker antes de executar o projeto."
  exit 1
fi

if ! docker compose version >/dev/null 2>&1; then
  echo "Docker Compose nao encontrado. Verifique sua instalacao do Docker."
  exit 1
fi

echo "Docker encontrado:"
docker --version

echo "Docker Compose encontrado:"
docker compose version

echo "Validando docker-compose.yml..."
docker compose config >/dev/null

echo "Tudo certo. Para subir o ambiente, execute:"
echo "docker compose up --build"
echo ""
echo "Depois acesse:"
echo "http://localhost:8080"
