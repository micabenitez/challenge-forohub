package com.mb.foro_hub.domain.respuesta.dto;

import jakarta.validation.constraints.NotBlank;

public record DatosActualizarRespuesta (
        @NotBlank String mensaje
) {
}
