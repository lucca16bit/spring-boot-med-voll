package com.med.voll.api.dto.medico;

import com.med.voll.api.models.Especialidade;
import com.med.voll.api.models.Medico;

public record DadosListagemMedicoDto(
        String nome,
        String email,
        String crm,
        Especialidade especialidade) {

    public DadosListagemMedicoDto(Medico medico) {
        this(medico.getNome(),
                medico.getEmail(),
                medico.getCrm(),
                medico.getEspecialidade());
    }
}