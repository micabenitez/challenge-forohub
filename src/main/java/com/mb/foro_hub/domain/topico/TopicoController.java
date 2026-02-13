package com.mb.foro_hub.domain.topico;

import com.mb.foro_hub.domain.topico.dto.DatosDetalleTopico;
import com.mb.foro_hub.domain.topico.dto.DatosRegistroTopico;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
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
}
