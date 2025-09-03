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
