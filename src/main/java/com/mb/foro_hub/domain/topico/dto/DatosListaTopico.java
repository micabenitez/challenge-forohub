package com.mb.foro_hub.domain.topico.dto;

import com.mb.foro_hub.domain.topico.Topico;

public record DatosListaTopico(
        Long id,
        String titulo,
        String status,
        String autor
) {

    public DatosListaTopico(Topico topico) {
        this(
                topico.getId(),
                topico.getTitulo(),
                topico.getStatus().toString(),
                topico.getUsuario().getNombre()
        );
    }
}
