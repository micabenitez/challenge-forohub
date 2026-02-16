package com.mb.foro_hub.domain.perfil;

import com.mb.foro_hub.domain.usuario.Usuario;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
@EqualsAndHashCode(of = "id")
@Table(name = "perfiles")
@Entity(name = "Perfil")
public class Perfil {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;

    public Perfil(String nombre) {
        this.nombre = nombre;
    }
}
