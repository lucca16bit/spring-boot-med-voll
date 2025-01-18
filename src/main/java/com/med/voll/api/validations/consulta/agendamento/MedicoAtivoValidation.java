package com.med.voll.api.validations.consulta.agendamento;

import com.med.voll.api.dto.consulta.DadosAgendamentoConsultaDto;
import com.med.voll.api.exception.ValidacaoException;
import com.med.voll.api.repository.MedicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MedicoAtivoValidation implements AgendamentoConsultasValidation {

    @Autowired
    private MedicoRepository repository;

    public void validar (DadosAgendamentoConsultaDto dados) {

        // escolha do medico opcional
        if (dados.idMedico() == null) {
            return;
        }

        var medicoEstaAtivo = repository.findAtivoById(dados.idMedico());
        if (!medicoEstaAtivo) {
            throw new ValidacaoException("Consulta não pode ser agendada com o médico");
        }
    }
}
