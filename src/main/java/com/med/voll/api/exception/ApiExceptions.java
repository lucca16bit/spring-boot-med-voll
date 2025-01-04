package com.med.voll.api.exception;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ApiExceptions {

    // dessa forma, o spring sabe que se em qlqr controller do projeto for lançado uma exceção EntityNotFoundException, é para chamar o método tratarErro404()
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity tratarErro404() {
        return ResponseEntity.notFound().build();
    }

    // essa é a exceção que o bean validation lança quando há campo inválido
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity tratarErro400(MethodArgumentNotValidException e) {
        var erros = e.getFieldErrors();

        return ResponseEntity.badRequest().body(erros.stream()
                .map(DadosErroValidacao::new).toList());
    }

    // dessa forma, conseguimos converter a lista de FieldError para uma lista de DadosErroValidacao
    private record DadosErroValidacao(String campo, String mensagem) {
        public DadosErroValidacao(FieldError erro) {
            this(
                    erro.getField(),
                    erro.getDefaultMessage()
            );
        }
    }
}
