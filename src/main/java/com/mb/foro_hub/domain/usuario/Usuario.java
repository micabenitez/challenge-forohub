package com.mb.foro_hub.domain.usuario;

import com.mb.foro_hub.domain.perfil.Perfil;
import com.mb.foro_hub.domain.usuario.dto.DatosActualizacionUsuario;
import com.mb.foro_hub.domain.usuario.dto.DatosRegistroUsuario;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
@EqualsAndHashCode(of = "id")
@Table(name = "usuarios")
@Entity(name = "Usuario")
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;
    private String email;
    private String contrasena;
    private Boolean activo = true;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "usuarios_perfiles",
            joinColumns = @JoinColumn(name = "usuario_id"),
            inverseJoinColumns = @JoinColumn(name = "perfil_id")
    )
    private Set<Perfil> perfiles = new HashSet<>();

    public Usuario(DatosRegistroUsuario datos) {
        this.nombre = datos.nombre();
        this.email = datos.email();
        this.contrasena = datos.contrasena();
    }

    public void actualizar(DatosActualizacionUsuario datos) {
        if(datos.nombre() != null) this.nombre = datos.nombre();
        if(datos.email() != null) this.email = datos.email();
    }

    public void desactivar() {
        this.activo = false;
    }
}
