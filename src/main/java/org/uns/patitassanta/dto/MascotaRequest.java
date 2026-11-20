package org.uns.patitassanta.dto;

import jakarta.validation.constraints.*;
import io.swagger.v3.oas.annotations.media.Schema;
import org.uns.patitassanta.model.Especie;
import org.uns.patitassanta.model.SexoMascota;
import java.math.BigDecimal;
import java.time.LocalDate;

@Schema(description = "Solicitud para mascota de Patitas del Santa")
public record MascotaRequest(
    @Schema(description = "Nombre de la mascota", example = "Luna")
    @NotBlank @Size(max = 80) String nombre,
    @Schema(description = "Especie de la mascota", example = "PERRO")
    @NotNull Especie especie,
    @Schema(description = "Raza de la mascota", example = "Mestizo")
    @NotBlank @Size(max = 80) String raza,
    @Schema(description = "Sexo de la mascota", example = "HEMBRA")
    @NotNull SexoMascota sexo,
    @Schema(description = "Fecha aproximada de nacimiento (AAAA-MM-DD)", example = "2021-04-12")
    @PastOrPresent LocalDate fechaNacimiento,
    @Schema(description = "Peso en kilogramos (no negativo)", example = "8.50")
    @NotNull @DecimalMin("0.0") @Digits(integer = 5, fraction = 2) BigDecimal pesoActualKg,
    @Schema(description = "ID del dueño responsable", example = "1")
    @NotNull Long duenoId
) {}
