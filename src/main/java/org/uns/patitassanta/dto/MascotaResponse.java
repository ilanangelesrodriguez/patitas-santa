package org.uns.patitassanta.dto;

import org.uns.patitassanta.model.*;
import java.math.BigDecimal;
import java.time.LocalDate;

public record MascotaResponse(Long id, String nombre, Especie especie, String raza,
                              SexoMascota sexo, LocalDate fechaNacimiento,
                              BigDecimal pesoActualKg, Long duenoId, String duenoDni) {
    public static MascotaResponse from(Mascota m) {
        return new MascotaResponse(m.getId(), m.getNombre(), m.getEspecie(), m.getRaza(),
                m.getSexo(), m.getFechaNacimiento(), m.getPesoActualKg(),
                m.getDueno().getId(), m.getDueno().getDni());
    }
}
