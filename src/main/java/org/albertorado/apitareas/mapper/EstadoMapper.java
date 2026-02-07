package org.albertorado.apitareas.mapper;

import org.albertorado.apitareas.dto.Estadodto;
import org.albertorado.apitareas.entity.Estado;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface EstadoMapper {

    Estadodto toDto(Estado estado);
    Estado toEntity(Estadodto estadoDto);

}
