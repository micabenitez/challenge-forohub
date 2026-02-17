package com.mb.foro_hub.domain.perfil;

import jakarta.validation.constraints.NotBlank;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PerfilRepository extends JpaRepository<Perfil, Long> {
    Optional<Perfil> findByNombre(String nombre);

    boolean existsByNombre(String nombre);
}