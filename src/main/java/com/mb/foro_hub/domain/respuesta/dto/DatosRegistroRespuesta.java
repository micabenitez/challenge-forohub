package com.mb.foro_hub.domain.respuesta.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record DatosRegistroRespuesta(
        @NotNull Long idUsuario,
        @NotBlank(message = "El mensaje es obligatorio") String mensaje
){
}
