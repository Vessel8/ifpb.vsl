# This is my Dockerfile

# Building the application

FROM bellsoft/liberica-openjre-debian:21-cds AS builder
WORKDIR /builder
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} application.jar
RUN java -Djarmode=tools -jar application.jar extract --layers --destination extracted


# Creating the runtime image

FROM bellsoft/liberica-openjre-debian:21-cds
WORKDIR /application
COPY --from=builder /builder/extracted/dependencies/ ./
COPY --from=builder /builder/extracted/spring-boot-loader/ ./
COPY --from=builder /builder/extracted/snapshot-dependencies/ ./
COPY --from=builder /builder/extracted/application/ ./
EXPOSE 8010


# Starting the application

ENTRYPOINT ["java", "-jar", "application.jar"]