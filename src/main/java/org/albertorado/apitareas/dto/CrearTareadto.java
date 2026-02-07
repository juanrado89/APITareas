package org.albertorado.apitareas.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CrearTareadto {

    @NotBlank(message = "El título es obligatorio")
    @Size(max = 255, message = "El título no puede superar 255 caracteres")
    private String titulo;

    @Size(max = 3000, message = "La descripción no puede superar 3000 caracteres")
    private String descripcion;

    @NotBlank(message = "El estadoKey es obligatorio")
    private String estadoKey;
}

