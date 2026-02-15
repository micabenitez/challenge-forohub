package com.mb.foro_hub.domain.respuesta.dto;

import com.mb.foro_hub.domain.respuesta.Respuesta;

public record DatosListaRespuesta(
        String mensaje,
        String usuario,
        Long idTopico
) {
    public DatosListaRespuesta(Respuesta rta) {
        this(
                rta.getMensaje(),
                rta.getUsuario().getNombre(),
                rta.getTopico().getId()
        );
    }
}
