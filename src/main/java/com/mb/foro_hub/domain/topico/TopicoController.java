package com.mb.foro_hub.domain.topico;

import com.mb.foro_hub.domain.topico.dto.DatosActualizacionTopico;
import com.mb.foro_hub.domain.topico.dto.DatosDetalleTopico;
import com.mb.foro_hub.domain.topico.dto.DatosListaTopico;
import com.mb.foro_hub.domain.topico.dto.DatosRegistroTopico;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/api/v1/topicos")
@RequiredArgsConstructor
public class TopicoController {

    private final TopicoService service;

    @PostMapping
    @Transactional
    public ResponseEntity<DatosDetalleTopico> registrarTopico(@RequestBody @Valid DatosRegistroTopico datos, UriComponentsBuilder uriComponentsBuilder) {
        DatosDetalleTopico topicoCreado = service.registrarTopico(datos);

        URI uri = uriComponentsBuilder
                .path("/topicos/{id}")
                .buildAndExpand(topicoCreado.id())
                .toUri();
        return ResponseEntity.created(uri).body(topicoCreado);
    }

    @GetMapping
    public ResponseEntity<Page<DatosListaTopico>> listarTopicos(@PageableDefault(size = 10, sort = {"fechaCreacion"}, direction = Sort.Direction.ASC) Pageable paginacion) {
        var page = service.listarTopicos(paginacion);
        return ResponseEntity.ok(page);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DatosDetalleTopico> obtenerTopicoPorId(@PathVariable Long id) {
        return ResponseEntity.ok(service.obtenerTopicoPorId(id));
    }

    @PutMapping
    @Transactional
    public ResponseEntity<DatosDetalleTopico> actualizar(@RequestBody @Valid DatosActualizacionTopico datos) {
        return ResponseEntity.ok(service.actualizar(datos));
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity desactivar(@PathVariable Long id) {
        service.desactivar(id);
        return ResponseEntity.noContent().build();
    }
}
