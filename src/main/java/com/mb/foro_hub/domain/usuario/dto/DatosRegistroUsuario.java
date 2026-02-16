package com.mb.foro_hub.domain.usuario.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record DatosRegistroUsuario(
        @NotBlank(message = "El nombre es obligatorio")
        @Size(min = 3, max = 100)
        String nombre,
        @NotBlank(message = "El email es obligatorio")
        @Email(message = "El formato del email es inválido")
        String email,
        @NotBlank(message = "La contraseña es obligatoria")
        @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z]).{6,}$", message = "Clave insegura")
        String contrasena
) {}