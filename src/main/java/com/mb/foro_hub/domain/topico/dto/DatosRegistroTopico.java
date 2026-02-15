package com.mb.foro_hub.domain.topico.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record DatosRegistroTopico(
        @NotBlank(message = "El titulo es obligatorio")
        String titulo,
        @NotBlank(message = "El mensaje es obligatorio")
        String mensaje,
        @NotNull(message = "El ID del curso es obligatorio") Long idCurso
) {
}
