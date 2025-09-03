# ---------- BUILD STAGE ----------
FROM gradle:8.13-jdk21 AS build
WORKDIR /app

# Copiar os arquivos do projeto
COPY . .

# Build do JAR (usado em produção)
RUN gradle bootJar --no-daemon

# ---------- RUNTIME STAGE ----------
FROM eclipse-temurin:21-jdk
WORKDIR /app

# Copiar o JAR do build stage
COPY --from=build /app/build/libs/*.jar app.jar

# Copiar scripts de desenvolvimento (opcional, mas útil)
COPY . .

# Porta da aplicação
EXPOSE 8080

# Entrypoint inteligente: se DEV=true, roda bootRun, senão roda o JAR
ARG DEV=false
ENV DEV=${DEV}

ENTRYPOINT ["sh", "-c", "if [ \"$DEV\" = 'true' ]; then ./gradlew bootRun; else java -jar app.jar; fi"]
