package com.mb.foro_hub.domain.curso;

import com.mb.foro_hub.domain.curso.dto.DatosActualizacionCurso;
import com.mb.foro_hub.domain.curso.dto.DatosRegistroCurso;
import jakarta.persistence.*;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
@EqualsAndHashCode(of = "id")
@Table(name = "cursos")
@Entity(name = "Curso")
public class Curso {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;
    private String categoria;
    private Boolean activo = true;

    public Curso(DatosRegistroCurso datos) {
        this.nombre = datos.nombre();
        this.categoria = datos.categoria();
    }

    public void actualizar(DatosActualizacionCurso datos) {
        if(datos.nombre() != null) this.nombre = datos.nombre();
        if(datos.categoria() != null) this.categoria = datos.categoria();
    }

    public void desactivar() {
        this.activo = false;
    }
}