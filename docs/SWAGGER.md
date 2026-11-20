# Swagger UI y OpenAPI — Patitas del Santa

## Compatibilidad

- Spring Boot **4.2.0** y Java **21** (base del repositorio consultado).
- Springdoc OpenAPI **3.2.0** (`org.springdoc:springdoc-openapi-starter-webmvc-ui`).
- Se conserva la arquitectura model/repository/service/controller y las reglas de negocio.
- H2 local y perfil `postgres` para Neon/Koyeb funcionan con la misma documentación.

## URLs

| Recurso | URL local | URL Koyeb |
| --- | --- | --- |
| Interfaz interactiva | `http://localhost:8080/swagger-ui.html` | `https://TU_DOMINIO.koyeb.app/swagger-ui.html` |
| OpenAPI JSON | `http://localhost:8080/v3/api-docs` | `https://TU_DOMINIO.koyeb.app/v3/api-docs` |
| OpenAPI YAML | `http://localhost:8080/v3/api-docs.yaml` | `https://TU_DOMINIO.koyeb.app/v3/api-docs.yaml` |

El dominio Koyeb es un ejemplo, reemplázalo por el que aparezca en tu panel.

## Probar desde Swagger

1. Abre `/swagger-ui.html`.
2. Despliega **Dueños** y usa `POST /api/duenos` → **Try it out** → **Execute** para obtener el ID creado.
3. Registra un veterinario y una mascota con `duenoId` válido.
4. Programa una cita con `fechaHora` **futura** (ISO-8601, por ejemplo una fecha futura respecto al día de la prueba).
5. Verifica los `GET`, la búsqueda por DNI y las respuestas `400`, `404`, `409` según corresponda.

Los ejemplos del Swagger no ejecutan automáticamente peticiones. Siempre comprueba los IDs y fechas actuales.

## Importar la especificación en Postman

Import > Link > `http://localhost:8080/v3/api-docs`, con la aplicación en ejecución. Postman podrá leer la definición OpenAPI generada.

## Implementación

- `build.gradle`: añade la dependencia de Springdoc 3.x.
- `src/main/java/org/uns/patitassanta/config/OpenApiConfig.java`: título, versión y descripción de la API.
- `src/main/java/org/uns/patitassanta/controller/`: `@Tag` y `@Operation` documentan cada endpoint; creación/eliminación declaran estado 201/204.
- `src/main/java/org/uns/patitassanta/dto/*Request.java`: `@Schema` describe entradas y proporciona ejemplos.
- `src/main/resources/application.properties`: configura rutas y orden de operaciones de Swagger UI.

## Publicación en Koyeb

Al subir el commit con Autodeploy activado, Koyeb reconstruirá el contenedor Docker. No necesitas un puerto distinto: Swagger utiliza el mismo puerto HTTP de la API (`PORT`, predeterminado 8080).

Si Swagger no abre, revisa primero `https://TU_DOMINIO/v3/api-docs`. Si devuelve un JSON válido, inspecciona la ruta de la UI y los logs de Koyeb.

**Seguridad**: el repositorio académico no implementa autenticación. Swagger UI permite probar operaciones de escritura si la API es pública; no uses datos personales reales. Para un despliegue de producción real, añade autenticación, autorización y restricciones de acceso según el entorno.
