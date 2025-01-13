package com.med.voll.api.validations.consulta;

import com.med.voll.api.dto.consulta.DadosAgendamentoConsultaDto;
import com.med.voll.api.exception.ValidacaoException;
import com.med.voll.api.repository.PacienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PacienteAtivoValidation implements AgendamentoConsultasValidation {

    @Autowired
    private PacienteRepository repository;

    public void validar (DadosAgendamentoConsultaDto dados) {
        var pacienteEstaAtivo = repository.findAtivoById(dados.idPaciente());
        if (!pacienteEstaAtivo) {
            throw new ValidacaoException("Consulta não pode ser agendada com paciente excluído");
        }
    }
}
