package org.uns.patitassanta.dto;

import jakarta.validation.constraints.*;
import java.time.LocalDateTime;

// Crear una cita: siempre empieza en PROGRAMADA; no se permite fecha pasada.
public record CitaRequest(
    @NotNull Long mascotaId,
    @NotNull Long veterinarioId,
    @NotNull @Future LocalDateTime fechaHora,
    @NotBlank @Size(max = 250) String motivo
) {}
