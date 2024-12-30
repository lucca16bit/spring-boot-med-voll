package com.med.voll.api.dto.medico;

import com.med.voll.api.dto.endereco.DadosEnderecoDto;

public record DadosCadastroMedicoDto(
        String nome,
        String email,
        String crm,
        Especialidade especialidade,
        DadosEnderecoDto endereco) {
}
