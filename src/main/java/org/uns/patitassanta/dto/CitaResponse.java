package org.uns.patitassanta.dto;

import org.uns.patitassanta.model.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public record CitaResponse(Long id, Long mascotaId, String mascotaNombre,
                           Long veterinarioId, String veterinarioNombre,
                           LocalDateTime fechaHora, String motivo, EstadoCita estado,
                           String diagnostico, BigDecimal costoSoles) {
    public static CitaResponse from(Cita c) {
        return new CitaResponse(c.getId(), c.getMascota().getId(), c.getMascota().getNombre(),
                c.getVeterinario().getId(), c.getVeterinario().getNombresCompletos(),
                c.getFechaHora(), c.getMotivo(), c.getEstado(),
                c.getDiagnostico(), c.getCostoSoles());
    }
}
