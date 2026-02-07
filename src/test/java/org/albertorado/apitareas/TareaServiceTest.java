package org.albertorado.apitareas;

import org.albertorado.apitareas.dto.ActualizarTareadto;
import org.albertorado.apitareas.dto.Estadodto;
import org.albertorado.apitareas.dto.Tareadto;
import org.albertorado.apitareas.entity.Estado;
import org.albertorado.apitareas.entity.Tarea;
import org.albertorado.apitareas.mapper.TareaMapper;
import org.albertorado.apitareas.repository.EstadoRepository;
import org.albertorado.apitareas.repository.TareaRepository;
import org.albertorado.apitareas.service.TareaService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.Disabled;

@Disabled("Evitar levantar contexto/DB en unit tests")
@SpringBootTest
class TareaServiceTest {

    private TareaRepository tareaRepository;
    private EstadoRepository estadoRepository;
    private TareaMapper tareaMapper;

    private TareaService tareaService;

    @BeforeEach
    void setUp() {
        tareaRepository = mock(TareaRepository.class);
        estadoRepository = mock(EstadoRepository.class);
        tareaMapper = mock(TareaMapper.class);

        // Stub del mapper: convierte la entidad Tarea -> Tareadto de forma mínima pero real
        when(tareaMapper.toDto(any(Tarea.class))).thenAnswer(inv -> {
            Tarea t = inv.getArgument(0);

            Tareadto out = new Tareadto();
            out.setId(t.getId());
            out.setTitulo(t.getTitulo());
            out.setDescripcion(t.getDescripcion());

            if (t.getEstado() != null) {
                Estadodto e = new Estadodto();
                e.setId(t.getEstado().getId());
                e.setKey(t.getEstado().getKey());
                e.setEstado(t.getEstado().getEstado());
                out.setEstado(e);
            }

            return out;
        });

        tareaService = new TareaService(tareaRepository, tareaMapper, estadoRepository);
    }

    @Test
    void actualizarTarea_actualizaEstado_siEstadoKeyExiste() {
        int tareaId = 1;

        Estado pendiente = new Estado();
        pendiente.setId(10);
        pendiente.setKey("PENDIENTE");
        pendiente.setEstado("Pendiente");

        Estado completada = new Estado();
        completada.setId(20);
        completada.setKey("COMPLETADA");
        completada.setEstado("Completada");

        Tarea existente = new Tarea();
        existente.setId(tareaId);
        existente.setTitulo("Comprar pan");
        existente.setDescripcion("Integral");
        existente.setEstado(pendiente);

        ActualizarTareadto dto = new ActualizarTareadto();
        dto.setId(tareaId);
        dto.setEstadoKey("completada");

        when(tareaRepository.findById(tareaId)).thenReturn(Optional.of(existente));
        when(estadoRepository.findByKey("COMPLETADA")).thenReturn(Optional.of(completada));
        when(tareaRepository.save(any(Tarea.class))).thenAnswer(inv -> inv.getArgument(0));

        Tareadto resultado = tareaService.actualizarTarea(dto);

        assertNotNull(resultado);
        assertNotNull(resultado.getEstado());
        assertEquals("COMPLETADA", resultado.getEstado().getKey());
        assertEquals(20, resultado.getEstado().getId());

        verify(tareaRepository).save(existente);
    }

    @Test
    void actualizarTarea_lanza404_siTareaNoExiste() {
        int tareaId = 99;

        ActualizarTareadto dto = new ActualizarTareadto();
        dto.setId(tareaId);
        dto.setEstadoKey("COMPLETADA");

        when(tareaRepository.findById(tareaId)).thenReturn(Optional.empty());

        ResponseStatusException ex = assertThrows(ResponseStatusException.class,
                () -> tareaService.actualizarTarea(dto));

        assertEquals(HttpStatus.NOT_FOUND, ex.getStatusCode());
        assertNotNull(ex.getReason());
        assertTrue(ex.getReason().contains("Tarea no encontrada"));

        verify(tareaRepository, never()).save(any());
    }

    @Test
    void actualizarTarea_lanza400_siEstadoKeyNoExiste() {
        int tareaId = 1;

        Estado pendiente = new Estado();
        pendiente.setId(10);
        pendiente.setKey("PENDIENTE");
        pendiente.setEstado("Pendiente");

        Tarea existente = new Tarea();
        existente.setId(tareaId);
        existente.setTitulo("X");
        existente.setEstado(pendiente);

        ActualizarTareadto dto = new ActualizarTareadto();
        dto.setId(tareaId);
        dto.setEstadoKey("NO_EXISTE");

        when(tareaRepository.findById(tareaId)).thenReturn(Optional.of(existente));
        when(estadoRepository.findByKey("NO_EXISTE")).thenReturn(Optional.empty());

        ResponseStatusException ex = assertThrows(ResponseStatusException.class,
                () -> tareaService.actualizarTarea(dto));

        assertEquals(HttpStatus.BAD_REQUEST, ex.getStatusCode());
        assertNotNull(ex.getReason());
        assertTrue(ex.getReason().contains("Estado inválido"));

        verify(tareaRepository, never()).save(any());
    }
}
