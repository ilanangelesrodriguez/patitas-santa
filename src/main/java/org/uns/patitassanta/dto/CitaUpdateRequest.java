package org.uns.patitassanta.dto;

import jakarta.validation.constraints.*;
import org.uns.patitassanta.model.EstadoCita;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public record CitaUpdateRequest(
    @NotNull Long mascotaId,
    @NotNull Long veterinarioId,
    @NotNull LocalDateTime fechaHora,
    @NotBlank @Size(max = 250) String motivo,
    @NotNull EstadoCita estado,
    @Size(max = 500) String diagnostico,
    @DecimalMin("0.0") @Digits(integer = 8, fraction = 2) BigDecimal costoSoles
) {}
