package org.uns.patitassanta.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.uns.patitassanta.dto.*;
import org.uns.patitassanta.exception.ReglaNegocioException;
import org.uns.patitassanta.model.Veterinario;
import org.uns.patitassanta.repository.*;
import java.util.List;

@Service @RequiredArgsConstructor
public class VeterinarioService {
    private final VeterinarioRepository veterinarios;
    private final CitaRepository citas;

    @Transactional(readOnly = true)
    public List<VeterinarioResponse> listar() {
        return veterinarios.findAll().stream().map(VeterinarioResponse::from).toList();
    }
    @Transactional(readOnly = true)
    public VeterinarioResponse obtener(Long id) { return VeterinarioResponse.from(buscar(id)); }
    @Transactional
    public VeterinarioResponse crear(VeterinarioRequest r) {
        if (veterinarios.existsByNumeroColegiatura(r.numeroColegiatura()))
            throw new ReglaNegocioException(HttpStatus.CONFLICT, "La colegiatura ya existe");
        Veterinario v = new Veterinario();
        rellenar(v, r);
        return VeterinarioResponse.from(veterinarios.save(v));
    }
    @Transactional
    public VeterinarioResponse actualizar(Long id, VeterinarioRequest r) {
        Veterinario v = buscar(id);
        if (veterinarios.existsByNumeroColegiaturaAndIdNot(r.numeroColegiatura(), id))
            throw new ReglaNegocioException(HttpStatus.CONFLICT, "La colegiatura pertenece a otro veterinario");
        rellenar(v, r);
        return VeterinarioResponse.from(veterinarios.save(v));
    }
    @Transactional
    public void eliminar(Long id) {
        Veterinario v = buscar(id);
        if (citas.existsByVeterinarioId(id))
            throw new ReglaNegocioException(HttpStatus.CONFLICT, "No se puede eliminar: tiene citas registradas");
        veterinarios.delete(v);
    }
    public Veterinario buscar(Long id) {
        return veterinarios.findById(id).orElseThrow(() ->
                new ReglaNegocioException(HttpStatus.NOT_FOUND, "Veterinario no encontrado: " + id));
    }
    private void rellenar(Veterinario v, VeterinarioRequest r) {
        v.setNumeroColegiatura(r.numeroColegiatura()); v.setNombresCompletos(r.nombresCompletos());
        v.setEspecialidad(r.especialidad()); v.setActivo(r.activo());
    }
}
