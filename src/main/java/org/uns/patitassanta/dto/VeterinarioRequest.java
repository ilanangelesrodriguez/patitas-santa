package org.uns.patitassanta.dto;

import jakarta.validation.constraints.*;

public record VeterinarioRequest(
    @NotBlank @Size(max = 30) String numeroColegiatura,
    @NotBlank @Size(max = 120) String nombresCompletos,
    @NotBlank @Size(max = 100) String especialidad,
    @NotNull Boolean activo
) {}
