package com.med.voll.api.dto.paciente;

import com.med.voll.api.domain.paciente.Paciente;

public record DadosListagemPacienteDto(Long id, String nome, String email, String cpf) {

    public DadosListagemPacienteDto(Paciente paciente) {
        this(
                paciente.getId(),
                paciente.getNome(),
                paciente.getEmail(),
                paciente.getCpf()
        );
    }
}
