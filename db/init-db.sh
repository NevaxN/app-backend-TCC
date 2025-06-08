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

sleep 5

docker exec -i tcc-postgres psql -U admin -d tccdb <<EOF
CREATE TABLE IF NOT EXISTS usuarios (
    id SERIAL PRIMARY KEY,
    login VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL
);

INSERT INTO usuarios (login, password) VALUES ('admin', '1234') ON CONFLICT DO NOTHING;
EOF
