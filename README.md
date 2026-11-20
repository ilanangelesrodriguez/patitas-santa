# Patitas del Santa — API REST con Spring Boot

Proyecto académico de **Arquitectura de Software, Semana 10 (UNS)**. Persistencia y arquitectura de tres capas para la Clínica Veterinaria «Patitas del Santa».

## Tecnología y requisitos

- **Java 21** (compatible con Amazon Corretto 21).
- **Spring Boot 4.2.0**, **Gradle 9.9.2**, Spring MVC, Spring Data JPA, Hibernate, Bean Validation, Lombok.
- **H2 en memoria (perfil predeterminado)**. **PostgreSQL/Neon (perfil `postgres`, opcional)**.
- IDE: **IntelliJ IDEA**. No se necesita instalar Gradle globalmente. Hay que tener conexión a Internet en la primera ejecución para descargar Gradle y dependencias.

## Inicio rápido en IntelliJ IDEA / Windows 11

1. Extrae este ZIP a una carpeta local (sin espacios o rutas muy largas, de preferencia).
2. Abre IntelliJ IDEA > **Open** > selecciona la carpeta `patitas-santa` (que contiene `build.gradle`).
3. Confirma que **Project SDK** y **Gradle JVM** sean *JDK 21* (Amazon Corretto 21 es válido). IntelliJ reconocerá Gradle; espera la sincronización.
4. Ejecuta `PatitasSantaApplication.java` mediante el botón ▶, o desde PowerShell en la raíz:

   ```powershell
   .\gradlew.bat bootRun
   ```

5. Abre la API en `http://localhost:8080/api/duenos`. Al principio mostrará `[]`.
6. Importa `postman/Patitas-del-Santa.postman_collection.json` en Postman y ejecuta las solicitudes **en orden**; la colección captura los IDs automáticamente.

**Nota del wrapper:** el primer uso de `gradlew.bat` descarga el JAR oficial del *Gradle Wrapper*, comprueba SHA-256 y posteriormente descarga Gradle 9.9.2. El proyecto también puede importarse directamente desde IntelliJ. Si ya clonaste el repositorio original, puedes conservar su `gradle/wrapper/gradle-wrapper.jar` oficial.

### H2 Console (obligatoria para la práctica)

- URL: `http://localhost:8080/h2-console`
- **JDBC URL:** `jdbc:h2:mem:patitassanta`
- **User Name:** `sa`
- **Password:** vacío
- Haz clic en **Connect** y consulta `DUENOS`, `MASCOTAS`, `VETERINARIOS`, `CITAS` (los nombres pueden visualizarse en mayúsculas).
- H2 está **en memoria**; al detener el servidor se pierden los registros. Este es el comportamiento esperado para este ejercicio.

### Conexión opcional con Neon PostgreSQL

El enunciado requiere H2, pero incluimos un perfil adicional sin credenciales en el repositorio. **No pegues contraseñas reales en el código, capturas de pantalla ni commits.** Si una contraseña se ha compartido en un chat, reemplázala en Neon.

En PowerShell (reemplaza los marcadores con tus datos **nuevos**):

```powershell
$env:SPRING_PROFILES_ACTIVE = 'postgres'
$env:DB_URL = 'jdbc:postgresql://TU_ENDPOINT_NEON/neondb?sslmode=require&channelBinding=require'
$env:DB_USERNAME = 'neondb_owner'
$env:DB_PASSWORD = 'TU_NUEVA_CONTRASENA'
.\gradlew.bat bootRun
```

En IntelliJ: **Run > Edit Configurations > Environment variables**. Declara `SPRING_PROFILES_ACTIVE=postgres`, `DB_URL`, `DB_USERNAME`, `DB_PASSWORD`. El perfil `postgres` utiliza `ddl-auto=update`, útil en desarrollo académico, pero no recomendado como migración de esquema en producción. En Neon se crean las mismas cuatro tablas al arrancar.

Para volver a H2 desde PowerShell:

```powershell
Remove-Item Env:SPRING_PROFILES_ACTIVE -ErrorAction SilentlyContinue
.\gradlew.bat bootRun
```

## Estructura y capas

```text
src/main/java/org/uns/patitassanta/
  PatitasSantaApplication.java
  model/              # Entidades JPA y enums
  repository/         # Interfaces JpaRepository y consultas
  service/            # Validaciones y reglas de negocio
  controller/         # REST CRUD de cada entidad
  dto/                # Contratos de entrada/salida, sin ciclos JSON
  exception/          # 400 / 404 / 409 y formato de errores
src/main/resources/
  application.properties           # H2 por defecto
  application-postgres.properties  # Neon opcional
src/test/java/                   # Pruebas de contexto y reglas
postman/                         # Colección con 3, 5, 2 y 4 registros
```

**Relaciones**: `Dueno (1) -> (N) Mascota`, `Mascota (1) -> (N) Cita` y `Veterinario (1) -> (N) Cita`. Una mascota siempre tiene un dueño y cada cita tiene mascota + veterinario.

## Endpoints principales

| Método | Ruta | Función |
| --- | --- | --- |
| GET / POST | `/api/duenos` | Listar / crear |
| GET / PUT / DELETE | `/api/duenos/{id}` | Obtener / actualizar / eliminar |
| GET | `/api/duenos/dni/{dni}` | Buscar dueño por DNI |
| GET / POST | `/api/mascotas` | Listar / crear |
| GET / PUT / DELETE | `/api/mascotas/{id}` | Obtener / actualizar / eliminar |
| GET | `/api/mascotas/por-dni/{dni}` | **Buscar mascotas por DNI del dueño** |
| GET / POST | `/api/veterinarios` | Listar / crear |
| GET / PUT / DELETE | `/api/veterinarios/{id}` | Obtener / actualizar / eliminar |
| GET / POST | `/api/citas` | Listar / crear |
| GET / PUT / DELETE | `/api/citas/{id}` | Obtener / actualizar / eliminar |
| GET | `/api/citas/por-veterinario/{id}` | Citas por veterinario |
| GET | `/api/citas/por-mascota/{id}` | Citas por mascota |

Revisa [docs/ENDPOINTS.md](docs/ENDPOINTS.md) para ejemplos JSON, estados y reglas.

## Ejecución de pruebas y empaquetado

```powershell
.\gradlew.bat test
.\gradlew.bat bootJar
```

El ejecutable queda en `build/libs/`. Para lanzar el JAR:

```powershell
java -jar build/libs/patitas-santa-1.0.0.jar
```

## Reglas implementadas

- DNI de **8 dígitos y único**. Colegiatura de veterinario **única**.
- Mascota obligatoriamente asociada a un dueño; fecha de nacimiento no futura; peso no negativo.
- Al crear, una cita siempre es `PROGRAMADA`, con fecha/hora futura, motivo, mascota y veterinario **activo**.
- No se aceptan dos citas `PROGRAMADA` al mismo horario para el mismo veterinario o mascota.
- Al editar una cita, el estado puede pasar a `ATENDIDA` (obliga a diagnóstico y costo >= 0) o `CANCELADA`.
- No se permite volver a `PROGRAMADA` una cita finalizada.
- La API devuelve `400` para validaciones, `404` para no encontrados y `409` para duplicados o bloqueos por relaciones.
- Para no romper la integridad histórica, no se eliminan dueños con mascotas, mascotas con citas ni veterinarios con citas.

> Limitación académica: el chequeo de duplicidad horaria usa consultas transaccionales; para concurrencia real de varios usuarios se necesitaría reforzar la regla con bloqueos o restricciones de base de datos.

## Subir el ZIP a tu repositorio GitHub

El ZIP **no modifica tu repositorio remoto**. Para reemplazar la base inicial después de revisar el contenido, copia los archivos descomprimidos a tu clon local. Mantén tus archivos `.git` y tu wrapper existente, si los tienes. Luego:

```powershell
git status
git add .
git commit -m "feat: implementar API veterinaria con Spring Boot, JPA y H2"
git push origin main
```

Repositorio de referencia: `https://github.com/ilanangelesrodriguez/patitas-santa`
