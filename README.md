# 🎓 Foro Hub API

![Java](https://img.shields.io/badge/Java-21-ED8B00?style=for-the-badge)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.5-6DB33F?style=for-the-badge&logo=springboot&logoColor=white)
![MySQL](https://img.shields.io/badge/MySQL-8.0-005C84?style=for-the-badge&logo=mysql&logoColor=white)
![Security](https://img.shields.io/badge/Spring%20Security-JWT-6DB33F?style=for-the-badge&logo=springsecurity&logoColor=white)

---

## 📖 Descripción

**Foro Hub** es una API RESTful que gestiona la comunicación en un entorno académico.  
Permite a los usuarios crear tópicos, publicar respuestas y moderar discusiones según su rol.

El sistema centraliza el conocimiento de forma estructurada, segura y escalable, aplicando autenticación stateless y autorización basada en roles.

---

## 💡 Competencias Técnicas Aplicadas

Este proyecto fue construido con un enfoque profesional para demostrar:

### 🔐 Control de Acceso Basado en Roles
- **ALUMNO:** crea y edita sus propios tópicos, publica respuestas.
- **PROFESOR:** modera discusiones y marca respuestas como “Solución”.
- **ADMIN:** gestiona usuarios y controla completamente la plataforma.

### 🧠 Lógica de Negocio
- Prevención de tópicos duplicados (título + mensaje).
- Validaciones a nivel de servicio.
- Eliminación lógica para preservar historial.

### 🧪 Calidad y Testing
- Tests de integración con **JUnit 5 + MockMvc**.
- Validación de casos exitosos (200/201).
- Manejo de errores (400).
- Protección de rutas (403).
- Uso de base H2 en memoria para pruebas aisladas.

---

## 🛠️ Stack Tecnológico

| Categoría        | Tecnología | Propósito |
|------------------|------------|-----------|
| Lenguaje         | Java 21 | Base del desarrollo |
| Framework        | Spring Boot 3 | Construcción de la API |
| Web              | Spring Web | Exposición de endpoints REST |
| Persistencia     | Spring Data JPA | Acceso a datos |
| ORM              | Hibernate | Mapeo objeto-relacional |
| Seguridad        | Spring Security | Protección de rutas |
| Autenticación    | JWT (Auth0) | Autenticación stateless |
| Base de Datos    | MySQL 8 | Entorno principal |
| Testing          | JUnit 5 + MockMvc | Pruebas de integración |
| Testing DB       | H2 Database | Base en memoria para tests |
| Build Tool       | Maven | Gestión de dependencias |

---

## 📊 Diagrama Entidad-Relación (DER)

El modelo de datos asegura la integridad relacional entre los usuarios, los cursos a los que pertenecen y sus interacciones en el foro (tópicos y respuestas).

### Entidades principales:
- Usuario
- Curso
- Topico
- Respuesta

### Relaciones clave:

```
Usuario (1) ────< (N) Topico
Usuario (1) ────< (N) Respuesta
Topico  (1) ────< (N) Respuesta
Curso   (1) ────< (N) Topico
```

### DER
<img width="695" height="473" alt="{88390DB4-FA72-46E6-9ADE-6B2D1623BF15}" src="https://github.com/user-attachments/assets/63c0b49c-3b7c-4875-95ba-f6d393eefe1e" />

---

## 🔐 Seguridad

El sistema utiliza **Autenticación Stateless**. Las rutas de la API están protegidas mediante los filtros de Spring Security. 

Al enviar credenciales válidas al endpoint `/login`, el servidor devuelve un **Token JWT**. Este token contiene la identidad y el rol del usuario, y debe ser incluido en el encabezado (`Authorization: Bearer <token>`) de cualquier petición a las rutas protegidas. Al no guardar sesiones en el servidor, la API garantiza un rendimiento eficiente y fácilmente escalable.

---

## 🧠 Decisiones Técnicas

### 📦 Arquitectura "Package by Feature"
El código está organizado por dominio (Topico, Usuario, Respuesta, Curso) en lugar de la separación clásica por capas globales.  
Esto mejora la cohesión, facilita el mantenimiento y prepara el proyecto para escalar.

### ⚠ Manejo Global de Errores
Se implementó un manejador centralizado con `@RestControllerAdvice` para:
- Capturar errores de validación.
- Manejar excepciones de base de datos.
- Retornar respuestas HTTP consistentes (400, 403, 404).

### 🧪 Estrategia de Testing
- Tests automatizados con base H2 en memoria.
- Aislamiento del entorno de desarrollo.
- Validación de seguridad y reglas de negocio.

---

## ⚙ Cómo correr el proyecto

### ✅ 1. Requisitos previos

- Java 21 o superior
- Maven 3.9+
- MySQL 8 en ejecución

Verificar versiones:

```bash
java -version
mvn -version
```

---

### 🗄 2. Crear base de datos

Ingresar a MySQL y ejecutar:

```sql
CREATE DATABASE foro_hub;
```

---

### 🔐 3. Configurar variables de entorno

En `application.properties`:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/foro_hub
spring.datasource.username=tu_usuario
spring.datasource.password=tu_contraseña

jwt.secret=tu_clave_secreta
```

---

### 📥 4. Clonar el repositorio

```bash
git clone https://github.com/micabenitez/challenge-forohub.git
cd challenge-forohub
```

---

### ▶ 5. Ejecutar la aplicación

Con Maven Wrapper:

```bash
./mvnw spring-boot:run
```

O con Maven instalado:

```bash
mvn spring-boot:run
```

---

### 🌐 6. Acceder a la API

La aplicación estará disponible en:

```
http://localhost:8080
```
