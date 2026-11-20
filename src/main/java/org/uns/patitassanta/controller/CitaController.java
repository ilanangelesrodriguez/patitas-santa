package org.uns.patitassanta.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.uns.patitassanta.dto.*;
import org.uns.patitassanta.service.CitaService;
import java.util.List;

@RestController
@RequestMapping("/api/citas")
@RequiredArgsConstructor
@Tag(name = "Citas", description = "Programación, consulta, actualización y eliminación de citas.")
public class CitaController {
    private final CitaService service;

    @Operation(summary = "Listar citas", description = "Consulta las citas veterinarias y sus estados.")
    @GetMapping
    public List<CitaResponse> listar() { return service.listar(); }
    @Operation(summary = "Obtener cita por ID", description = "Obtiene el detalle de una cita.")
    @GetMapping("/{id}")
    public CitaResponse obtener(@PathVariable Long id) { return service.obtener(id); }
    @Operation(summary = "Buscar citas por veterinario", description = "Lista las citas asociadas a un veterinario.")
    @GetMapping("/por-veterinario/{id}")
    public List<CitaResponse> porVeterinario(@PathVariable Long id) { return service.porVeterinario(id); }
    @Operation(summary = "Buscar citas por mascota", description = "Lista el historial de citas de una mascota.")
    @GetMapping("/por-mascota/{id}")
    public List<CitaResponse> porMascota(@PathVariable Long id) { return service.porMascota(id); }
    @Operation(summary = "Programar cita", description = "Registra una cita futura en estado PROGRAMADA para una mascota y un veterinario activo.")
    @ApiResponse(responseCode = "201", description = "Recurso creado correctamente")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CitaResponse crear(@Valid @RequestBody CitaRequest request) { return service.crear(request); }
    @Operation(summary = "Actualizar o finalizar cita", description = "Actualiza una cita y permite registrar atención, diagnóstico, costo o cancelación.")
    @PutMapping("/{id}")
    public CitaResponse actualizar(@PathVariable Long id, @Valid @RequestBody CitaUpdateRequest request) {
        return service.actualizar(id, request);
    }
    @Operation(summary = "Eliminar cita", description = "Elimina una cita por su identificador.")
    @ApiResponse(responseCode = "204", description = "Recurso eliminado correctamente")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void eliminar(@PathVariable Long id) { service.eliminar(id); }
}
