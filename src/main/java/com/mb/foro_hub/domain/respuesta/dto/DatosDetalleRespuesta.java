package com.mb.foro_hub.domain.respuesta.dto;

import com.mb.foro_hub.domain.respuesta.Respuesta;

import java.time.LocalDateTime;

public record DatosDetalleRespuesta(
        Long id,
        String mensaje,
        LocalDateTime fechaCreacion,
        String usuario,
        Long idTopico,
        Boolean solucion
){
    public DatosDetalleRespuesta(Respuesta rta) {
        this(
                rta.getId(),
                rta.getMensaje(),
                rta.getFechaCreacion(),
                rta.getUsuario().getNombre(),
                rta.getTopico().getId(),
                rta.getSolucion()
        );
    }
}
