package com.mb.foro_hub.domain.respuesta;

import com.mb.foro_hub.domain.respuesta.dto.DatosActualizarRespuesta;
import com.mb.foro_hub.domain.respuesta.dto.DatosListaRespuesta;
import com.mb.foro_hub.domain.respuesta.dto.DatosDetalleRespuesta;
import com.mb.foro_hub.domain.respuesta.dto.DatosRegistroRespuesta;
import com.mb.foro_hub.domain.topico.EstadoTopico;
import com.mb.foro_hub.domain.topico.TopicoRepository;
import com.mb.foro_hub.domain.usuario.UsuarioRepository;
import com.mb.foro_hub.exceptions.ValidacionException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RespuestaService {
    private final RespuestaRepository respuestaRepository;
    private final TopicoRepository topicoRepository;
    private final UsuarioRepository usuarioRepository;

    @PreAuthorize("isAuthenticated()")
    public DatosDetalleRespuesta crearRespuesta(Long idTopico, DatosRegistroRespuesta datos) {
        var topico = topicoRepository.findById(idTopico)
                .orElseThrow(() -> new ValidacionException("No existe un topico con ese ID"));

        if(topico.getStatus() == EstadoTopico.CERRADO) throw new ValidacionException("No se puede responder a un tópico cerrado");

        if (topico.getStatus() == EstadoTopico.SIN_RESPUESTA) {
            topico.setStatus(EstadoTopico.NO_SOLUCIONADO);
        }

        var usuario = usuarioRepository.findById(datos.idUsuario())
                .orElseThrow(() -> new ValidacionException("No existe un usuario con ese ID"));

        var respuesta = new Respuesta(datos, usuario, topico);
        respuestaRepository.save(respuesta);

        return new DatosDetalleRespuesta(respuesta);
    }

    public Page<DatosListaRespuesta> listarRespuestas(Long topicoId, Pageable paginacion) {
        if (!topicoRepository.existsById(topicoId)) throw new ValidacionException("No existe un tópico con ese ID");
        return respuestaRepository.findAllByTopicoId(topicoId, paginacion)
                .map(DatosListaRespuesta::new);
    }

    public DatosDetalleRespuesta obtenerRespuestaPorId(Long id) {
        var respuesta = getRespuesta(id);
        return new DatosDetalleRespuesta(respuesta);
    }

    @PreAuthorize("@respuestaService.esAutor(#id, authentication.name) or hasRole('ADMIN')")
    public DatosDetalleRespuesta actualizarRespuesta(Long id, DatosActualizarRespuesta datos) {
        var respuesta = getRespuesta(id);
        respuesta.actualizarDatos(datos);

        return new DatosDetalleRespuesta(respuesta);
    }

    @PreAuthorize("@respuestaService.esAutor(#id, authentication.name) or hasRole('ADMIN')")
    public void desactivarRespuesta(Long id) {
        var respuesta = getRespuesta(id);
        respuesta.desactivar();
    }

    private Respuesta getRespuesta(Long id) {
        var respuesta = respuestaRepository.findById(id)
                .orElseThrow(() -> new ValidacionException("Respuesta no encontrada"));
        return respuesta;
    }

    @PreAuthorize("@respuestaService.esAutor(#id, authentication.name) or hasRole('ADMIN') or hasRole('PROFESOR')")
    public DatosDetalleRespuesta marcarComoSolucion(Long id) {
        var respuesta = getRespuesta(id);
        var topico = respuesta.getTopico();

        if (topico.getStatus() == EstadoTopico.CERRADO || topico.getStatus() == EstadoTopico.SPAM) {
            throw new ValidacionException("No se puede marcar solución en un tópico cerrado o spam");
        }

        topico.getRespuestas().stream()
                .filter(Respuesta::getSolucion)
                .forEach(Respuesta::desmarcarComoSolucion);

        respuesta.marcarComoSolucion();
        topico.marcarComoSolucionado();

        return new DatosDetalleRespuesta(respuesta);
    }

    public boolean esAutor(Long id, String email) {
        return respuestaRepository.esAutor(id, email);
    }
}
