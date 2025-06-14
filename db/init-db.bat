@echo off
REM Para de e remove o container se ele existir
docker stop tcc-postgres
docker rm tcc-postgres

REM Cria o container do PostgreSQL
docker run --name tcc-postgres ^
    -e POSTGRES_USER=admin ^
    -e POSTGRES_PASSWORD=admin ^
    -e POSTGRES_DB=tccdb ^
    -p 5432:5432 ^
    -v tcc_pgdata:/var/lib/postgresql/data ^
    -d postgres:16

REM Aguarda 5 segundos para o banco subir
timeout /t 5 /nobreak > NUL

REM Executa os comandos SQL dentro do container
echo CREATE TABLE IF NOT EXISTS usuarios ( > temp.sql
echo     id SERIAL PRIMARY KEY, >> temp.sql
echo     login VARCHAR(255) NOT NULL UNIQUE, >> temp.sql
echo     password VARCHAR(255) NOT NULL >> temp.sql
echo ); >> temp.sql
echo. >> temp.sql
echo INSERT INTO usuarios (login, password) VALUES ('admin', '1234') ON CONFLICT DO NOTHING; >> temp.sql

docker exec -i tcc-postgres psql -U admin -d tccdb < temp.sql

REM Limpa o arquivo temporário
del temp.sql
