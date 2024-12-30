package com.med.voll.api.dto.endereco;

public record DadosEnderecoDto(
        String logradouro,
        String bairro,
        String cep,
        String cidade,
        String uf,
        String numero,
        String complemento) {
}
