package com.mb.foro_hub.domain.usuario;

import com.mb.foro_hub.domain.usuario.dto.DatosAuth;
import com.mb.foro_hub.domain.usuario.dto.DatosDetalleUsuario;
import com.mb.foro_hub.domain.usuario.dto.DatosRegistroUsuario;
import com.mb.foro_hub.infra.security.DatosTokenJWT;
import com.mb.foro_hub.infra.security.TokenService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager manager;
    private final TokenService tokenService;
    private final UsuarioService usuarioService;

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody @Valid DatosAuth datos) {
        var authToken =  new UsernamePasswordAuthenticationToken(datos.email(), datos.contrasena());
        var auth = manager.authenticate(authToken);

        var tokenJWT = tokenService.generarToken((Usuario) auth.getPrincipal());
        return ResponseEntity.ok(new DatosTokenJWT(tokenJWT));
    }

    @PostMapping("/signup")
    @Transactional
    public ResponseEntity<DatosDetalleUsuario> registrar(@RequestBody @Valid DatosRegistroUsuario datos, UriComponentsBuilder uriBuilder) {
        var usuario = usuarioService.registrarUsuario(datos);
        var uri = uriBuilder.path("/usuarios/{id}").buildAndExpand(usuario.id()).toUri();
        return ResponseEntity.created(uri).body(usuario);
    }
}
