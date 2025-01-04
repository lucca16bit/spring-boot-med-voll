package com.med.voll.api.dto.medico;

import com.med.voll.api.domain.medico.Especialidade;
import com.med.voll.api.domain.medico.Medico;

public record DadosListagemMedicoDto(
        Long id,
        String nome,
        String email,
        String crm,
        Especialidade especialidade) {

    public DadosListagemMedicoDto(Medico medico) {
        this(
                medico.getId(),
                medico.getNome(),
                medico.getEmail(),
                medico.getCrm(),
                medico.getEspecialidade());
    }
}