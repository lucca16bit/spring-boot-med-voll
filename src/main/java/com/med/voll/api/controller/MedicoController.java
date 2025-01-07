package com.med.voll.api.controller;

import com.med.voll.api.dto.medico.DadosAtualizacaoMedicoDto;
import com.med.voll.api.dto.medico.DadosCadastroMedicoDto;
import com.med.voll.api.dto.medico.DadosDetalhadoMedicoDto;
import com.med.voll.api.dto.medico.DadosListagemMedicoDto;
import com.med.voll.api.domain.medico.Medico;
import com.med.voll.api.repository.MedicoRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/medicos")
public class MedicoController {

    @Autowired
    private MedicoRepository repository;

    // para o método cadastrar, deve devolver no corpo da resposta os dados do novo recurso/registro criado
    // e também, um cabeçalho do protocolo http (Location)
    @PostMapping
    @Transactional
    public ResponseEntity cadastrar(@RequestBody @Valid DadosCadastroMedicoDto dados, UriComponentsBuilder uriBuilder) { // o requestbody vai salvar o corpo da json nesta variavel
        var medico = new Medico(dados);
        repository.save(medico);

        var uri = uriBuilder.path("/medicos/{id}")
                .buildAndExpand(medico.getId()).toUri();

        // para devolver codigo http (201) com cabeçalho location e detalhes do recurso recem criado
        return ResponseEntity.created(uri).body(new DadosDetalhadoMedicoDto(medico));
    }

    @GetMapping
    public ResponseEntity<Page<DadosListagemMedicoDto>> listar(@PageableDefault(size = 10, sort = {"nome"}) Pageable paginacao) {

        var page = repository.findAllByAtivoTrue(paginacao)
                .map(DadosListagemMedicoDto::new); // neste caso, a record vai precisar de um construtor para a conversao de mdico para listagemMedicoDto

        // para devolver código http (200)
        return ResponseEntity.ok(page);

    }

    @PutMapping
    @Transactional // o transactional faz o trabalho de update sozinho
    public ResponseEntity atualizar(@RequestBody @Valid DadosAtualizacaoMedicoDto dados) {
        var medico = repository.getReferenceById(dados.id());
        medico.atualizarInformacoes(dados);

        // não é recomendado devolver e receber entidades JPA no controller, entao vamos de DTO

        return ResponseEntity.ok(new DadosDetalhadoMedicoDto(medico));
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity excluir(@PathVariable Long id) { // pathvariable referencia o id do parametro ao id da URL
        var medico = repository.getReferenceById(id);
        medico.excluir();

        // para devolver código http (204 no content)
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity detalhar(@PathVariable Long id) {
        var medico = repository.getReferenceById(id);
        return ResponseEntity.ok(new DadosDetalhadoMedicoDto(medico));
    }
}
