package org.uns.patitassanta.dto;

import jakarta.validation.constraints.*;
import io.swagger.v3.oas.annotations.media.Schema;
import org.uns.patitassanta.model.EstadoCita;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Schema(description = "Datos para actualizar o finalizar una cita veterinaria")
public record CitaUpdateRequest(
    @Schema(description = "Identificador de la mascota", example = "1")
    @NotNull Long mascotaId,
    @Schema(description = "Identificador del veterinario", example = "1")
    @NotNull Long veterinarioId,
    @Schema(description = "Fecha y hora de la cita en formato ISO-8601", example = "2027-06-20T10:30:00")
    @NotNull LocalDateTime fechaHora,
    @Schema(description = "Motivo de la consulta veterinaria", example = "Control general")
    @NotBlank @Size(max = 250) String motivo,
    @Schema(description = "Estado de la cita", example = "ATENDIDA")
    @NotNull EstadoCita estado,
    @Schema(description = "Diagnóstico breve, obligatorio al atender", example = "Paciente en buen estado general")
    @Size(max = 500) String diagnostico,
    @Schema(description = "Costo de consulta en soles, no negativo", example = "50.00")
    @DecimalMin("0.0") @Digits(integer = 8, fraction = 2) BigDecimal costoSoles
) {}
