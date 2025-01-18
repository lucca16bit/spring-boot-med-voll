package com.med.voll.api.dto.consulta;

import com.med.voll.api.domain.consulta.MotivoCancelamento;
import jakarta.validation.constraints.NotNull;

public record DadosCancelamentoConsultaDto(
        @NotNull
        Long idConsulta,

        @NotNull
        MotivoCancelamento motivo
) {
}
