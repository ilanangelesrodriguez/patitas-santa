package org.uns.patitassanta.dto;

import jakarta.validation.constraints.*;

public record DuenoRequest(
    @NotBlank @Pattern(regexp = "\\d{8}", message = "El DNI debe tener 8 dígitos") String dni,
    @NotBlank @Size(max = 80) String nombres,
    @NotBlank @Size(max = 100) String apellidos,
    @NotBlank @Size(max = 20) String celular,
    @Email @Size(max = 120) String correo,
    @NotBlank @Size(max = 200) String direccion
) {}
