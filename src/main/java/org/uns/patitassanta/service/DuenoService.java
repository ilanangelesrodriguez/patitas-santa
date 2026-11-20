package org.uns.patitassanta.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.uns.patitassanta.dto.*;
import org.uns.patitassanta.exception.ReglaNegocioException;
import org.uns.patitassanta.model.Dueno;
import org.uns.patitassanta.repository.*;
import java.util.List;

@Service @RequiredArgsConstructor
public class DuenoService {
    private final DuenoRepository duenos;
    private final MascotaRepository mascotas;

    @Transactional(readOnly = true)
    public List<DuenoResponse> listar() { return duenos.findAll().stream().map(DuenoResponse::from).toList(); }

    @Transactional(readOnly = true)
    public DuenoResponse obtener(Long id) { return DuenoResponse.from(buscar(id)); }

    @Transactional(readOnly = true)
    public DuenoResponse porDni(String dni) {
        return DuenoResponse.from(duenos.findByDni(dni).orElseThrow(() ->
                new ReglaNegocioException(HttpStatus.NOT_FOUND, "Dueño no encontrado con DNI " + dni)));
    }

    @Transactional
    public DuenoResponse crear(DuenoRequest r) {
        if (duenos.existsByDni(r.dni()))
            throw new ReglaNegocioException(HttpStatus.CONFLICT, "El DNI ya está registrado");
        Dueno d = new Dueno();
        rellenar(d, r);
        return DuenoResponse.from(duenos.save(d));
    }

    @Transactional
    public DuenoResponse actualizar(Long id, DuenoRequest r) {
        Dueno d = buscar(id);
        if (duenos.existsByDniAndIdNot(r.dni(), id))
            throw new ReglaNegocioException(HttpStatus.CONFLICT, "El DNI ya pertenece a otro dueño");
        rellenar(d, r);
        return DuenoResponse.from(duenos.save(d));
    }

    @Transactional
    public void eliminar(Long id) {
        Dueno d = buscar(id);
        if (mascotas.existsByDuenoId(id))
            throw new ReglaNegocioException(HttpStatus.CONFLICT, "No se puede eliminar: el dueño tiene mascotas");
        duenos.delete(d);
    }

    public Dueno buscar(Long id) {
        return duenos.findById(id).orElseThrow(() ->
                new ReglaNegocioException(HttpStatus.NOT_FOUND, "Dueño no encontrado: " + id));
    }

    private void rellenar(Dueno d, DuenoRequest r) {
        d.setDni(r.dni()); d.setNombres(r.nombres()); d.setApellidos(r.apellidos());
        d.setCelular(r.celular()); d.setCorreo(r.correo()); d.setDireccion(r.direccion());
    }
}
