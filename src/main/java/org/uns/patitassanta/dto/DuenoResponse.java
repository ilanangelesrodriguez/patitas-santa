package org.uns.patitassanta.dto;

import org.uns.patitassanta.model.Dueno;

public record DuenoResponse(Long id, String dni, String nombres, String apellidos,
                            String celular, String correo, String direccion) {
    public static DuenoResponse from(Dueno d) {
        return new DuenoResponse(d.getId(), d.getDni(), d.getNombres(), d.getApellidos(),
                d.getCelular(), d.getCorreo(), d.getDireccion());
    }
}
