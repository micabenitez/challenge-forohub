package com.mb.foro_hub.domain.topico.dto;

import com.mb.foro_hub.domain.topico.Topico;

import java.time.LocalDateTime;

public record DatosDetalleTopico(
        Long id,
        String titulo,
        String mensaje,
        LocalDateTime fechaCreacion,
        String status,
        String autor,
        String curso
) {
    public DatosDetalleTopico(Topico topico) {
        this(
                topico.getId(),
                topico.getTitulo(),
                topico.getMensaje(),
                topico.getFechaCreacion(),
                topico.getStatus().name(),
                topico.getUsuario().getNombre(),
                topico.getCurso().getNombre()
        );
    }
}
