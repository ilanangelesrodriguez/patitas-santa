package org.uns.patitassanta.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.uns.patitassanta.dto.*;
import org.uns.patitassanta.exception.ReglaNegocioException;
import org.uns.patitassanta.model.Mascota;
import org.uns.patitassanta.repository.*;
import java.util.List;

@Service @RequiredArgsConstructor
public class MascotaService {
    private final MascotaRepository mascotas;
    private final DuenoService duenos;
    private final CitaRepository citas;

    @Transactional(readOnly = true)
    public List<MascotaResponse> listar() { return mascotas.findAll().stream().map(MascotaResponse::from).toList(); }
    @Transactional(readOnly = true)
    public MascotaResponse obtener(Long id) { return MascotaResponse.from(buscar(id)); }
    @Transactional(readOnly = true)
    public List<MascotaResponse> porDni(String dni) {
        duenos.porDni(dni); // Se distingue DNI inexistente de dueño sin mascotas.
        return mascotas.findByDuenoDniOrderByIdAsc(dni).stream().map(MascotaResponse::from).toList();
    }

    @Transactional
    public MascotaResponse crear(MascotaRequest r) {
        Mascota m = new Mascota();
        rellenar(m, r);
        return MascotaResponse.from(mascotas.save(m));
    }
    @Transactional
    public MascotaResponse actualizar(Long id, MascotaRequest r) {
        Mascota m = buscar(id);
        rellenar(m, r);
        return MascotaResponse.from(mascotas.save(m));
    }
    @Transactional
    public void eliminar(Long id) {
        Mascota m = buscar(id);
        if (citas.existsByMascotaId(id))
            throw new ReglaNegocioException(HttpStatus.CONFLICT, "No se puede eliminar: la mascota tiene citas");
        mascotas.delete(m);
    }
    public Mascota buscar(Long id) {
        return mascotas.findById(id).orElseThrow(() ->
                new ReglaNegocioException(HttpStatus.NOT_FOUND, "Mascota no encontrada: " + id));
    }
    private void rellenar(Mascota m, MascotaRequest r) {
        m.setNombre(r.nombre()); m.setEspecie(r.especie()); m.setRaza(r.raza());
        m.setSexo(r.sexo()); m.setFechaNacimiento(r.fechaNacimiento());
        m.setPesoActualKg(r.pesoActualKg()); m.setDueno(duenos.buscar(r.duenoId()));
    }
}
