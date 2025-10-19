# Dockerfile
FROM eclipse-temurin:17-jdk-alpine

# Argumentos que podemos pasar al construir la imagen
ARG JAR_FILE=target/*.jar

# Crear un usuario no-root para mayor seguridad
RUN addgroup -S spring && adduser -S spring -G spring
USER spring

# Directorio de trabajo
WORKDIR /app

# Copiar el JAR desde tu máquina al contenedor
COPY ${JAR_FILE} app.jar

# Exponer el puerto 8081
EXPOSE 8081

# Comando para ejecutar la aplicación
ENTRYPOINT ["java", "-jar", "app.jar"]