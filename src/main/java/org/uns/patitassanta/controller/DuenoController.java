package org.uns.patitassanta.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.uns.patitassanta.dto.*;
import org.uns.patitassanta.service.DuenoService;
import java.util.List;

@RestController
@RequestMapping("/api/duenos")
@RequiredArgsConstructor
@Tag(name = "Dueños", description = "CRUD de dueños y búsqueda por DNI.")
public class DuenoController {
    private final DuenoService service;

    @Operation(summary = "Listar dueños", description = "Obtiene todos los dueños registrados.")
    @GetMapping
    public List<DuenoResponse> listar() { return service.listar(); }

    @Operation(summary = "Obtener dueño por ID", description = "Consulta un dueño existente por su identificador.")
    @GetMapping("/{id}")
    public DuenoResponse obtener(@PathVariable Long id) { return service.obtener(id); }

    @Operation(summary = "Buscar dueño por DNI", description = "Busca un dueño mediante su DNI único de ocho dígitos.")
    @GetMapping("/dni/{dni}")
    public DuenoResponse porDni(@PathVariable String dni) { return service.porDni(dni); }

    @Operation(summary = "Registrar dueño", description = "Registra un nuevo dueño. El DNI debe ser único.")
    @ApiResponse(responseCode = "201", description = "Recurso creado correctamente")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public DuenoResponse crear(@Valid @RequestBody DuenoRequest request) { return service.crear(request); }

    @Operation(summary = "Actualizar dueño", description = "Modifica los datos de contacto y personales de un dueño.")
    @PutMapping("/{id}")
    public DuenoResponse actualizar(@PathVariable Long id, @Valid @RequestBody DuenoRequest request) {
        return service.actualizar(id, request);
    }

    @Operation(summary = "Eliminar dueño", description = "Elimina un dueño únicamente cuando no tiene mascotas asociadas.")
    @ApiResponse(responseCode = "204", description = "Recurso eliminado correctamente")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void eliminar(@PathVariable Long id) { service.eliminar(id); }
}
