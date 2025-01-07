package com.med.voll.api.controller;

import com.med.voll.api.domain.usuario.Usuario;
import com.med.voll.api.dto.security.DadosAutenticacaoDto;
import com.med.voll.api.dto.security.DadosTokenJWTDto;
import com.med.voll.api.services.TokenService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
public class AutenticacaoController {

    @Autowired
    private AuthenticationManager manager;

    @Autowired
    private TokenService tokenService;

    @PostMapping
    public ResponseEntity efetuarLogin(@RequestBody @Valid DadosAutenticacaoDto dados) {
        var authenticationTokentoken = new UsernamePasswordAuthenticationToken(dados.login(), dados.senha()); // spring tem o dto dele pois ele não recebe o nosso dto
        var authentication = manager.authenticate(authenticationTokentoken);

        var tokenJWT = tokenService.gerarToken((Usuario) authentication.getPrincipal());

        return ResponseEntity.ok(new DadosTokenJWTDto(tokenJWT));
    }
}
