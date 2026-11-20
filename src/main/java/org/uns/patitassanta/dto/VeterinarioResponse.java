package org.uns.patitassanta.dto;

import org.uns.patitassanta.model.Veterinario;

public record VeterinarioResponse(Long id, String numeroColegiatura, String nombresCompletos,
                                  String especialidad, boolean activo) {
    public static VeterinarioResponse from(Veterinario v) {
        return new VeterinarioResponse(v.getId(), v.getNumeroColegiatura(),
                v.getNombresCompletos(), v.getEspecialidad(), v.isActivo());
    }
}
