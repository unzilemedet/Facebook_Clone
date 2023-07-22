# İşletim sistemi ve Java JDK eklenir.
# FROM amazoncorretto:17 -- amazon corretto ile java jdk17 sürümü kullanılacak demektir.
FROM azul/zulu-openjdk-alpine:17.0.7
# build aldığımız jar dosyasını docker imajımızın içine kopyalıyoruz.
# COPY ConfigServerGit/build/libs/ConfigServerGit-v.1.0.jar app.jar
# COPY AuthService/build/libs/AuthService-v.1.0.jar app.jar
# COPY UserService/build/libs/UserService-v.1.0.jar app.jar
COPY ApiGatewayService/build/libs/ApiGatewayService-v.1.0.jar app.jar

# docker imajımızın çalışması için java uygulamamızı tetikliyoruz.
ENTRYPOINT ["java","-jar","/app.jar"]