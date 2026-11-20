# Arquitectura de software — Semana 10

La petición HTTP atraviesa las **tres capas** exigidas en el caso:

```text
Cliente REST (Postman)
        │ JSON / HTTP
        ▼
┌─────────────────────────┐
│ controller              │  CRUD, endpoints y validación de entrada (@Valid)
└───────────┬─────────────┘
            ▼
┌─────────────────────────┐
│ service                 │  Reglas de negocio y transacciones (@Transactional)
└───────────┬─────────────┘
            ▼
┌─────────────────────────┐
│ repository              │  Interfaces que extienden JpaRepository
└───────────┬─────────────┘
            ▼
          JPA
            │
┌───────────▼─────────────┐
│ H2 (defecto) / Neon PG  │
└─────────────────────────┘
```

`model/` es transversal: entidades persistentes utilizadas en `repository` y `service`. `dto/` contiene la estructura JSON pública y `exception/` mantiene los errores consistentes.

## Diagrama ER simplificado

```text
DUENO (id PK, dni UNIQUE, nombres, apellidos, celular, correo, direccion)
  1 ─────────── N MASCOTA (id PK, dueno_id FK, nombre, especie, raza,
                         sexo, fecha_nacimiento, peso_actual_kg)
                       1
                       │
                       N
                     CITA (id PK, mascota_id FK, veterinario_id FK,
                           fecha_hora, motivo, estado, diagnostico, costo_soles)
                       N
                       │
                       1
VETERINARIO (id PK, numero_colegiatura UNIQUE, nombres_completos,
             especialidad, activo)
```

Se utilizan claves foráneas, unicidades, validaciones Jakarta, y rechazo de eliminación del padre cuando existen referencias.
