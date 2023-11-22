package dev.piccodev.daarquiteturaaodeploy.batch.writter;

import dev.piccodev.daarquiteturaaodeploy.domain.Transacao;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;

@Component
public class CNABItemWriter {

    @Bean
    public JdbcBatchItemWriter<Transacao> writer(DataSource dataSource){

        //Em batch, queremos uma boa performance, por isso costumamos utilizar SQL puro.
        return new JdbcBatchItemWriterBuilder<Transacao>() //We will receive a "Transacao" object and will write it in the database.
                .dataSource(dataSource)
                .sql("""
                     INSERT INTO transacao (tipo, data, valor, cpf, cartao, hora, dono_da_loja, nome_da_loja)
                        VALUES (:tipo, :data, :valor, :cpf, :cartao, :hora, :donoDaLoja, :nomeDaLoja)
                     """)
                .beanMapped() //This method will map the attributes of the "Transacao" object to the parameters of the SQL query. Because of this, we used the ":attributeName" notation in the SQL query.
                .build();
    }
}