package com.mb.foro_hub.domain.topico;

import com.mb.foro_hub.domain.curso.CursoRepository;
import com.mb.foro_hub.domain.topico.dto.DatosActualizacionTopico;
import com.mb.foro_hub.domain.topico.dto.DatosDetalleTopico;
import com.mb.foro_hub.domain.topico.dto.DatosListaTopico;
import com.mb.foro_hub.domain.topico.dto.DatosRegistroTopico;
import com.mb.foro_hub.domain.usuario.Usuario;
import com.mb.foro_hub.domain.usuario.UsuarioRepository;
import com.mb.foro_hub.exceptions.ValidacionException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TopicoService {

    private final TopicoRepository topicoRepository;
    private final CursoRepository cursoRepository;

    @PreAuthorize("isAuthenticated()")
    public DatosDetalleTopico registrarTopico(@Valid DatosRegistroTopico datos) {
        if (topicoRepository.existsByTituloAndMensaje(datos.titulo(), datos.mensaje())) {
            throw new ValidacionException("Ya existe un tópico con el mismo título y mensaje");
        }

        var curso = cursoRepository.findById(datos.idCurso())
                .orElseThrow(() -> new ValidacionException("No existe un curso con ese ID"));

        var authentication = SecurityContextHolder.getContext().getAuthentication();
        var usuario = (Usuario) authentication.getPrincipal();

        var topico = new Topico(datos.titulo(), datos.mensaje(), curso, usuario);
        topicoRepository.save(topico);
        return new DatosDetalleTopico(topico);
    }

    public Page<DatosListaTopico> listarTopicos(Pageable paginacion) {
        return topicoRepository.findAllByActivoTrue(paginacion).map(DatosListaTopico::new);
    }

    @PreAuthorize("@topicoService.esAutor(#id, authentication.name) or hasRole('ADMIN')")
    public DatosDetalleTopico actualizar(Long id, @Valid DatosActualizacionTopico datos) {
        var topico = topicoRepository.findById(id)
                .orElseThrow(() -> new ValidacionException("No existe un topico con ese ID"));

        topico.actualizarInfo(datos);

        return new DatosDetalleTopico(topico);
    }

    @PreAuthorize("@topicoService.esAutor(#id, authentication.name) or hasRole('ADMIN')")
    public void desactivar(Long id) {
        var topico = topicoRepository.findById(id)
                .orElseThrow(() -> new ValidacionException("No existe un topico con ese ID"));
        topico.desactivar();
    }

    public DatosDetalleTopico obtenerTopicoPorId(Long id) {
        var topico = topicoRepository.findById(id)
                .orElseThrow(() -> new ValidacionException("No existe un topico con ese ID"));
        return new DatosDetalleTopico(topico);
    }

    public boolean esAutor(Long id, String email) {
        return topicoRepository.esAutor(id, email);
    }
}
