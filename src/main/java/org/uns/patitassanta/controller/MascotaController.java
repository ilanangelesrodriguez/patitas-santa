package org.uns.patitassanta.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.uns.patitassanta.dto.*;
import org.uns.patitassanta.service.MascotaService;
import java.util.List;

@RestController
@RequestMapping("/api/mascotas")
@RequiredArgsConstructor
@Tag(name = "Mascotas", description = "CRUD de mascotas y consulta por DNI del dueño.")
public class MascotaController {
    private final MascotaService service;

    @Operation(summary = "Listar mascotas", description = "Obtiene todas las mascotas con la referencia de su dueño.")
    @GetMapping
    public List<MascotaResponse> listar() { return service.listar(); }

    @Operation(summary = "Obtener mascota por ID", description = "Consulta una mascota por su identificador.")
    @GetMapping("/{id}")
    public MascotaResponse obtener(@PathVariable Long id) { return service.obtener(id); }

    @Operation(summary = "Buscar mascotas por DNI del dueño", description = "Lista las mascotas asociadas al dueño identificado por DNI.")
    @GetMapping("/por-dni/{dni}")
    public List<MascotaResponse> porDni(@PathVariable String dni) { return service.porDni(dni); }

    @Operation(summary = "Registrar mascota", description = "Registra una mascota asociada obligatoriamente a un dueño existente.")
    @ApiResponse(responseCode = "201", description = "Recurso creado correctamente")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public MascotaResponse crear(@Valid @RequestBody MascotaRequest request) { return service.crear(request); }

    @Operation(summary = "Actualizar mascota", description = "Modifica la información y peso de una mascota.")
    @PutMapping("/{id}")
    public MascotaResponse actualizar(@PathVariable Long id, @Valid @RequestBody MascotaRequest request) {
        return service.actualizar(id, request);
    }

    @Operation(summary = "Eliminar mascota", description = "Elimina una mascota únicamente cuando no tiene citas asociadas.")
    @ApiResponse(responseCode = "204", description = "Recurso eliminado correctamente")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void eliminar(@PathVariable Long id) { service.eliminar(id); }
}
