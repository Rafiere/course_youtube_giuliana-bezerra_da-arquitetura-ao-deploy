package dev.piccodev.daarquiteturaaodeploy.web.service;

import dev.piccodev.daarquiteturaaodeploy.domain.TipoTransacao;
import dev.piccodev.daarquiteturaaodeploy.repository.TransacaoRepository;
import dev.piccodev.daarquiteturaaodeploy.web.entity.TransacaoReport;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

@Service
public class TransacaoService {

    private final TransacaoRepository transacaoRepository;

    public TransacaoService(TransacaoRepository transacaoRepository){
        this.transacaoRepository = transacaoRepository;
    }

    public List<TransacaoReport> getTotaisTransacoesPorNomeDaLoja(){

        var transacoes = transacaoRepository.findAllByOrderByNomeDaLojaAscIdDesc();

        //O LinkedHashMap mantém a ordem de inserção dos elementos
        var reportMap = new LinkedHashMap<String, TransacaoReport>();

        transacoes.forEach(transacao -> {
            String nomeDaLoja = transacao.nomeDaLoja();
            BigDecimal valor = transacao.valor().multiply(TipoTransacao.findByTipo(transacao.tipo()).getSinal());


            reportMap.compute(nomeDaLoja, (key, existingReport) -> {
                var report = (existingReport != null) ? existingReport :
                        new TransacaoReport(key, BigDecimal.ZERO, new ArrayList<>());

                return report.addTotal(valor).addTransacao(transacao.withValor(valor));
            });
        });

        return new ArrayList<>(reportMap.values());
    }
}