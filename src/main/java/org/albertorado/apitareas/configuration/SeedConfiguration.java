package org.albertorado.apitareas.configuration;

import org.albertorado.apitareas.entity.Estado;
import org.albertorado.apitareas.repository.EstadoRepository;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SeedConfiguration {

    @Bean
    ApplicationRunner seedEstados(EstadoRepository estadoRepository) {
        return args -> {
            crearSiNoExiste(estadoRepository, "PENDIENTE", "Pendiente");
            crearSiNoExiste(estadoRepository, "COMPLETADA", "Completada");
        };
    }

    private void crearSiNoExiste(EstadoRepository repo, String key, String nombre) {
        repo.findByKey(key).orElseGet(() -> {
            Estado e = new Estado();
            e.setKey(key);
            e.setEstado(nombre); // si tu campo es "estado" como nombre visible
            return repo.save(e);
        });
    }
}
