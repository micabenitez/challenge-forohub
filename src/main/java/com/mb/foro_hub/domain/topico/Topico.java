package com.mb.foro_hub.domain.topico;

import com.mb.foro_hub.domain.curso.Curso;
import com.mb.foro_hub.domain.respuesta.Respuesta;
import com.mb.foro_hub.domain.usuario.Usuario;

import java.time.LocalDateTime;

public class Topico {
    Long id;
    String titulo;
    String mensaje;
    LocalDateTime fechaCreacion;
    EstadoTopico estadoTopico;
    Usuario usuario;
    Curso curso;
    Respuesta respuestas;

}
