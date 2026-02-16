package com.mb.foro_hub.domain.usuario.dto;

import com.mb.foro_hub.domain.perfil.Perfil;
import com.mb.foro_hub.domain.usuario.Usuario;

import java.util.List;

public record DatosDetalleUsuario(
        Long id,
        String nombre,
        String email,
        List<String> perfiles
) {

    public DatosDetalleUsuario(Usuario usuario) {
        this(
                usuario.getId(),
                usuario.getNombre(),
                usuario.getEmail(),
                usuario.getPerfiles().stream()
                        .map(Perfil::getNombre)
                        .toList()
        );
    }
}