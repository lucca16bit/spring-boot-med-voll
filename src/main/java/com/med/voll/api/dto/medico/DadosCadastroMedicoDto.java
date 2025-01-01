package com.med.voll.api.dto.medico;

import com.med.voll.api.dto.endereco.DadosEnderecoDto;
import com.med.voll.api.models.Especialidade;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record DadosCadastroMedicoDto(
        @NotBlank // verifica se o campo não está vazio
        String nome,

        @NotBlank // NotBlank é somente para campus strings
        @Email
        String email,

        @NotBlank
        String telefone,

        @NotBlank
        @Pattern(regexp = "\\d{4,6}") // expressao regular porque ele é um número de 4 a 6 dígitos
        String crm,

        @NotNull
        Especialidade especialidade,

        @NotNull
        @Valid // para validar outro objeto que tambem terão validações
        DadosEnderecoDto endereco) {
}
