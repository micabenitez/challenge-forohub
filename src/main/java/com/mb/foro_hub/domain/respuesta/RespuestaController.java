package com.mb.foro_hub.domain.respuesta;

import com.mb.foro_hub.domain.respuesta.dto.DatosActualizarRespuesta;
import com.mb.foro_hub.domain.respuesta.dto.DatosListaRespuesta;
import com.mb.foro_hub.domain.respuesta.dto.DatosDetalleRespuesta;
import com.mb.foro_hub.domain.respuesta.dto.DatosRegistroRespuesta;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class RespuestaController {
    private final RespuestaService service;

    @PostMapping("/topicos/{topicoId}/respuestas")
    @Transactional
    public ResponseEntity<DatosDetalleRespuesta> crearRespuesta(
            @PathVariable Long topicoId, @RequestBody @Valid DatosRegistroRespuesta datos,
            UriComponentsBuilder uriComponentsBuilder) {
        DatosDetalleRespuesta respuesta = service.crearRespuesta(topicoId, datos);
        URI uri = uriComponentsBuilder
                .path("/api/v1/respuestas/{id}")
                .buildAndExpand(respuesta.id())
                .toUri();
        return ResponseEntity.created(uri).body(respuesta);
    }

    @GetMapping("/topicos/{topicoId}/respuestas")
    public ResponseEntity<Page<DatosListaRespuesta>> listarRespuestasPorTopico(
            @PathVariable Long topicoId,
            @PageableDefault(size = 10, sort = "fechaCreacion") Pageable paginacion
    ) {
        return ResponseEntity.ok(service.listarRespuestas(topicoId, paginacion));
    }


    @GetMapping("/respuestas/{id}")
    public ResponseEntity<DatosDetalleRespuesta> obtenerRespuestaPorId(@PathVariable Long id) {
        return ResponseEntity.ok(service.obtenerRespuestaPorId(id));
    }

    @PutMapping("/respuestas/{id}")
    @Transactional
    public ResponseEntity<DatosDetalleRespuesta> actualizar(@PathVariable Long id, @RequestBody @Valid DatosActualizarRespuesta datos) {
        return ResponseEntity.ok(service.actualizarRespuesta(id, datos));
    }

    @DeleteMapping("/respuestas/{id}")
    @Transactional
    public ResponseEntity desactivar(@PathVariable Long id) {
        service.desactivarRespuesta(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/respuestas/{id}/solucion")
    @Transactional
    public ResponseEntity<DatosDetalleRespuesta> marcarComoSolucion(@PathVariable Long id) {
        var respuesta = service.marcarComoSolucion(id);
        return ResponseEntity.ok(respuesta);
    }
}
