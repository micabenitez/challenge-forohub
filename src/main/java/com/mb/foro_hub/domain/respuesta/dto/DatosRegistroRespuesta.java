package com.mb.foro_hub.domain.respuesta.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record DatosRegistroRespuesta(
        @NotNull Long idUsuario,
        @NotBlank String mensaje
){
}
