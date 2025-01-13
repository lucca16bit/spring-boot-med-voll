package com.med.voll.api.validations.consulta;

import com.med.voll.api.dto.consulta.DadosAgendamentoConsultaDto;
import com.med.voll.api.exception.ValidacaoException;
import org.springframework.stereotype.Component;

import java.time.DayOfWeek;

@Component
public class HorarioFuncionamentoValidation implements AgendamentoConsultasValidation {

    public void validar(DadosAgendamentoConsultaDto dados) {
        var dataConsulta = dados.data();

        // segunda a sabado das 7hrs às 19hrs
        var domingo = dataConsulta.getDayOfWeek().equals(DayOfWeek.SUNDAY);
        var antesDaAbertura = dataConsulta.getHour() < 7;
        var depoisDoEncerramento = dataConsulta.getHour() < 18;
        if (domingo || antesDaAbertura || depoisDoEncerramento) {
            throw new ValidacaoException("Consulta fora do horário de funcionamento da clínica.");
        }
    }
}
