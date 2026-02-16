package com.mb.foro_hub.domain.usuario;

import com.mb.foro_hub.domain.perfil.PerfilRepository;
import com.mb.foro_hub.domain.usuario.dto.DatosActualizacionUsuario;
import com.mb.foro_hub.domain.usuario.dto.DatosDetalleUsuario;
import com.mb.foro_hub.domain.usuario.dto.DatosRegistroUsuario;
import com.mb.foro_hub.exceptions.ValidacionException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UsuarioService {
    private final UsuarioRepository usuarioRepository;
    private final PerfilRepository perfilRepository;

    public DatosDetalleUsuario registrarUsuario(DatosRegistroUsuario datos) {
        var perfilUsuario = perfilRepository.findByNombre("ROLE_USER")
                .orElseThrow(() -> new IllegalStateException("El perfil ROLE_USER no existe en la BD"));

        var usuario = new Usuario(datos);
        usuario.getPerfiles().add(perfilUsuario);

        usuarioRepository.save(usuario);

        return new DatosDetalleUsuario(usuario);
    }

    public DatosDetalleUsuario actualizar(Long id, DatosActualizacionUsuario datos) {
        var usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new ValidacionException("El usuario con ese ID no existe"));

        usuario.actualizar(datos);
        return new DatosDetalleUsuario(usuario);
    }

    public DatosDetalleUsuario obtenerUsuarioPorId(Long id) {
        var usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new ValidacionException("El usuario con ese ID no existe"));
        return new DatosDetalleUsuario(usuario);
    }

    public Page<DatosDetalleUsuario> listarUsuarios(Pageable paginacion) {
        return usuarioRepository.findAllByActivoTrue(paginacion).map(DatosDetalleUsuario::new);
    }

    public void eliminar(Long id) {
        var usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new ValidacionException("El usuario con ese ID no existe"));

        usuario.desactivar();
    }
}
