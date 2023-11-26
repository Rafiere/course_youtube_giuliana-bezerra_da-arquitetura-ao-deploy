package dev.piccodev.daarquiteturaaodeploy.web.service;

import dev.piccodev.daarquiteturaaodeploy.domain.Transacao;
import dev.piccodev.daarquiteturaaodeploy.repository.TransacaoRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.sql.Time;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

//When we use the Mockito, we should use the Mockito runner for the Mockito inject the mocks.
@ExtendWith(MockitoExtension.class)
public class TransacaoServiceTest {

    //The @InjectMocks will inject the mocks created with @Mock to the service class.
    @InjectMocks
    private TransacaoService transacaoService;

    //The mock should mock de dependencies of the service class.
    @Mock
    private TransacaoRepository transacaoRepository;

    @Test
    public void testGetTotaisTransacoesByNomeDaLoja(){

        //Arrange
        final String lojaA = "Loja A", lojaB = "Loja B";

        var transacao1 = new Transacao(1L, 1, LocalDate.ofInstant(Instant.ofEpochMilli(System.currentTimeMillis()), ZoneId.systemDefault()),
                BigDecimal.valueOf(100), 123456789L, "1234-5678-9012-3456", LocalTime.ofInstant(Instant.ofEpochMilli(System.currentTimeMillis()), ZoneId.systemDefault()),
                "Dono da loja A", lojaA);

        var transacao2 = new Transacao(1L, 1, LocalDate.ofInstant(Instant.ofEpochMilli(System.currentTimeMillis()), ZoneId.systemDefault()),
                BigDecimal.valueOf(50), 123456780L, "1234-5678-9012-4444", LocalTime.ofInstant(Instant.ofEpochMilli(System.currentTimeMillis()), ZoneId.systemDefault()),
                "Dono da loja B", lojaB);

        var transacao3 = new Transacao(1L, 1, LocalDate.ofInstant(Instant.ofEpochMilli(System.currentTimeMillis()), ZoneId.systemDefault()),
                BigDecimal.valueOf(75), 123456789L, "1234-5678-9012-3456", LocalTime.ofInstant(Instant.ofEpochMilli(System.currentTimeMillis()), ZoneId.systemDefault()),
                "Dono da loja A", lojaA);

        //Act

        //The stub will simulate the return of the findAllByOrderByNomeDaLojaAscIdDesc() method.
        //We do this with the when().thenReturn() methods.

        Mockito.when(transacaoRepository.findAllByOrderByNomeDaLojaAscIdDesc())
                .thenReturn(List.of(transacao1, transacao2, transacao3));

        var reports = transacaoService.getTotaisTransacoesPorNomeDaLoja();

        //Assert

        assertEquals(2, reports.size());

        reports.forEach(report -> {
            if (report.nomeDaLoja().equals(lojaA)) {
                assertEquals(BigDecimal.valueOf(175), report.totalDeTransacoesDaLoja());
            } else {
                assertEquals(BigDecimal.valueOf(50), report.totalDeTransacoesDaLoja());
            }
        });
    }
}