package com.med.voll.api.dto.endereco;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record DadosEnderecoDto(
        @NotBlank
        String logradouro,

        @NotBlank
        String bairro,

        @NotBlank
        @Pattern(regexp = "\\d{8}") // expressao regular para cep que são 8 digitos
        String cep,

        @NotBlank
        String cidade,

        @NotBlank
        String uf,

        // opcional
        String numero,

        // opcional
        String complemento) {
}
