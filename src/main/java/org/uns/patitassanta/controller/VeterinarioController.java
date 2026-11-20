package org.uns.patitassanta.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.uns.patitassanta.dto.*;
import org.uns.patitassanta.service.VeterinarioService;
import java.util.List;

@RestController
@RequestMapping("/api/veterinarios")
@RequiredArgsConstructor
public class VeterinarioController {
    private final VeterinarioService service;

    @GetMapping
    public List<VeterinarioResponse> listar() { return service.listar(); }

    @GetMapping("/{id}")
    public VeterinarioResponse obtener(@PathVariable Long id) { return service.obtener(id); }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public VeterinarioResponse crear(@Valid @RequestBody VeterinarioRequest request) { return service.crear(request); }

    @PutMapping("/{id}")
    public VeterinarioResponse actualizar(@PathVariable Long id, @Valid @RequestBody VeterinarioRequest request) {
        return service.actualizar(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void eliminar(@PathVariable Long id) { service.eliminar(id); }
}
