# API REST con Spring Boot

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
