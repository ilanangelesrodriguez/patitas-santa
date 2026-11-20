package org.uns.patitassanta.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.uns.patitassanta.dto.*;
import org.uns.patitassanta.service.DuenoService;
import java.util.List;

@RestController
@RequestMapping("/api/duenos")
@RequiredArgsConstructor
public class DuenoController {
    private final DuenoService service;

    @GetMapping
    public List<DuenoResponse> listar() { return service.listar(); }

    @GetMapping("/{id}")
    public DuenoResponse obtener(@PathVariable Long id) { return service.obtener(id); }

    @GetMapping("/dni/{dni}")
    public DuenoResponse porDni(@PathVariable String dni) { return service.porDni(dni); }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public DuenoResponse crear(@Valid @RequestBody DuenoRequest request) { return service.crear(request); }

    @PutMapping("/{id}")
    public DuenoResponse actualizar(@PathVariable Long id, @Valid @RequestBody DuenoRequest request) {
        return service.actualizar(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void eliminar(@PathVariable Long id) { service.eliminar(id); }
}
