FROM openjdk:8
ENV SPRING_PROFILES_ACTIVE prod
VOLUME /tmp
EXPOSE 8082
ADD ./target/ms-movimiento-0.0.1-SNAPSHOT.jar ms-movimiento.jar
ENTRYPOINT ["java","-jar","/ms-movimiento.jar"]