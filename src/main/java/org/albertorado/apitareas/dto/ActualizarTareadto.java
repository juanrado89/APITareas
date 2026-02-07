package org.albertorado.apitareas.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ActualizarTareadto {
    private int id;
    private String titulo;
    private String descripcion;
    private String estadoKey;
}
