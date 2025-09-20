package br.com.isibank.isibank.service;

import br.com.isibank.isibank.dto.PagamentoDTO;
import br.com.isibank.isibank.dto.TransferenciaDTO;
import br.com.isibank.isibank.exceptions.InvalidAccountException;
import br.com.isibank.isibank.exceptions.NotEnoughBalanceException;
import br.com.isibank.isibank.service.transacao.ITransacaoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

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

    @BeforeEach
    public void setup(){
        pagamentoValido = new PagamentoDTO(1, LocalDateTime.parse("2025-09-09T23:00:00"),
                "1234567", "Boleto", 100.00);

        pgtoSaldoInsuficiente = new PagamentoDTO(2, LocalDateTime.parse("2025-09-09T23:05:00"),
                "1234567", "Boleto", 100.00);

        pgtoContaInvalida = new PagamentoDTO(100,LocalDateTime.parse("2025-90-09T23:10:00"),
                "1234567", "Boleto", 100.00);

        transferenciaValida = new TransferenciaDTO(1, 2, LocalDateTime.parse("2025-90-09T23:10:00"),150.00, "Manda o PIX");
        transfOrigemInvalida = new TransferenciaDTO(200, 4, LocalDateTime.parse("2025-90-09T23:10:00"),160.00, "Manda o PIX");
        transfDestinoInvalida = new TransferenciaDTO(3, 6, LocalDateTime.parse("2025-90-09T23:10:00"),170.00, "Manda o PIX");
        transfSaldoInsuficiente = new TransferenciaDTO(4, 8, LocalDateTime.parse("2025-90-09T23:10:00"),180.00, "Manda o PIX");

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

}