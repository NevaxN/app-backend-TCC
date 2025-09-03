@echo off
REM Derruba containers antigos (com volumes)
docker-compose down -v

REM Sobe Postgres e backend em modo DEV
set DEV=true
docker-compose up -d --build postgres app
