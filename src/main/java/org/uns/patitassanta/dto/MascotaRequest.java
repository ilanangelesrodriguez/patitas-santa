package org.uns.patitassanta.dto;

import jakarta.validation.constraints.*;
import org.uns.patitassanta.model.Especie;
import org.uns.patitassanta.model.SexoMascota;
import java.math.BigDecimal;
import java.time.LocalDate;

public record MascotaRequest(
    @NotBlank @Size(max = 80) String nombre,
    @NotNull Especie especie,
    @NotBlank @Size(max = 80) String raza,
    @NotNull SexoMascota sexo,
    @PastOrPresent LocalDate fechaNacimiento,
    @NotNull @DecimalMin("0.0") @Digits(integer = 5, fraction = 2) BigDecimal pesoActualKg,
    @NotNull Long duenoId
) {}
