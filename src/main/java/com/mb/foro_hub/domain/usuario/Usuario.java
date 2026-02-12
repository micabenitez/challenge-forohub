package com.mb.foro_hub.domain.usuario;

import com.mb.foro_hub.domain.perfil.Perfil;

import java.util.List;

public class Usuario {
    private Long id;
    private String nombre;
    private String email;
    private String contrasena;
    private List<Perfil> perfiles;
}
