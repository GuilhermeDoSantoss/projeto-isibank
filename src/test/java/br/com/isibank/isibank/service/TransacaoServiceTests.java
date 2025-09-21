package br.com.isibank.isibank.service;

import br.com.isibank.isibank.dto.ExtratoDTO;
import br.com.isibank.isibank.dto.PagamentoDTO;
import br.com.isibank.isibank.dto.TransferenciaDTO;
import br.com.isibank.isibank.exceptions.InvalidAccountException;
import br.com.isibank.isibank.exceptions.InvalidDateIntervalException;
import br.com.isibank.isibank.exceptions.NotEnoughBalanceException;
import br.com.isibank.isibank.model.Transacao;
import br.com.isibank.isibank.service.transacao.ITransacaoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest

public class TransacaoServiceTests {

    @Mock
    private ITransacaoService service;

    private PagamentoDTO pagamentoValido;
    private PagamentoDTO pgtoSaldoInsuficiente;
    private PagamentoDTO pgtoContaInvalida;

    private TransferenciaDTO transferenciaValida;
    private TransferenciaDTO transfOrigemInvalida;
    private TransferenciaDTO transfDestinoInvalida;
    private TransferenciaDTO transfSaldoInsuficiente;

    private ExtratoDTO extratoValido;
    private ExtratoDTO extratoContaInvalida;
    private ExtratoDTO extratoDatasInvalidas;

    @BeforeEach
    public void setup(){
        pagamentoValido = new PagamentoDTO(10, LocalDateTime.parse("2025-09-09T23:00:00"),
                "1234567", "Boleto", 100.00);

        pgtoSaldoInsuficiente = new PagamentoDTO(20, LocalDateTime.parse("2025-09-09T23:05:00"),
                "1234567", "Boleto", 1000000.00);

        pgtoContaInvalida = new PagamentoDTO(100,LocalDateTime.parse("2025-90-09T23:10:00"),
                "1234567", "Boleto", 100.00);

        transferenciaValida = new TransferenciaDTO(10, 20, LocalDateTime.parse("2025-90-09T23:10:00"),150.00, "Manda o PIX");
        transfOrigemInvalida = new TransferenciaDTO(100, 20, LocalDateTime.parse("2025-90-09T23:10:00"),160.00, "Manda o PIX");
        transfDestinoInvalida = new TransferenciaDTO(10, 100, LocalDateTime.parse("2025-90-09T23:10:00"),170.00, "Manda o PIX");
        transfSaldoInsuficiente = new TransferenciaDTO(10, 20, LocalDateTime.parse("2025-90-09T23:10:00"),180.00, "Manda o PIX");

        extratoValido = new ExtratoDTO(10, LocalDateTime.parse("2024-01-01T00:00"), LocalDateTime.parse("2024-02-02T00:00"));
        extratoContaInvalida = new ExtratoDTO(100, LocalDateTime.parse("2024-01-01T00:00"), LocalDateTime.parse("2024-02-02T00:00"));
        extratoDatasInvalidas = new ExtratoDTO(10, LocalDateTime.parse("2024-02-02T00:00"), LocalDateTime.parse("2024-01-01T00:00"));

        Mockito.when(service.efetuarPagamento(pagamentoValido)).thenReturn(true);
        // Se o pagamento for válido, o mock devolve true (sucesso).

        Mockito.when(service.efetuarPagamento(pgtoSaldoInsuficiente)).thenThrow(NotEnoughBalanceException.class);
        // Se o pagamento tiver saldo insuficiente, o mock lança a exception NotEnoughBalanceException.

        Mockito.when(service.efetuarPagamento(pgtoContaInvalida)).thenThrow(InvalidAccountException.class);
        // Se a conta for inválida, o mock lança a exception InvalidAccountException

        Mockito.when(service.efetuarTransferencia(transferenciaValida)).thenReturn(true);
        Mockito.when(service.efetuarTransferencia(transfOrigemInvalida)).thenThrow(InvalidAccountException.class);
        Mockito.when(service.efetuarTransferencia(transfDestinoInvalida)).thenThrow(InvalidAccountException.class);
        Mockito.when(service.efetuarTransferencia(transfSaldoInsuficiente)).thenThrow(NotEnoughBalanceException.class);

        Mockito.when(service.getExtrato(extratoValido)).thenReturn(new ArrayList<Transacao>());
        Mockito.when(service.getExtrato(extratoContaInvalida)).thenThrow(InvalidAccountException.class);
        Mockito.when(service.getExtrato(extratoDatasInvalidas)).thenThrow(InvalidDateIntervalException.class);

    }

    @Test
    public void shouldEffectiveDoPayment(){
        assertTrue(service.efetuarPagamento(pagamentoValido));
    }

    @Test
    public void shouldCheckInsufficientBallance(){
        assertThrows(NotEnoughBalanceException.class, () -> {
            service.efetuarPagamento(pgtoSaldoInsuficiente);
        });
    }

    @Test
    public void shouldCheckInvalidAccount(){
        assertThrows(InvalidAccountException.class, () -> {
            service.efetuarPagamento(pgtoContaInvalida);
        });
    }

    //assertTrue → garante resultado esperado (boolean true).
    //assertThrows → garante que um erro específico foi disparado.
    //Lambda () -> ... → encapsula o código que deve lançar a exceção.

    @Test
    public void shouldEffectiveTransfer(){
        assertTrue(service.efetuarTransferencia(transferenciaValida));
    }

    @Test
    public void shouldCheckInvalidDestinationAccount(){
        assertThrows(InvalidAccountException.class, () -> {
            service.efetuarTransferencia(transfDestinoInvalida);
        });
    }

    @Test
    public void shouldCheckAccountBallanceInsuficient(){
        assertThrows(NotEnoughBalanceException.class, () -> {
               service.efetuarTransferencia(transfSaldoInsuficiente);
               });
    }

    @Test
    public void shouldRetrieveExtrato(){
        assertNotNull(service.getExtrato(extratoValido));
    }

    @Test
    public void shouldCheckInvalidAccountNoExtrato(){
        assertThrows(InvalidAccountException.class, () -> {
            service.getExtrato(extratoContaInvalida);
        });
    }

    @Test
    public void shouldCheckDateInterval(){
        assertThrows(InvalidDateIntervalException.class, () -> {
            service.getExtrato(extratoDatasInvalidas);
        });
    }

}