package com.med.voll.api.controller;

import com.med.voll.api.dto.consulta.DadosAgendamentoConsultaDto;
import com.med.voll.api.dto.consulta.DadosCancelamentoConsultaDto;
import com.med.voll.api.services.ConsultaService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/consultas")
@SecurityRequirement(name = "bearer-key")
public class ConsultaController {

    @Autowired
    private ConsultaService consultaService;

    @PostMapping
    @Transactional
    public ResponseEntity agendar (@RequestBody @Valid DadosAgendamentoConsultaDto dados) {
        var dto = consultaService.agendar(dados);

        return ResponseEntity.ok(dto);
    }

    @DeleteMapping
    @Transactional
    public ResponseEntity cancelar(@RequestBody @Valid DadosCancelamentoConsultaDto dados) {
        consultaService.cancelar(dados);
        return ResponseEntity.noContent().build();
    }
}
