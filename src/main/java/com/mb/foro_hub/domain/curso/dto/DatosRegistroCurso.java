package com.mb.foro_hub.domain.curso.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record DatosRegistroCurso(
        @NotBlank(message = "El nombre es obligatorio")
        @Size(min = 3, max = 100, message = "El nombre debe tener entre 3 y 100 caracteres")
        String nombre,
        @NotBlank(message = "La categoría es obligatoria")
        String categoria
) {
}
