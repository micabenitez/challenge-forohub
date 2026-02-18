package com.mb.foro_hub.domain.usuario;

import com.mb.foro_hub.domain.usuario.dto.DatosAuth;
import com.mb.foro_hub.infra.security.TokenService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
@ActiveProfiles("test")
class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private JacksonTester<DatosAuth> jsonAutenticacion;
    @MockitoBean
    private AuthenticationManager authenticationManager;
    @MockitoBean
    private TokenService tokenService;

    @Test
    @DisplayName("Debería devolver http 200 y el Token cuando las credenciales son válidas")
    void login_escenario1() throws Exception {
        // 1. Arrange
        var datosLogin = new DatosAuth("usuario@test.com", "123456");

        Authentication authMock = mock(Authentication.class);
        Usuario usuarioMock = new Usuario();
        when(authMock.getPrincipal()).thenReturn(usuarioMock);

        when(authenticationManager.authenticate(any()))
                .thenReturn(authMock);

        when(tokenService.generarToken(any())).thenReturn("token-jwt-simulado");

        // 2. Act
        var response = mockMvc.perform(post("/api/v1/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonAutenticacion.write(datosLogin).getJson())
        ).andReturn().getResponse();

        // 3. Assert
        Assertions.assertThat(response.getStatus()).isEqualTo(200);
        Assertions.assertThat(response.getContentAsString()).contains("token-jwt-simulado");
    }

    @Test
    @DisplayName("Debería devolver http 400 cuando los datos de login son inválidos")
    void login_escenario2() throws Exception {
        mockMvc.perform(post("/api/v1/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{}")
        ).andExpect(status().isBadRequest());
    }
}