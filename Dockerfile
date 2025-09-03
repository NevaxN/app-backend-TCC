# ---------- BUILD STAGE ----------
FROM gradle:8.13-jdk21 AS build
WORKDIR /app

# Copiar os arquivos do projeto
COPY . .

# Garante que o gradlew está no formato e com as permissões corretas
# Adiciona o comando dos2unix para converter line endings e chmod para permissões
RUN apt-get update && apt-get install -y dos2unix
RUN dos2unix gradlew && chmod +x gradlew

# Build do JAR (usado em produção)
RUN gradle bootJar --no-daemon

# ---------- RUNTIME STAGE ----------
FROM eclipse-temurin:21-jdk
WORKDIR /app

# Copiar o JAR do build stage
COPY --from=build /app/build/libs/*.jar app.jar

# Copiar scripts de desenvolvimento (opcional, mas útil)
# NOTE: O gradlew já está no volume de dev, mas esta linha garante que os outros
# scripts e arquivos de configuração estejam disponíveis para o hot reload.
COPY . .

# Porta da aplicação
EXPOSE 8080

# Entrypoint inteligente: se DEV=true, roda bootRun, senão roda o JAR
ARG DEV=false
ENV DEV=${DEV}

# O ENTRYPOINT é melhor ser mais simples e deixar o docker-compose lidar com a decisão.
# De qualquer forma, esta lógica está correta.
ENTRYPOINT ["sh", "-c", "if [ \"$DEV\" = 'true' ]; then ./gradlew bootRun; else java -jar app.jar; fi"]