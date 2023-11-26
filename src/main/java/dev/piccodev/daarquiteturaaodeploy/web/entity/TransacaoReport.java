package dev.piccodev.daarquiteturaaodeploy.web.entity;

import dev.piccodev.daarquiteturaaodeploy.domain.Transacao;

import java.math.BigDecimal;
import java.util.List;

public record TransacaoReport(String nomeDaLoja,
                              BigDecimal totalDeTransacoesDaLoja,
                              List<Transacao> transacoesDaLoja) {

    public TransacaoReport addTotal(BigDecimal valor){

        return new TransacaoReport(nomeDaLoja, totalDeTransacoesDaLoja.add(valor), transacoesDaLoja);
    }

    public TransacaoReport addTransacao(Transacao transacao){

        transacoesDaLoja.add(transacao);

        return new TransacaoReport(nomeDaLoja, totalDeTransacoesDaLoja, transacoesDaLoja);
    }
}
