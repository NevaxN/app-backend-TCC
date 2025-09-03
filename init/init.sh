#!/bin/bash

# Derruba containers antigos
docker-compose down -v

# Sobe o Postgres e o backend em modo DEV
DEV=true docker-compose up -d --build postgres app
