package com.mb.foro_hub.domain.curso;

import com.mb.foro_hub.domain.curso.dto.DatosActualizacionCurso;
import com.mb.foro_hub.domain.curso.dto.DatosDetalleCurso;
import com.mb.foro_hub.domain.curso.dto.DatosRegistroCurso;
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
@RequestMapping("/api/v1/cursos")
@RequiredArgsConstructor
public class CursoController {
    private final CursoService service;

    @PostMapping
    @Transactional
    public ResponseEntity<DatosDetalleCurso> registrar(@RequestBody @Valid DatosRegistroCurso datos, UriComponentsBuilder uriComponentsBuilder) {
        var curso = service.registrarCurso(datos);

        URI uri = uriComponentsBuilder
                .path("/api/v1/cursos/{id}")
                .buildAndExpand(curso.id())
                .toUri();
        return ResponseEntity.created(uri).body(curso);
    }

    @GetMapping
    public ResponseEntity<Page<DatosDetalleCurso>> obtenerCursos(@PageableDefault(size = 10, sort = {"nombre"}, direction = Sort.Direction.ASC) Pageable paginacion ) {
        return ResponseEntity.ok(service.obtenerCursos(paginacion));
    }


    @GetMapping("/{id}")
    public ResponseEntity<DatosDetalleCurso> obtenerCursoPorId(@PathVariable Long id) {
        return ResponseEntity.ok(service.obtenerCursoPorId(id));
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<DatosDetalleCurso> actualizar(@PathVariable Long id, @RequestBody @Valid DatosActualizacionCurso datos) {
        return ResponseEntity.ok(service.actualizar(id, datos));
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity desactivar(@PathVariable Long id) {
        service.desactivar(id);
        return ResponseEntity.noContent().build();
    }

}
