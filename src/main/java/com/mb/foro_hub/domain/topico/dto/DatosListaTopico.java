package com.mb.foro_hub.domain.topico.dto;

public record DatosListaTopico(
        Long id,
        String titulo,
        String status,
        String autor
) {
}
