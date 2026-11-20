package org.uns.patitassanta.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.uns.patitassanta.dto.*;
import org.uns.patitassanta.exception.ReglaNegocioException;
import org.uns.patitassanta.model.*;
import org.uns.patitassanta.repository.CitaRepository;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service @RequiredArgsConstructor
public class CitaService {
    private final CitaRepository citas;
    private final MascotaService mascotas;
    private final VeterinarioService veterinarios;

    @Transactional(readOnly = true)
    public List<CitaResponse> listar() { return citas.findAll().stream().map(CitaResponse::from).toList(); }
    @Transactional(readOnly = true)
    public CitaResponse obtener(Long id) { return CitaResponse.from(buscar(id)); }
    @Transactional(readOnly = true)
    public List<CitaResponse> porVeterinario(Long id) {
        veterinarios.buscar(id);
        return citas.findByVeterinarioIdOrderByFechaHoraAsc(id).stream().map(CitaResponse::from).toList();
    }
    @Transactional(readOnly = true)
    public List<CitaResponse> porMascota(Long id) {
        mascotas.buscar(id);
        return citas.findByMascotaIdOrderByFechaHoraAsc(id).stream().map(CitaResponse::from).toList();
    }

    @Transactional
    public CitaResponse crear(CitaRequest r) {
        Mascota mascota = mascotas.buscar(r.mascotaId());
        Veterinario vet = veterinarios.buscar(r.veterinarioId());
        if (!vet.isActivo())
            throw new ReglaNegocioException(HttpStatus.BAD_REQUEST, "No se puede asignar un veterinario inactivo");
        validarDisponibilidad(mascota.getId(), vet.getId(), r.fechaHora(), -1L);
        Cita c = new Cita();
        c.setMascota(mascota); c.setVeterinario(vet); c.setFechaHora(r.fechaHora());
        c.setMotivo(r.motivo()); c.setEstado(EstadoCita.PROGRAMADA);
        return CitaResponse.from(citas.save(c));
    }

    @Transactional
    public CitaResponse actualizar(Long id, CitaUpdateRequest r) {
        Cita c = buscar(id);
        // La historia clínica no puede regresar a PROGRAMADA tras finalizar la cita.
        if (c.getEstado() != EstadoCita.PROGRAMADA && r.estado() != c.getEstado())
            throw new ReglaNegocioException(HttpStatus.CONFLICT, "No se permite cambiar un estado finalizado");
        Mascota mascota = mascotas.buscar(r.mascotaId());
        Veterinario vet = veterinarios.buscar(r.veterinarioId());
        if (r.estado() == EstadoCita.PROGRAMADA) {
            if (!r.fechaHora().isAfter(LocalDateTime.now()))
                throw new ReglaNegocioException(HttpStatus.BAD_REQUEST, "La cita programada debe tener fecha futura");
            if (!vet.isActivo())
                throw new ReglaNegocioException(HttpStatus.BAD_REQUEST, "El veterinario está inactivo");
            if (r.costoSoles() != null || (r.diagnostico() != null && !r.diagnostico().isBlank()))
                throw new ReglaNegocioException(HttpStatus.BAD_REQUEST, "Una cita PROGRAMADA no lleva costo ni diagnóstico");
            validarDisponibilidad(mascota.getId(), vet.getId(), r.fechaHora(), id);
        }
        if (r.estado() == EstadoCita.ATENDIDA &&
                (r.diagnostico() == null || r.diagnostico().isBlank() || r.costoSoles() == null))
            throw new ReglaNegocioException(HttpStatus.BAD_REQUEST, "La cita ATENDIDA requiere diagnóstico y costo");
        if (r.estado() == EstadoCita.CANCELADA &&
                (r.costoSoles() != null || (r.diagnostico() != null && !r.diagnostico().isBlank())))
            throw new ReglaNegocioException(HttpStatus.BAD_REQUEST, "Una cita CANCELADA no lleva costo ni diagnóstico");
        c.setMascota(mascota); c.setVeterinario(vet); c.setFechaHora(r.fechaHora());
        c.setMotivo(r.motivo()); c.setEstado(r.estado());
        c.setDiagnostico(r.estado() == EstadoCita.ATENDIDA ? r.diagnostico() : null);
        c.setCostoSoles(r.estado() == EstadoCita.ATENDIDA ? r.costoSoles() : null);
        return CitaResponse.from(citas.save(c));
    }
    @Transactional
    public void eliminar(Long id) { citas.delete(buscar(id)); }

    private Cita buscar(Long id) {
        return citas.findById(id).orElseThrow(() ->
                new ReglaNegocioException(HttpStatus.NOT_FOUND, "Cita no encontrada: " + id));
    }
    private void validarDisponibilidad(Long mascotaId, Long vetId, LocalDateTime fecha, Long idIgnorado) {
        if (citas.existsByVeterinarioIdAndFechaHoraAndEstadoAndIdNot(
                vetId, fecha, EstadoCita.PROGRAMADA, idIgnorado))
            throw new ReglaNegocioException(HttpStatus.CONFLICT, "El veterinario ya tiene una cita a esa hora");
        if (citas.existsByMascotaIdAndFechaHoraAndEstadoAndIdNot(
                mascotaId, fecha, EstadoCita.PROGRAMADA, idIgnorado))
            throw new ReglaNegocioException(HttpStatus.CONFLICT, "La mascota ya tiene una cita a esa hora");
    }
}
