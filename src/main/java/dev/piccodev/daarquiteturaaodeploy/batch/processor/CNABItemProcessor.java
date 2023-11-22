package dev.piccodev.daarquiteturaaodeploy.batch.processor;

import dev.piccodev.daarquiteturaaodeploy.domain.Transacao;
import dev.piccodev.daarquiteturaaodeploy.domain.TransacaoCNAB;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class CNABItemProcessor {

    //This processor will receive a "TransacaoCNAB" object and will return a "Transacao" object.

    @Bean
    public ItemProcessor<TransacaoCNAB, Transacao> processor(){

        //The "ItemProcessor" is a functional interface, so we can use a lambda expression to implement it.

        //For each item read, the "process" method will be called.

        //The wither pattern is used to work with immutable objects. It is a way to create a new object with the same values of the original object, but with some values changed.

        return item -> {
            var transacao = new Transacao(null,
                    item.tipo(),
                          null,
                          null,
                          item.cpf(),
                          item.cartao(),
                          null,
                          item.donoDaLoja().trim(),
                          item.nomeDaLoja().trim())
                    .withValor(item.valor().divide(BigDecimal.valueOf(100)))
                    .withData(item.data())
                    .withHora(item.hora());

            return transacao;
        };

    }
}