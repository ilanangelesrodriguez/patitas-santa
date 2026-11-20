# Ejemplos REST — `http://localhost:8080`

Todos los POST y PUT incluyen `Content-Type: application/json`. Los ID son numéricos generados por la base de datos. **Ejecuta los POST en orden: dueño → mascota → veterinario → cita.**

## 1. Crear dueño

`POST /api/duenos`

```json
{
  "dni": "74859632",
  "nombres": "Carmen",
  "apellidos": "García Pérez",
  "celular": "987654321",
  "correo": "carmen@example.com",
  "direccion": "Nuevo Chimbote"
}
```

El DNI debe tener ocho dígitos y ser único.

## 2. Crear mascota

`POST /api/mascotas`

```json
{
  "nombre": "Luna",
  "especie": "PERRO",
  "raza": "Mestiza",
  "sexo": "HEMBRA",
  "fechaNacimiento": "2022-04-15",
  "pesoActualKg": 7.5,
  "duenoId": 1
}
```

Especies: `PERRO`, `GATO`, `CONEJO`, `AVE`, `OTRA`. Sexo: `MACHO`, `HEMBRA`. Fecha de nacimiento es opcional (aproximada). Puedes actualizar el peso con PUT a la misma ruta, enviando el objeto completo.

Búsqueda personalizada: `GET /api/mascotas/por-dni/74859632`.

## 3. Crear veterinario

`POST /api/veterinarios`

```json
{
  "numeroColegiatura": "CMVP-10001",
  "nombresCompletos": "Laura Mendoza Salas",
  "especialidad": "Medicina general",
  "activo": true
}
```

La colegiatura debe ser única. `activo=false` impide asignar **nuevas** citas.

## 4. Crear cita

`POST /api/citas`

```json
{
  "mascotaId": 1,
  "veterinarioId": 1,
  "fechaHora": "2027-12-20T10:30:00",
  "motivo": "Vacunación anual"
}
```

La fecha/hora debe estar en el futuro respecto al reloj del servidor. El estado inicial se establece automáticamente en `PROGRAMADA`. No se manda costo ni diagnóstico al crear.

## 5. Registrar atención

`PUT /api/citas/1`

```json
{
  "mascotaId": 1,
  "veterinarioId": 1,
  "fechaHora": "2027-12-20T10:30:00",
  "motivo": "Vacunación anual",
  "estado": "ATENDIDA",
  "diagnostico": "Mascota en buen estado de salud",
  "costoSoles": 45.00
}
```

Para cancelar, usa `estado: "CANCELADA"` y omite `diagnostico` y `costoSoles`. Para reprogramar, usa `estado: "PROGRAMADA"` y una fecha futura, siempre que la cita no esté finalizada.

## 6. Obtener / listar / actualizar / eliminar

- Dueños: `GET /api/duenos`, `GET /api/duenos/1`, `PUT /api/duenos/1`, `DELETE /api/duenos/1`.
- Mascotas: `GET /api/mascotas`, `GET /api/mascotas/1`, `PUT /api/mascotas/1`, `DELETE /api/mascotas/1`.
- Veterinarios: `GET /api/veterinarios`, `GET /api/veterinarios/1`, `PUT /api/veterinarios/1`, `DELETE /api/veterinarios/1`.
- Citas: `GET /api/citas`, `GET /api/citas/1`, `PUT /api/citas/1`, `DELETE /api/citas/1`.

Cada DELETE exitoso devuelve `204 No Content`. Cualquier intento de borrar un padre relacionado devuelve `409 Conflict`.

## Evidencias sugeridas para el informe

1. Árbol de carpetas en IntelliJ con las capas `model`, `repository`, `service`, `controller`.
2. Ejecutar los POST de la colección Postman (3 dueños, 5 mascotas, 2 veterinarios y 4 citas).
3. Captura de las tablas en la consola H2 (`SELECT * FROM DUENOS`, etc.).
4. Probar `GET /api/mascotas/por-dni/{dni}` y `GET /api/citas/por-veterinario/{id}`.
5. Probar caso inválido con costo negativo en la actualización de una cita `ATENDIDA` y DNI duplicado: deben fallar con error controlado.
