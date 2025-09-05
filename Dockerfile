# ---------- BUILD STAGE ----------
# Este estágio prepara TUDO: corrige o gradlew e compila o JAR.
FROM gradle:8.13-jdk21 AS build
WORKDIR /app

# Copiar tudo para o estágio de build
COPY . .

# Instalar dos2unix e corrigir o gradlew AQUI
RUN apt-get update && apt-get install -y dos2unix && \
    dos2unix gradlew && \
    chmod +x gradlew

# Build do JAR (usado em produção)
# Usamos ./gradlew para garantir que a versão corrigida está sendo usada
RUN ./gradlew bootJar --no-daemon

# ---------- RUNTIME STAGE ----------
# Este estágio será a imagem final, magra e correta.
FROM eclipse-temurin:21-jdk
WORKDIR /app

# 1. Copiar o JAR do build stage (para o modo de produção)
COPY --from=build /app/build/libs/*.jar app.jar

# 2. Copiar SOMENTE os arquivos do Gradle necessários para o modo DEV.
#    Crucial: Copiamos do estágio 'build', onde o 'gradlew' já foi corrigido!
COPY --from=build /app/gradlew ./
COPY --from=build /app/gradle ./gradle
COPY --from=build /app/build.gradle ./
COPY --from=build /app/settings.gradle ./

# Porta da aplicação
EXPOSE 8080

# Entrypoint inteligente (sem alterações)
ARG DEV=false
ENV DEV=${DEV}
ENTRYPOINT ["sh", "-c", "if [ \"$DEV\" = 'true' ]; then ./gradlew bootRun; else java -jar app.jar; fi"]