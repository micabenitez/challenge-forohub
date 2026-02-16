package com.mb.foro_hub.domain.usuario.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record DatosActualizacionUsuario(
        @NotBlank String nombre,
        @NotBlank @Email String email
) {
}
