package com.mb.foro_hub.domain.topico;

import com.mb.foro_hub.domain.curso.CursoRepository;
import com.mb.foro_hub.domain.topico.dto.DatosActualizacionTopico;
import com.mb.foro_hub.domain.topico.dto.DatosDetalleTopico;
import com.mb.foro_hub.domain.topico.dto.DatosListaTopico;
import com.mb.foro_hub.domain.topico.dto.DatosRegistroTopico;
import com.mb.foro_hub.domain.usuario.UsuarioRepository;
import com.mb.foro_hub.exceptions.ValidacionException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TopicoService {

    private final TopicoRepository topicoRepository;
    private final CursoRepository cursoRepository;
    private final UsuarioRepository usuarioRepository;

    public DatosDetalleTopico registrarTopico(@Valid DatosRegistroTopico datos) {
        var curso = cursoRepository.findById(datos.idCurso())
                .orElseThrow(() -> new ValidacionException("No existe un curso con ese ID"));

        //TODO: implementar jwt
        var usuario = usuarioRepository.getReferenceById(1L);

        var topico = new Topico(datos.titulo(), datos.mensaje(), curso, usuario);
        topicoRepository.save(topico);
        return new DatosDetalleTopico(topico);
    }

    public Page<DatosListaTopico> listarTopicos(Pageable paginacion) {
        return topicoRepository.findAllByActivoTrue(paginacion).map(DatosListaTopico::new);
    }

    public DatosDetalleTopico actualizar(@Valid DatosActualizacionTopico datos) {
        var topico = topicoRepository.findById(datos.id())
                .orElseThrow(() -> new ValidacionException("No existe un topico con ese ID"));

        topico.actualizarInfo(datos);

        return new DatosDetalleTopico(topico);
    }

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
}
