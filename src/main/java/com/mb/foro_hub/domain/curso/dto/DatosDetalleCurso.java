package com.mb.foro_hub.domain.curso.dto;

import com.mb.foro_hub.domain.curso.Curso;

public record DatosDetalleCurso(
        Long id,
        String nombre,
        String categoria,
        Boolean activo
) {
    public DatosDetalleCurso(Curso curso) {
        this(curso.getId(), curso.getNombre(), curso.getCategoria(), curso.getActivo());
    }
}
