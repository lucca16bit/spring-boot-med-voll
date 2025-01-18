package com.med.voll.api.validations.consulta.agendamento;

import com.med.voll.api.dto.consulta.DadosAgendamentoConsultaDto;

public interface AgendamentoConsultasValidation {

    void validar(DadosAgendamentoConsultaDto dados);
}
