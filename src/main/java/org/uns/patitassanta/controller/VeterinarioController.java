package org.uns.patitassanta.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.uns.patitassanta.dto.*;
import org.uns.patitassanta.service.VeterinarioService;
import java.util.List;

@RestController
@RequestMapping("/api/veterinarios")
@RequiredArgsConstructor
@Tag(name = "Veterinarios", description = "CRUD de veterinarios, especialidades y disponibilidad.")
public class VeterinarioController {
    private final VeterinarioService service;

    @Operation(summary = "Listar veterinarios", description = "Obtiene los veterinarios registrados y su estado de actividad.")
    @GetMapping
    public List<VeterinarioResponse> listar() { return service.listar(); }

    @Operation(summary = "Obtener veterinario por ID", description = "Consulta los datos de un veterinario.")
    @GetMapping("/{id}")
    public VeterinarioResponse obtener(@PathVariable Long id) { return service.obtener(id); }

    @Operation(summary = "Registrar veterinario", description = "Registra un veterinario con número de colegiatura único.")
    @ApiResponse(responseCode = "201", description = "Recurso creado correctamente")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public VeterinarioResponse crear(@Valid @RequestBody VeterinarioRequest request) { return service.crear(request); }

    @Operation(summary = "Actualizar veterinario", description = "Actualiza datos, especialidad o estado de actividad.")
    @PutMapping("/{id}")
    public VeterinarioResponse actualizar(@PathVariable Long id, @Valid @RequestBody VeterinarioRequest request) {
        return service.actualizar(id, request);
    }

    @Operation(summary = "Eliminar veterinario", description = "Elimina un veterinario únicamente cuando no tiene citas asociadas.")
    @ApiResponse(responseCode = "204", description = "Recurso eliminado correctamente")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void eliminar(@PathVariable Long id) { service.eliminar(id); }
}
