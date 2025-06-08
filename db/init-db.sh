#!/bin/bash

docker stop tcc-postgres
docker rm tcc-postgres

docker run --name tcc-postgres \
  -e POSTGRES_USER=admin \
  -e POSTGRES_PASSWORD=admin \
  -e POSTGRES_DB=tccdb \
  -p 5432:5432 \
  -v tcc_pgdata:/var/lib/postgresql/data \
  -d postgres:16
