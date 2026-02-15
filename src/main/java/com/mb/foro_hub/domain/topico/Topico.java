package com.mb.foro_hub.domain.topico;

import com.mb.foro_hub.domain.curso.Curso;
import com.mb.foro_hub.domain.respuesta.Respuesta;
import com.mb.foro_hub.domain.topico.dto.DatosActualizacionTopico;
import com.mb.foro_hub.domain.usuario.Usuario;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
@EqualsAndHashCode(of = "id")
@Table(name = "topicos")
@Entity(name = "Topico")
public class Topico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String titulo;
    private String mensaje;
    @Column(name = "fecha_creacion")
    private LocalDateTime fechaCreacion = LocalDateTime.now();;

    @Column(name = "status")
    @Enumerated(value = EnumType.STRING)
    private EstadoTopico status = EstadoTopico.SIN_RESPUESTA;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "curso_id")
    private Curso curso;

    @OneToMany(mappedBy = "topico")
    private List<Respuesta> respuestas;

    private Boolean activo = true;

    public Topico(String titulo, String mensaje, Curso curso, Usuario usuario) {
        this.titulo = titulo;
        this.mensaje = mensaje;
        this.curso = curso;
        this.usuario = usuario;
    }

    public void actualizarInfo(@Valid DatosActualizacionTopico datos) {
        if(titulo != null) {
            this.titulo = datos.titulo();
        }
        if(mensaje != null) {
            this.mensaje = datos.mensaje();
        }
    }

    public void desactivar() {
        this.activo = false;
    }

    public void marcarComoSolucionado() {
        this.status = EstadoTopico.SOLUCIONADO;
    }
}
