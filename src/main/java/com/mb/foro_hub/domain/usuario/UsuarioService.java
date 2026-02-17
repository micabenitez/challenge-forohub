package com.mb.foro_hub.domain.usuario;

import com.mb.foro_hub.domain.perfil.PerfilRepository;
import com.mb.foro_hub.domain.usuario.dto.DatosActualizacionUsuario;
import com.mb.foro_hub.domain.usuario.dto.DatosDetalleUsuario;
import com.mb.foro_hub.domain.usuario.dto.DatosRegistroUsuario;
import com.mb.foro_hub.exceptions.ValidacionException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UsuarioService {
    private final UsuarioRepository usuarioRepository;
    private final PerfilRepository perfilRepository;
    private final PasswordEncoder passwordEncoder;

    public DatosDetalleUsuario registrarUsuario(DatosRegistroUsuario datos) {
        var perfilUsuario = perfilRepository.findByNombre("ROLE_ALUMNO")
                .orElseThrow(() -> new IllegalStateException("El perfil ROLE_ALUMNO no existe en la BD"));

        var usuario = new Usuario(datos);
        usuario.getPerfiles().add(perfilUsuario);
        usuario.setContrasena(passwordEncoder.encode(datos.contrasena()));

        usuarioRepository.save(usuario);
        return new DatosDetalleUsuario(usuario);
    }

    @PreAuthorize("#id == authentication.principal.id or hasRole('ADMIN')")
    public DatosDetalleUsuario actualizar(Long id, DatosActualizacionUsuario datos) {
        var usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new ValidacionException("El usuario con ese ID no existe"));

        usuario.actualizar(datos);
        return new DatosDetalleUsuario(usuario);
    }

    @PreAuthorize("isAuthenticated()")
    public DatosDetalleUsuario obtenerUsuarioPorId(Long id) {
        var usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new ValidacionException("El usuario con ese ID no existe"));
        return new DatosDetalleUsuario(usuario);
    }

    @PreAuthorize("hasRole('ADMIN')")
    public Page<DatosDetalleUsuario> listarUsuarios(Pageable paginacion) {
        return usuarioRepository.findAllByActivoTrue(paginacion).map(DatosDetalleUsuario::new);
    }

    @PreAuthorize("hasRole('ADMIN')")
    public void eliminar(Long id) {
        var usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new ValidacionException("El usuario con ese ID no existe"));
        usuario.desactivar();
    }

    @PreAuthorize("hasRole('ADMIN')")
    public void asignarPerfil(Long id, String nombrePerfil) {
        var usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new ValidacionException("Usuario no encontrado"));

        var perfil = perfilRepository.findByNombre(nombrePerfil)
                .orElseThrow(() -> new ValidacionException("Perfil no encontrado"));
        usuario.getPerfiles().add(perfil);
    }
}
