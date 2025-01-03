package com.med.voll.api.dto.medico;

import com.med.voll.api.models.Endereco;
import com.med.voll.api.models.Especialidade;
import com.med.voll.api.models.Medico;

public record DadosDetalhadoMedicoDto(
        Long id,
        String nome,
        String email,
        String crm,
        String telefone,
        Especialidade especialidade,
        Endereco endereco) {

    public DadosDetalhadoMedicoDto(Medico medico) {
        this(
                medico.getId(),
                medico.getNome(),
                medico.getEmail(),
                medico.getCrm(),
                medico.getTelefone(),
                medico.getEspecialidade(),
                medico.getEndereco()
        );
    }
}
