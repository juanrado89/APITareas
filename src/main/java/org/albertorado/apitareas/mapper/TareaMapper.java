package org.albertorado.apitareas.mapper;

import org.albertorado.apitareas.dto.Tareadto;
import org.albertorado.apitareas.entity.Tarea;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring", uses = {EstadoMapper.class})
public interface TareaMapper {

    Tareadto toDto(Tarea tarea);
    Tarea toEntity(Tareadto dto);
    List<Tareadto> toDto(List<Tarea> resultado);
    List<Tarea> toEntity(List<Tareadto> resultado);

}
