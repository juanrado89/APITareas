package org.albertorado.apitareas.service;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.albertorado.apitareas.dto.ActualizarTareadto;
import org.albertorado.apitareas.dto.CrearTareadto;
import org.albertorado.apitareas.dto.Estadodto;
import org.albertorado.apitareas.dto.Tareadto;
import org.albertorado.apitareas.entity.Estado;
import org.albertorado.apitareas.entity.Tarea;
import org.albertorado.apitareas.mapper.TareaMapper;
import org.albertorado.apitareas.repository.EstadoRepository;
import org.albertorado.apitareas.repository.TareaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class TareaService {
    private final TareaRepository tareaRepository;
    private final TareaMapper tareaMapper;
    private final EstadoRepository estadoRepository;

    public TareaService(TareaRepository tareaRepository, TareaMapper tareaMapper, EstadoRepository estadoRepository) {
        this.tareaRepository = tareaRepository;
        this.tareaMapper = tareaMapper;
        this.estadoRepository = estadoRepository;
    }

    public Tareadto crearTarea(CrearTareadto tarea){
        String key = tarea.getEstadoKey().trim().toUpperCase();
        Estado estado = estadoRepository.findByKey(key)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Estado inválido: " + key));
        Tarea crear = new Tarea();
        crear.setTitulo(tarea.getTitulo());
        crear.setDescripcion(tarea.getDescripcion());
        crear.setEstado(estado);
        return tareaMapper.toDto(tareaRepository.save(crear));
    }

    public List<Tareadto> listarTareas(){
        List<Tarea> tareas = tareaRepository.findAll();
        return tareaMapper.toDto(tareas);
    }

    @Transactional
    public Tareadto actualizarTarea(ActualizarTareadto dto) {

        Tarea tareaActualizar = tareaRepository.findById(dto.getId())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Tarea no encontrada: " + dto.getId()));
        if (dto.getTitulo() != null && !dto.getTitulo().isBlank()) {
            tareaActualizar.setTitulo(dto.getTitulo());
        }
        if (dto.getDescripcion() != null) {
            tareaActualizar.setDescripcion(dto.getDescripcion());
        }
        if (dto.getEstadoKey() != null) {
            String key = dto.getEstadoKey().trim().toUpperCase();

            Estado estado = estadoRepository.findByKey(key)
                    .orElseThrow(() -> new ResponseStatusException(
                            HttpStatus.BAD_REQUEST, "Estado inválido: " + key));
            tareaActualizar.setEstado(estado);
        }
        return tareaMapper.toDto(tareaRepository.save(tareaActualizar));
    }

    @Transactional
    public void eliminarTarea(int id) {
        if (!tareaRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Tarea no encontrada: " + id);
        }
        tareaRepository.deleteById(id);
    }

    public Tareadto actualizarEstado(int id, @Valid Estadodto estado) {
        Tarea tareaActualizar = tareaRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Tarea no encontrada: " + id));

        if (estado.getKey() != null) {
            String key = estado.getKey().trim().toUpperCase();
            Estado estadoActualizar = estadoRepository.findByKey(key)
                    .orElseThrow(() -> new ResponseStatusException(
                            HttpStatus.BAD_REQUEST, "Estado inválido: " + key));
            tareaActualizar.setEstado(estadoActualizar);
        }
        return tareaMapper.toDto(tareaRepository.save(tareaActualizar));

    }
}
