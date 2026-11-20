package org.uns.patitassanta.dto;

import jakarta.validation.constraints.*;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;

// Crear una cita: siempre empieza en PROGRAMADA; no se permite fecha pasada.
@Schema(description = "Datos para programar una nueva cita veterinaria")
public record CitaRequest(
    @Schema(description = "Identificador de la mascota", example = "1")
    @NotNull Long mascotaId,
    @Schema(description = "Identificador del veterinario activo", example = "1")
    @NotNull Long veterinarioId,
    @Schema(description = "Fecha y hora FUTURAS en formato ISO-8601", example = "2027-06-20T10:30:00")
    @NotNull @Future LocalDateTime fechaHora,
    @Schema(description = "Motivo de la consulta veterinaria", example = "Vacunación anual")
    @NotBlank @Size(max = 250) String motivo
) {}
