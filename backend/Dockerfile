FROM openjdk:8 as builder
WORKDIR /build
#Copiar maven.
COPY backend/mvnw /build/mvnw
COPY backend/.mvn/ /build/.mvn/
#Copiar pom.xml y traer dependencias.
COPY backend/pom.xml /build/pom.xml
RUN ./mvnw dependency:resolve
COPY backend/src/ /build/src/
#Enpaquetar el proyecto, saltándose los tests.
ARG DATABASE=logistica
ARG DRIVER=org.h2.Driver
ARG USER=sa
ARG PASS=sa
ARG HOST=mem
ARG PROTOCOL=h2
ARG PORT=0
ARG TEST=true
RUN ./mvnw -DDATABASE=$DATABASE -DDRIVER=$DRIVER -DUSER=$USER -DPASS=$PASS -DHOST=$HOST -DPROTOCOL=$PROTOCOL -DPORT=$PORT -DTEST=$TEST package
#-DskipTests
RUN mkdir -p target/dependency && (cd target/dependency; jar -xf ../*.jar)

FROM openjdk:8-jre-alpine
EXPOSE 8080
ARG DEPENDENCY=/build/target/dependency
# Copiar las carpetas y reestructurarlas.
COPY --from=builder ${DEPENDENCY}/BOOT-INF/lib /app/lib
COPY --from=builder ${DEPENDENCY}/META-INF /app/META-INF
COPY --from=builder ${DEPENDENCY}/BOOT-INF/classes /app

ENTRYPOINT ["java","-cp","app:app/lib/*","com.logistica.demo.LogisticaBackApplication"]