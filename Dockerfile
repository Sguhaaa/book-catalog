FROM openjdk:11
VOLUME /tmp
ADD target/book-catalog-0.0.1-SNAPSHOT.jar book-catalog.jar
ENTRYPOINT ["java","-jar","book-catalog.jar"]