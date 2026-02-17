package com.mb.foro_hub.domain.usuario;

import com.mb.foro_hub.domain.usuario.dto.DatosActualizacionUsuario;
import com.mb.foro_hub.domain.usuario.dto.DatosDetalleUsuario;
import com.mb.foro_hub.domain.usuario.dto.DatosRegistroUsuario;
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
@RequestMapping("/api/v1/usuarios")
@RequiredArgsConstructor
public class UsuarioController {
    private final UsuarioService service;

    @PostMapping
    @Transactional
    public ResponseEntity<DatosDetalleUsuario> registrar(@RequestBody @Valid DatosRegistroUsuario datos, UriComponentsBuilder uriComponentsBuilder) {
        var usuario = service.registrarUsuario(datos);

        URI uri = uriComponentsBuilder
                    .path("/api/v1/usuarios/{id}")
                    .buildAndExpand(usuario.id())
                    .toUri();

        return ResponseEntity.created(uri).body(usuario);
    }

    @GetMapping
    public ResponseEntity<Page<DatosDetalleUsuario>> listarUsuarios(@PageableDefault(size = 10) Pageable paginacion) {
        return ResponseEntity.ok(service.listarUsuarios(paginacion));
    }

    @GetMapping("/{id}")
    public ResponseEntity<DatosDetalleUsuario> obtenerUsuarioPorId(@PathVariable Long id) {
        return ResponseEntity.ok(service.obtenerUsuarioPorId(id));
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<DatosDetalleUsuario> actualizar(@PathVariable Long id, @RequestBody @Valid DatosActualizacionUsuario datos) {
        var usuario = service.actualizar(id, datos);
        return ResponseEntity.ok(usuario);
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        service.eliminar(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/perfil")
    public ResponseEntity<Void> asignarPerfil(@PathVariable Long id, @RequestParam String perfil) {
        service.asignarPerfil(id, perfil);
        return ResponseEntity.noContent().build();
    }

}
