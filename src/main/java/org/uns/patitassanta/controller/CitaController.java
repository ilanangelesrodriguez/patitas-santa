package org.uns.patitassanta.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.uns.patitassanta.dto.*;
import org.uns.patitassanta.service.CitaService;
import java.util.List;

@RestController
@RequestMapping("/api/citas")
@RequiredArgsConstructor
public class CitaController {
    private final CitaService service;

    @GetMapping
    public List<CitaResponse> listar() { return service.listar(); }
    @GetMapping("/{id}")
    public CitaResponse obtener(@PathVariable Long id) { return service.obtener(id); }
    @GetMapping("/por-veterinario/{id}")
    public List<CitaResponse> porVeterinario(@PathVariable Long id) { return service.porVeterinario(id); }
    @GetMapping("/por-mascota/{id}")
    public List<CitaResponse> porMascota(@PathVariable Long id) { return service.porMascota(id); }
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CitaResponse crear(@Valid @RequestBody CitaRequest request) { return service.crear(request); }
    @PutMapping("/{id}")
    public CitaResponse actualizar(@PathVariable Long id, @Valid @RequestBody CitaUpdateRequest request) {
        return service.actualizar(id, request);
    }
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void eliminar(@PathVariable Long id) { service.eliminar(id); }
}
