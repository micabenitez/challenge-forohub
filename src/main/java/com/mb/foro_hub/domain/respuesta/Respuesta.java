package com.mb.foro_hub.domain.respuesta;

import com.mb.foro_hub.domain.topico.Topico;
import com.mb.foro_hub.domain.usuario.Usuario;

import java.time.LocalDateTime;

public class Respuesta {
    private Long id;
    private String mensaje;
    private Topico topico;
    private LocalDateTime fechaCreacion;
    private Usuario usuario;
    private String solucion;
}
