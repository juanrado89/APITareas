package org.albertorado.apitareas.repository;

import org.albertorado.apitareas.entity.Tarea;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TareaRepository extends JpaRepository<Tarea,Integer> {
    Optional<Tarea> findById(@Param("id") Integer id);
}
