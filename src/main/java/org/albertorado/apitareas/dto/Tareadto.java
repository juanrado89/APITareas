package org.albertorado.apitareas.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Tareadto {

    private int id;
    private String titulo;
    private String descripcion;
    private Estadodto estado;

}
