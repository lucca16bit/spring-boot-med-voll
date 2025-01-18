package com.med.voll.api.services;

import com.med.voll.api.domain.consulta.Consulta;
import com.med.voll.api.domain.medico.Medico;
import com.med.voll.api.dto.consulta.DadosAgendamentoConsultaDto;
import com.med.voll.api.dto.consulta.DadosCancelamentoConsultaDto;
import com.med.voll.api.dto.consulta.DadosDetalhamentoConsultaDto;
import com.med.voll.api.exception.ValidacaoException;
import com.med.voll.api.repository.ConsultaRepository;
import com.med.voll.api.repository.MedicoRepository;
import com.med.voll.api.repository.PacienteRepository;
import com.med.voll.api.validations.consulta.agendamento.AgendamentoConsultasValidation;
import com.med.voll.api.validations.consulta.cancelamento.CancelamentoDeConsultaValidation;
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

    @Autowired
    private List<CancelamentoDeConsultaValidation> cancelamentoValidations;

    public DadosDetalhamentoConsultaDto agendar(DadosAgendamentoConsultaDto dados) {
        if (!pacienteRepository.existsById(dados.idPaciente())) {
            throw new ValidacaoException("Id do paciente informado não existe.");
        }

        if (dados.idMedico() != null && !medicoRepository.existsById(dados.idMedico())) {
            throw new ValidacaoException("Id do médico informado não existe.");
        }

        validations.forEach(v -> v.validar(dados));

        var paciente = pacienteRepository.getReferenceById(dados.idPaciente());
        var medico = escolherMedico(dados);
        if (medico == null) {
            throw new ValidacaoException("Não existe médico disponível nessa data");
        }

        var consulta = new Consulta(null, medico, paciente, dados.data());

        repository.save(consulta);

        return new DadosDetalhamentoConsultaDto(consulta);
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

    public void cancelar(DadosCancelamentoConsultaDto dados) {
        if (!repository.existsById(dados.idConsulta())) {
            throw new ValidacaoException("Id da consulta informado não existe!");
        }
        cancelamentoValidations.forEach(v -> v.validar(dados));

        var consulta = repository.getReferenceById(dados.idConsulta());
        consulta.cancelar(dados.motivo());
    }
}
