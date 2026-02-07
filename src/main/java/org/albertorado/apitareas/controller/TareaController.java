package org.albertorado.apitareas.controller;

import jakarta.validation.Valid;
import org.albertorado.apitareas.dto.ActualizarTareadto;
import org.albertorado.apitareas.dto.CrearTareadto;
import org.albertorado.apitareas.dto.Estadodto;
import org.albertorado.apitareas.dto.Tareadto;
import org.albertorado.apitareas.entity.Estado;
import org.albertorado.apitareas.service.TareaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/tarea")
public class TareaController {

    private final TareaService tareaService;

    public TareaController(TareaService tareaService) {
        this.tareaService = tareaService;
    }

    @GetMapping()
    public List<Tareadto> listar() {
        return tareaService.listarTareas();
    }

    @PostMapping()
    public ResponseEntity<Tareadto> crear(@Valid @RequestBody CrearTareadto dto) {
        Tareadto creada = tareaService.crearTarea(dto);
        return ResponseEntity
                .created(URI.create("/api/tareas/" + creada.getId()))
                .body(creada);
    }

    @PutMapping("/{id}")
    public Tareadto actualizar(@PathVariable int id, @Valid @RequestBody ActualizarTareadto dto) {
        dto.setId(id);
        return tareaService.actualizarTarea(dto);
    }

    @PatchMapping("/{id}/estado")
    public Tareadto actualizarEstado(@PathVariable int id,@Valid @RequestBody Estadodto estado) {
        return tareaService.actualizarEstado(id,estado);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void eliminar(@PathVariable int id) {
        tareaService.eliminarTarea(id);
    }
}
