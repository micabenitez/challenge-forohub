package com.mb.foro_hub.domain.usuario;

import com.mb.foro_hub.domain.perfil.Perfil;
import com.mb.foro_hub.domain.usuario.dto.DatosActualizacionUsuario;
import com.mb.foro_hub.domain.usuario.dto.DatosRegistroUsuario;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
@EqualsAndHashCode(of = "id")
@Table(name = "usuarios")
@Entity(name = "Usuario")
public class Usuario implements UserDetails {
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

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return perfiles.stream()
                .map(perfil -> new SimpleGrantedAuthority(perfil.getNombre()))
                .collect(Collectors.toList());
    }

    @Override
    public String getPassword() {
        return contrasena;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
