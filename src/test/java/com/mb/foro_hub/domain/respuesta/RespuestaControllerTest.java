package com.mb.foro_hub.domain.respuesta;

import com.mb.foro_hub.domain.respuesta.dto.DatosDetalleRespuesta;
import com.mb.foro_hub.domain.respuesta.dto.DatosRegistroRespuesta;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
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
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
@ActiveProfiles("test")
class RespuestaControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private JacksonTester<DatosRegistroRespuesta> jsonRegistro;
    @MockitoBean
    private RespuestaService respuestaService;

    @Test
    @DisplayName("Debería devolver http 201 cuando se registra una respuesta en un tópico existente")
    @WithMockUser
    void registrar_escenario1() throws Exception {
        Long idTopico = 1L;
        var datosRegistro = new DatosRegistroRespuesta(1L,"Solución propuesta al problema");

        var datosDetalle = new DatosDetalleRespuesta(1L, "Solución propuesta...", LocalDateTime.now(), "Autor",idTopico, false);
        when(respuestaService.crearRespuesta(eq(idTopico), any(DatosRegistroRespuesta.class))).thenReturn(datosDetalle);

        var response = mockMvc.perform(post("/api/v1/topicos/{id}/respuestas", idTopico)
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonRegistro.write(datosRegistro).getJson())
        ).andReturn().getResponse();

        Assertions.assertThat(response.getStatus()).isEqualTo(201);
    }
}