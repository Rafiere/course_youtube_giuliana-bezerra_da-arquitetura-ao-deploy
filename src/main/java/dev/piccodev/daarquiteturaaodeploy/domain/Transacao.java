package dev.piccodev.daarquiteturaaodeploy.domain;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Date;

public record Transacao(Long id,
                        Integer tipo,
                        LocalDate data,
                        BigDecimal valor,
                        Long cpf,
                        String cartao,
                        LocalTime hora,
                        String donoDaLoja,
                        String nomeDaLoja) {

    //Above, we are using the witter pattern.

    //The "with" methods are used to create a new object with
    //the same values of the original object, but with some values changed.

    public Transacao withValor(BigDecimal valor){

        return new Transacao(this.id(),
                             this.tipo(),
                             this.data(),
                             valor,
                             this.cpf(),
                             this.cartao(),
                             this.hora(),
                             this.donoDaLoja(),
                             this.nomeDaLoja());
    }

    public Transacao withData(String data) throws ParseException {

        var dateFormat = new SimpleDateFormat("yyyyMMdd");
        var date = dateFormat.parse(data).toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
//        var date = dateFormat.parse(data);

        return new Transacao(this.id(), this.tipo(), date, this.valor(),
                this.cpf(), this.cartao(), this.hora(), this.donoDaLoja(), this.nomeDaLoja());
    }

    public Transacao withHora(String hora) throws ParseException {

        var hourFormat = new SimpleDateFormat("HHmmss");
        var hour = hourFormat.parse(hora).toInstant().atZone(ZoneId.systemDefault()).toLocalTime();

        return new Transacao(this.id(), this.tipo(), this.data(), this.valor(),
                this.cpf(), this.cartao(), hour, this.donoDaLoja(), this.nomeDaLoja());
    }
}
