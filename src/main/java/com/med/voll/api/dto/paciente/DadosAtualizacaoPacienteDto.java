package com.med.voll.api.dto.paciente;

import com.med.voll.api.dto.endereco.DadosEnderecoDto;
import jakarta.validation.constraints.NotNull;

public record DadosAtualizacaoPacienteDto(
        @NotNull
        Long id,
        String nome,
        String telefone,
        DadosEnderecoDto endereco) {
}
