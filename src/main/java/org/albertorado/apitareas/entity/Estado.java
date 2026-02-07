package org.albertorado.apitareas.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Estado {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_estado",nullable = false)
    private int id;

    @NotNull
    @Size(min = 0,max = 20)
    @Basic
    @Column(name = "key", nullable = false, unique = true)
    private String key;

    @NotNull
    @Size(min = 0,max = 20)
    @Basic
    @Column(name = "estado",length = 20,nullable = false)
    private String estado;

    @OneToMany(mappedBy = "estado")
    private List<Tarea> tareas;
}
