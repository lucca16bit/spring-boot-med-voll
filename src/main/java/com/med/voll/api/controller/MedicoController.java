package com.med.voll.api.controller;

import com.med.voll.api.dto.medico.DadosAtualizacaoMedicoDto;
import com.med.voll.api.dto.medico.DadosCadastroMedicoDto;
import com.med.voll.api.dto.medico.DadosListagemMedicoDto;
import com.med.voll.api.models.Medico;
import com.med.voll.api.repository.MedicoRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/medicos")
public class MedicoController {

    @Autowired
    private MedicoRepository repository;

    @PostMapping
    @Transactional
    public void cadastrar(@RequestBody @Valid DadosCadastroMedicoDto dados) { // o requestbody vai salvar o corpo da json nesta variavel
        repository.save(new Medico(dados));
    }

    @GetMapping
    public Page<DadosListagemMedicoDto> listar(@PageableDefault(size = 10, sort = {"nome"}) Pageable paginacao) {
        return repository.findAll(paginacao)
                .map(DadosListagemMedicoDto::new); // neste caso, a record vai precisar de um construtor para a conversao de mdico para listagemMedicoDto
    }

    @PutMapping
    @Transactional // o transactional faz o trabalho de update sozinho
    public void atualizar(@RequestBody @Valid DadosAtualizacaoMedicoDto dados) {
        var medico = repository.getReferenceById(dados.id());
        medico.atualizarInformacoes(dados);
    }

    @DeleteMapping("/{id}")
    @Transactional
    public void excluir(@PathVariable Long id) { // pathvariable referencia o id do parametro ao id da URL
        repository.deleteById(id);
    }
}
