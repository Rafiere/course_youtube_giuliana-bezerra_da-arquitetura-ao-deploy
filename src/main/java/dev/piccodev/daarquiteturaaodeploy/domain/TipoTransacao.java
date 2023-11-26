package dev.piccodev.daarquiteturaaodeploy.domain;

import java.math.BigDecimal;

public enum TipoTransacao {

    DEBITO(1), BOLETO(2), FINANCIAMENTO(3), CREDITO(4),
    RECEBIMENTO_EMPRESTIMO(5), VENDAS(6), RECEBIMENTO_TED(7),
    RECEBIMENTO_DOC(8), ALUGUEL(9);

    private int tipo;

    private TipoTransacao(int tipo) {
        this.tipo = tipo;
    }

    //Below, we are applying the Strategy design pattern.
    //According to the type of transaction, we return a signal.
    //Instead of use an "if" in each service that manipulate a transaction, we
    //centralize the logic here.
    public BigDecimal getSinal(){
        return switch (tipo) {
            case 1, 3, 5, 6, 8, 9 -> new BigDecimal(1);
            case 2, 4, 7 -> new BigDecimal(-1);
            default -> BigDecimal.ZERO;
        };
    }

    //According to the type of transaction, we return a type.
    public static TipoTransacao findByTipo(int tipo){

        for (TipoTransacao tipoTransacao : TipoTransacao.values()) {
            if(tipoTransacao.tipo == tipo){
                return tipoTransacao;
            }
        }

        throw new IllegalArgumentException("Tipo de transação inválido");
    }
}
