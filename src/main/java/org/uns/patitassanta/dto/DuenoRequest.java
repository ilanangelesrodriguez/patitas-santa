package org.uns.patitassanta.dto;

import jakarta.validation.constraints.*;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Solicitud para dueno de Patitas del Santa")
public record DuenoRequest(
    @Schema(description = "DNI único de ocho dígitos", example = "70801234")
    @NotBlank @Pattern(regexp = "\\d{8}", message = "El DNI debe tener 8 dígitos") String dni,
    @Schema(description = "Nombres del dueño", example = "Rosa")
    @NotBlank @Size(max = 80) String nombres,
    @Schema(description = "Apellidos del dueño", example = "Medina")
    @NotBlank @Size(max = 100) String apellidos,
    @Schema(description = "Número de celular", example = "987654321")
    @NotBlank @Size(max = 20) String celular,
    @Schema(description = "Correo electrónico", example = "rosa@example.com")
    @Email @Size(max = 120) String correo,
    @Schema(description = "Domicilio del dueño", example = "Nuevo Chimbote, Áncash")
    @NotBlank @Size(max = 200) String direccion
) {}
