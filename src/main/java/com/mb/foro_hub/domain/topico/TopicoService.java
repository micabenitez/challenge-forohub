package com.mb.foro_hub.domain.topico;

import com.mb.foro_hub.domain.curso.CursoRepository;
import com.mb.foro_hub.domain.topico.dto.DatosDetalleTopico;
import com.mb.foro_hub.domain.topico.dto.DatosRegistroTopico;
import com.mb.foro_hub.domain.usuario.Usuario;
import com.mb.foro_hub.domain.usuario.UsuarioRepository;
import com.mb.foro_hub.exceptions.ValidacionException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
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
        Usuario usuario = null;

        var topico = new Topico(datos.titulo(), datos.mensaje(), curso, usuario);
        topicoRepository.save(topico);
        return new DatosDetalleTopico(topico);
    }
}
