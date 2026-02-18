package com.mb.foro_hub.domain.topico;

import com.mb.foro_hub.exceptions.ValidacionException;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;

import com.mb.foro_hub.domain.topico.dto.DatosDetalleTopico;
import com.mb.foro_hub.domain.topico.dto.DatosRegistroTopico;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
@ActiveProfiles("test")
class TopicoControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private JacksonTester<DatosRegistroTopico> jsonRegistro;
    @Autowired
    private JacksonTester<DatosDetalleTopico> jsonDetalle;
    @MockitoBean
    private TopicoService topicoService;

    @Test
    @DisplayName("Debería devolver http 400 cuando la solicitud no tenga body")
    @WithMockUser
    void registrar_escenario1() throws Exception {
        var response = mockMvc.perform(post("/api/v1/topicos"))
                .andReturn().getResponse();

        Assertions.assertThat(response.getStatus())
                .isEqualTo(400);
    }

    @Test
    @DisplayName("Debería devolver http 201 cuando los datos son válidos")
    @WithMockUser
    void registrar_escenario2() throws Exception {
        var datosRegistro = new DatosRegistroTopico("Título test", "Mensaje test", 1L);
        var datosDetalle = new DatosDetalleTopico(1L, "Título test", "Mensaje test", LocalDateTime.now(), "SIN_RESPUESTA", "test", "cursoTest");

        when(topicoService.registrarTopico(any())).thenReturn(datosDetalle);

        var response = mockMvc.perform(post("/api/v1/topicos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonRegistro.write(datosRegistro).getJson())
        ).andReturn().getResponse();

        Assertions.assertThat(response.getStatus()).isEqualTo(201);

        var jsonEsperado = jsonDetalle.write(datosDetalle).getJson();
        Assertions.assertThat(response.getContentAsString()).isEqualTo(jsonEsperado);
    }

    @Test
    @DisplayName("Debería devolver http 400 cuando el tópico ya existe")
    @WithMockUser
    void registrar_escenario3() throws Exception {
        var datosRegistro = new DatosRegistroTopico("Título duplicado", "Mensaje duplicado", 1L);

        when(topicoService.registrarTopico(any())).thenThrow(new ValidacionException("Tópico duplicado"));

        var response = mockMvc.perform(post("/api/v1/topicos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonRegistro.write(datosRegistro).getJson())
        ).andReturn().getResponse();

        Assertions.assertThat(response.getStatus()).isEqualTo(400);
    }

    @Test
    @DisplayName("Debería devolver http 403 cuando el usuario no está autenticado")
    void registrar_escenario4() throws Exception {
        var response = mockMvc.perform(post("/api/v1/topicos")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{}")
        ).andReturn().getResponse();

        Assertions.assertThat(response.getStatus()).isEqualTo(403);
    }

    @Test
    @DisplayName("Debería devolver http 404 cuando el tópico no existe")
    @WithMockUser
    void detalle_escenario1() throws Exception {
        Long idInexistente = 999999L;

        when(topicoService.obtenerTopicoPorId(idInexistente)).thenThrow(new EntityNotFoundException());

        var response = mockMvc.perform(get("/api/v1/topicos/" + idInexistente))
                .andReturn().getResponse();

        Assertions.assertThat(response.getStatus()).isEqualTo(404);
    }

    @Test
    @DisplayName("Debería devolver http 200 y lista de tópicos (Paginación)")
    @WithMockUser
    void listar_escenario1() throws Exception {
        var response = mockMvc.perform(get("/api/v1/topicos")
                .param("page", "0")
                .param("size", "10")
                .param("sort", "fechaCreacion,desc")
        ).andReturn().getResponse();

        Assertions.assertThat(response.getStatus()).isEqualTo(200);
        Assertions.assertThat(response.getContentAsString()).isNotNull();
    }
}