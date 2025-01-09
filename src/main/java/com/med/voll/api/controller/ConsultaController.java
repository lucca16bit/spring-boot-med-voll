package com.med.voll.api.controller;

import com.med.voll.api.dto.consulta.DadosAgendamentoConsultaDto;
import com.med.voll.api.dto.consulta.DadosDetalhamentoConsultaDto;
import com.med.voll.api.services.ConsultaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/consultas")
public class ConsultaController {

    @Autowired
    private ConsultaService consultaService;

    @PostMapping
    @Transactional
    public ResponseEntity agendar (@RequestBody @Valid DadosAgendamentoConsultaDto dados) {
        consultaService.agendar(dados);

        return ResponseEntity.ok(new DadosDetalhamentoConsultaDto(null, null, null, null));
    }
}
