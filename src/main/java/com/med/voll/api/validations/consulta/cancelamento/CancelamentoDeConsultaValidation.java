package com.med.voll.api.validations.consulta.cancelamento;

import com.med.voll.api.dto.consulta.DadosCancelamentoConsultaDto;

public interface CancelamentoDeConsultaValidation {
    void validar(DadosCancelamentoConsultaDto dados);
}
