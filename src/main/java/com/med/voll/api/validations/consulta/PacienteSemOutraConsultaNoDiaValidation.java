package com.med.voll.api.validations.consulta;

import com.med.voll.api.dto.consulta.DadosAgendamentoConsultaDto;
import com.med.voll.api.exception.ValidacaoException;
import com.med.voll.api.repository.ConsultaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PacienteSemOutraConsultaNoDiaValidation implements AgendamentoConsultasValidation {

    @Autowired
    private ConsultaRepository repository;

    public void validar (DadosAgendamentoConsultaDto dados) {
        var primeiroHorario = dados.data().withHour(7);
        var ultimoHorario = dados.data().withHour(18);
        var pacientePossuiOutraConsultaNoDia = repository.existsByPacienteIdAndDataBetween(dados.idPaciente(), primeiroHorario, ultimoHorario);

        if (pacientePossuiOutraConsultaNoDia) {
            throw new ValidacaoException("Paciente já possui uma consulta agendada nesse dia");
        }
    }
}
