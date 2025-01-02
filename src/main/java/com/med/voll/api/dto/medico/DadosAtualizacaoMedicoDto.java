package com.med.voll.api.dto.medico;

import com.med.voll.api.dto.endereco.DadosEnderecoDto;
import jakarta.validation.constraints.NotNull;

public record DadosAtualizacaoMedicoDto(
        @NotNull
        Long id,
        String nome,
        String telefone,
        DadosEnderecoDto endereco) {
}
