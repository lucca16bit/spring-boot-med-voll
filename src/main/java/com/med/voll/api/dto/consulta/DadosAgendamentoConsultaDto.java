package com.med.voll.api.dto.consulta;

import com.med.voll.api.domain.medico.Especialidade;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record DadosAgendamentoConsultaDto(
        Long idMedico,

        @NotNull
        Long idPaciente,

        @NotNull
        @Future // annotation que só permite agendamento do horario atual para frente
        LocalDateTime data,

        Especialidade especialidade) {
}
