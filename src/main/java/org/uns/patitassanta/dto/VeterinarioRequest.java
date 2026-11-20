package org.uns.patitassanta.dto;

import jakarta.validation.constraints.*;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Solicitud para veterinario de Patitas del Santa")
public record VeterinarioRequest(
    @Schema(description = "Número de colegiatura único", example = "CMVP-12345")
    @NotBlank @Size(max = 30) String numeroColegiatura,
    @Schema(description = "Nombres y apellidos del veterinario", example = "María Torres Flores")
    @NotBlank @Size(max = 120) String nombresCompletos,
    @Schema(description = "Especialidad médica", example = "Medicina general")
    @NotBlank @Size(max = 100) String especialidad,
    @Schema(description = "Indica si se encuentra activo", example = "true")
    @NotNull Boolean activo
) {}
