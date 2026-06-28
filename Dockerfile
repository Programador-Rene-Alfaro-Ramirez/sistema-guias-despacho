# 1. Utilizamos una imagen base oficial de Java 17 súper ligera (Alpine)
FROM eclipse-temurin:17-jdk-alpine

# 2. Creamos un volumen temporal (ayuda a que Spring Boot inicie más rápido)
VOLUME /tmp

# 3. Exponemos el puerto en el que corre nuestra aplicación
EXPOSE 8080

# 4. Apuntamos al archivo .jar que genera Maven al compilar
ARG JAR_FILE=target/*.jar

# 5. Copiamos ese archivo .jar dentro del contenedor y lo renombramos a "app.jar"
COPY ${JAR_FILE} app.jar

# 6. Comando definitivo para ejecutar la aplicación cuando inicie el contenedor
ENTRYPOINT ["java", "-jar", "/app.jar"] 