package dev.piccodev.daarquiteturaaodeploy.web.controller;

import dev.piccodev.daarquiteturaaodeploy.web.entity.TransacaoReport;
import dev.piccodev.daarquiteturaaodeploy.web.service.TransacaoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/transacoes")
public class TransacaoController {

    private final TransacaoService transacaoService;

    public TransacaoController(TransacaoService transacaoService){
        this.transacaoService = transacaoService;
    }

    @GetMapping
    public ResponseEntity<List<TransacaoReport>> listAll(){

        var transacoes = transacaoService.getTotaisTransacoesPorNomeDaLoja();

        return ResponseEntity.status(HttpStatus.OK).body(transacoes);
    }
}
