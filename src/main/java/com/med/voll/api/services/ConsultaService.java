package com.med.voll.api.services;

import com.med.voll.api.domain.consulta.Consulta;
import com.med.voll.api.domain.medico.Medico;
import com.med.voll.api.dto.consulta.DadosAgendamentoConsultaDto;
import com.med.voll.api.exception.ValidacaoException;
import com.med.voll.api.repository.ConsultaRepository;
import com.med.voll.api.repository.MedicoRepository;
import com.med.voll.api.repository.PacienteRepository;
import com.med.voll.api.validations.consulta.AgendamentoConsultasValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ConsultaService {

    @Autowired
    private ConsultaRepository repository;

    @Autowired
    private MedicoRepository medicoRepository;

    @Autowired
    private PacienteRepository pacienteRepository;

    @Autowired
    private List<AgendamentoConsultasValidation> validations;

    public void agendar(DadosAgendamentoConsultaDto dados) {
        if (!pacienteRepository.existsById(dados.idPaciente())) {
            throw new ValidacaoException("Id do paciente informado não existe.");
        }

        if (dados.idMedico() != null && !medicoRepository.existsById(dados.idMedico())) {
            throw new ValidacaoException("Id do médico informado não existe.");
        }

        validations.forEach(v -> v.validar(dados));

        var paciente = pacienteRepository.getReferenceById(dados.idPaciente());
        var medico = escolherMedico(dados);
        var consulta = new Consulta(null, medico, paciente, dados.data());

        repository.save(consulta);
    }

    private Medico escolherMedico(DadosAgendamentoConsultaDto dados) {
        if (dados.idMedico() != null) {
            return medicoRepository.getReferenceById(dados.idMedico());
        }

        if (dados.especialidade() == null) {
            throw new ValidacaoException("Especialidade é obrigatória quando o médico não for escolhido");
        }

        return medicoRepository.escolherMedicoAleatorioLivreNaData(dados.especialidade(), dados.data());
    }
}
