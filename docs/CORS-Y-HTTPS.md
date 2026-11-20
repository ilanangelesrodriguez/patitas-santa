# Corrección de Swagger HTTP/HTTPS y CORS — Patitas del Santa

## Problema original

La UI se abría por `https://.../swagger-ui/index.html`, pero Swagger enviaba
`http://.../api/citas`. Los navegadores bloquean normalmente las solicitudes
HTTP iniciadas desde una página HTTPS (mixed content). **No es necesariamente CORS.**

## Archivos agregados y modificados

- `config/CorsConfig.java` **nuevo**: CORS global para rutas `/api/**`, todos los
  orígenes y los métodos GET, POST, PUT, PATCH, DELETE y OPTIONS. No admite
  cookies o credenciales cross-origin (`allowCredentials(false)`).
- `config/OpenApiConfig.java` **modificado**: fija `servers: [{url: "/"}]`, por lo
  que Swagger utiliza el protocolo y el dominio donde está abierta la UI.
- `application.properties` **modificado**: `server.forward-headers-strategy=framework`
  permite que Spring respete los encabezados HTTPS del proxy.
- `OpenApiConfigTests.java` **nuevo**: prueba unitaria de la URL relativa.

## Instalar

1. Extrae el ZIP.
2. Copia los archivos de la carpeta `patitas-santa/` en la **raíz** de tu
   repositorio local. Sobrescribe solo los dos archivos modificados que aparecen
   en este parche; los demás son nuevos. **No reemplaces otras carpetas**.
3. En IntelliJ abre Gradle y recarga el proyecto.
4. Ejecuta en PowerShell:

   ```powershell
   .\gradlew.bat test
   .\gradlew.bat bootRun
   ```

5. Abre `http://localhost:8080/swagger-ui/index.html`.
6. Sube los cambios si las pruebas pasan:

   ```powershell
   git add src/main/java/org/uns/patitassanta/config src/main/resources/application.properties src/test/java/org/uns/patitassanta/OpenApiConfigTests.java docs/CORS-Y-HTTPS.md
   git commit -m "fix: permitir CORS en API y corregir Swagger HTTPS tras proxy"
   git push origin main
   ```

7. Koyeb/Mistral Run debe reconstruir y desplegar desde el commit nuevo.

## Verificación de la API en producción

Visita `https://TU-DOMINIO/swagger-ui/index.html` y presiona F5 o Ctrl+Shift+R.
Abre `https://TU-DOMINIO/v3/api-docs`: revisa que la propiedad `servers` sea
`[{"url":"/", ...}]`.

Al ejecutar Try it out -> POST /api/citas, en DevTools > Network se debe observar
una URL `https://TU-DOMINIO/api/citas` (no `http://...`).

**Prueba preflight CORS** (cambia el dominio):

```bash
curl -i -X OPTIONS 'https://TU-DOMINIO/api/citas' \
  -H 'Origin: https://otro-sitio.example' \
  -H 'Access-Control-Request-Method: POST' \
  -H 'Access-Control-Request-Headers: content-type'
```

El servidor debe responder con encabezados `Access-Control-Allow-Origin` y
`Access-Control-Allow-Methods`, salvo que otro proxy los modifique.

Ejemplo de prueba POST (IDs 1 existentes, fecha futura):

```bash
curl -i 'https://TU-DOMINIO/api/citas' \
  -H 'Content-Type: application/json' \
  --data '{"mascotaId":1,"veterinarioId":1,"fechaHora":"2027-06-20T10:30:00","motivo":"Vacunación anual"}'
```

Si la red ya funciona pero recibes `400`, `404` o `409`, el problema es de
validaciones, IDs inexistentes o reglas de negocio, no de CORS.

## Seguridad

CORS **no equivale a autenticación**. Este parche abre la API a cualquier origen
web, incluidas operaciones de escritura y eliminación. Es solo para demos/datos
ficticios. Si el sistema se usará con dueños reales, configura autenticación,
autorización y dominios permitidos antes de hacerlo público.

Si tu backend está expuesto directamente al público sin un reverse proxy que
valide los encabezados `Forwarded`/`X-Forwarded-*`, no uses la opción
`server.forward-headers-strategy=framework` sin revisar esa topología.
