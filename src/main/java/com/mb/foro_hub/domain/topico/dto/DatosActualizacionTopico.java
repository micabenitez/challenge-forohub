package com.mb.foro_hub.domain.topico.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record DatosActualizacionTopico(
        @NotNull Long id,
        @NotBlank String titulo,
        @NotBlank String mensaje
) {
}
