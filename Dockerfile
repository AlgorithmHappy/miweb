 # Usa una imagen base de OpenJDK para Java 21 (versión slim para reducir tamaño)
FROM openjdk:21-jdk-slim

# Establece el directorio de trabajo dentro del contenedor
WORKDIR /app

# Copia los archivos de configuración de Gradle y el wrapper desde la subcarpeta
# Ahora, la ruta debe ser completa desde la raíz del repositorio
COPY backend/mail_whatsapp_send/build.gradle ./
COPY backend/mail_whatsapp_send/settings.gradle ./
COPY backend/mail_whatsapp_send/gradle ./gradle

# Asegura que el script de Gradle sea ejecutable
COPY backend/mail_whatsapp_send/gradlew .
RUN chmod +x gradlew

# Descarga las dependencias de Gradle
RUN ./gradlew dependencies

# Copia el resto del código fuente de tu aplicación desde la subcarpeta
COPY backend/mail_whatsapp_send/src ./src

# Construye la aplicación Spring Boot.
RUN ./gradlew build -x test

# Expone el puerto en el que tu aplicación Spring Boot escucha (por defecto 8080)
EXPOSE 8080

# Comando para ejecutar la aplicación cuando el contenedor se inicie
# Mantén el PATH del JAR igual, ya que build/libs/ es relativo a /app dentro del contenedor
# Asegúrate de que 'mail_whatsapp_send-0.0.1-SNAPSHOT.jar' sea el nombre correcto de tu JAR compilado.
ENTRYPOINT ["java", "-jar", "build/libs/mail_whatsapp_send-0.0.1-SNAPSHOT.jar"]
