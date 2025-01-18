package com.med.voll.api.validations.consulta.cancelamento;

import com.med.voll.api.dto.consulta.DadosCancelamentoConsultaDto;
import com.med.voll.api.exception.ValidacaoException;
import com.med.voll.api.repository.ConsultaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDateTime;

@Component("ValidadorHorarioAntecedenciaCancelamento")
public class HorarioAntecedenciaValidation implements CancelamentoDeConsultaValidation{

    @Autowired
    private ConsultaRepository repository;

    @Override
    public void validar(DadosCancelamentoConsultaDto dados) {
        var consulta = repository.getReferenceById(dados.idConsulta());
        var agora = LocalDateTime.now();

        var diferencaHoras = Duration.between(agora, consulta.getData()).toHours();

        if (diferencaHoras < 24) {
            throw new ValidacaoException("Consulta só pode ser cancelada com antecedência mínima de 24hrs");
        }
    }
}
