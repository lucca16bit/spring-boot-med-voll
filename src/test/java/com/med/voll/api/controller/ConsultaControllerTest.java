package com.med.voll.api.controller;

import com.med.voll.api.domain.medico.Especialidade;
import com.med.voll.api.dto.consulta.DadosAgendamentoConsultaDto;
import com.med.voll.api.dto.consulta.DadosDetalhamentoConsultaDto;
import com.med.voll.api.services.ConsultaService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
public class ConsultaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private JacksonTester<DadosAgendamentoConsultaDto> dtoAgendamentoJson;

    @Autowired
    private JacksonTester<DadosDetalhamentoConsultaDto> dtoDetalhamentoJson;

    @MockitoBean
    private ConsultaService consultaService;

    @Test
    @DisplayName("Deveria devolver codigo http 400 quando as info estao invalidas")
    @WithMockUser
    void agendar_cenario1() throws Exception {
        // given ou arrange
        var response = mockMvc.perform(post("/consultas"))
                .andReturn().getResponse();

        // then ou assert
        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    @DisplayName("Deveria devolver codigo http 200 quando as info estao validas")
    @WithMockUser
    void agendar_cenario2() throws Exception {
        var data = LocalDateTime.now().plusHours(1);
        var especialidade = Especialidade.CARDIOLOGIA;

        var dadosDetalhamento = new DadosDetalhamentoConsultaDto(null, 2l, 5l, data);
        when(consultaService.agendar(any())).thenReturn(dadosDetalhamento);

        // given ou arrange
        var response = mockMvc.
                perform(
                        post("/consultas")
                                .contentType(MediaType.APPLICATION_JSON) // cabeçalho HTTP
                                .content(dtoAgendamentoJson.write(
                                        new DadosAgendamentoConsultaDto(2l, 5l, data, especialidade)
                                ).getJson())
                )
                .andReturn().getResponse();

        // then ou assert
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());

        var jsonEsperado = dtoDetalhamentoJson.write(dadosDetalhamento).getJson();

        assertThat(response.getContentAsString()).isEqualTo(jsonEsperado);
    }
}
