package dev.piccodev.daarquiteturaaodeploy.batch.reader;

import dev.piccodev.daarquiteturaaodeploy.domain.TransacaoCNAB;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.transform.Range;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Component;

//The CNAB file is a fixed-length file. Each line has a fixed length and a specific layout.

@Component
public class CNABItemReader {

    //Spring Batch has a FlatFileItemReader that can be used to read a fixed-length file.

    @Bean
    public FlatFileItemReader<TransacaoCNAB> reader(){

            return new FlatFileItemReaderBuilder<TransacaoCNAB>()
                    .name("CNABItemReader")
                    .resource(new FileSystemResource("/home/rafael/Desktop/codes/courses/youtube/giuliana-bezerra/daarquiteturaaodeploy/src/main/resources/files/CNAB.txt"))
                    .fixedLength()
                    //The "columns" method will define the length of each column. Each column represents a field of the CNAB file layout.
                    .columns(new Range(1, 1), //The first column has a length of 1 character.
                            new Range(2, 9), //The second column has a length of 8 characters.
                            new Range(10, 19),
                            new Range(20, 30),
                            new Range(31, 42),
                            new Range(43, 48),
                            new Range(49, 62),
                            new Range(63, 80))
                    .names("tipo", //This method will map the columns to the attributes of the "TransacaoCNAB" class. These names should be equal to the names of the attributes of the "TransacaoCNAB" class.
                            "data",
                            "valor",
                            "cpf",
                            "cartao",
                            "hora",
                            "donoDaLoja",
                            "nomeDaLoja") //In this way, the first character of the file will be mapped to the "tipo" attribute, the second to the "data" attribute, and so on.
                    .targetType(TransacaoCNAB.class) //The "targetType" method will define the type of the object that will be returned by the reader.
                    .build();
    }
}
