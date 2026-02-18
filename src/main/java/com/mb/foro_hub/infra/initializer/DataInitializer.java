package com.mb.foro_hub.infra.initializer;

import com.mb.foro_hub.domain.perfil.Perfil;
import com.mb.foro_hub.domain.perfil.PerfilRepository;
import com.mb.foro_hub.domain.usuario.Usuario;
import com.mb.foro_hub.domain.usuario.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
@Profile("!test")
public class DataInitializer implements CommandLineRunner {

    private final PerfilRepository perfilRepository;
    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public void run(String... args) {
        crearPerfiles();
        crearAdmin();
    }

    private void crearPerfiles() {
        crearPerfilSiNoExiste("ROLE_ALUMNO");
        crearPerfilSiNoExiste("ROLE_PROFESOR");
        crearPerfilSiNoExiste("ROLE_ADMIN");
    }

    private void crearPerfilSiNoExiste(String nombre) {
        if (!perfilRepository.existsByNombre(nombre)) {
            perfilRepository.save(new Perfil(nombre));
            System.out.println("Perfil creado: " + nombre);
        }
    }

    private void crearAdmin() {
        if (usuarioRepository.existsByEmail("admin@foro.com")) {
            return;
        }

        Perfil perfilAdmin = perfilRepository.findByNombre("ADMIN")
                .orElseThrow(() -> new RuntimeException("Perfil ADMIN no encontrado"));

        Usuario admin = new Usuario();

        admin.setNombre("Administrador");
        admin.setEmail("admin@foro.com");
        admin.setContrasena(passwordEncoder.encode("123456"));
        admin.getPerfiles().add(perfilAdmin);

        usuarioRepository.save(admin);

        System.out.println("Usuario admin creado");
    }
}
