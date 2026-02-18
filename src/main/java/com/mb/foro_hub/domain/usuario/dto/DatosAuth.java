package com.mb.foro_hub.domain.usuario.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record DatosAuth(
        @NotBlank @Email String email,
        @NotBlank String contrasena
) {
}
