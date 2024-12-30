package com.med.voll.api.controller;

import com.med.voll.api.dto.medico.DadosCadastroMedicoDto;
import com.med.voll.api.models.Medico;
import com.med.voll.api.repository.MedicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/medicos")
public class MedicoController {

    @Autowired
    private MedicoRepository repository;

    @PostMapping
    @Transactional
    public void cadastrar(@RequestBody DadosCadastroMedicoDto dados) { // o requestbody vai salvar o corpo da json nesta variavel
        repository.save(new Medico(dados));
    }
}
