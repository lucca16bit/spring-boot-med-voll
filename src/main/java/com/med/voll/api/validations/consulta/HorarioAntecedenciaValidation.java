package com.med.voll.api.validations.consulta;

import com.med.voll.api.dto.consulta.DadosAgendamentoConsultaDto;
import com.med.voll.api.exception.ValidacaoException;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDateTime;

@Component
public class HorarioAntecedenciaValidation implements AgendamentoConsultasValidation {

    public void validar(DadosAgendamentoConsultaDto dados) {
        var dataConsulta = dados.data();
        var agora = LocalDateTime.now();
        var diferencaEmMin = Duration.between(agora, dataConsulta).toMinutes();

        if (diferencaEmMin < 30) {
            throw new ValidacaoException("Consulta deve ser agendada com antecedência mínima de 30 minutos");
        }
    }
}
