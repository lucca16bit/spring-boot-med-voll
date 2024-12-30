package com.med.voll.api.controller;

import com.med.voll.api.dto.medico.DadosCadastroMedicoDto;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/medicos")
public class MedicoController {

    @PostMapping
    public void cadastrar(@RequestBody DadosCadastroMedicoDto dados) { // o requestbody vai salvar o corpo da json nesta string
        System.out.println(dados);
    }
}
