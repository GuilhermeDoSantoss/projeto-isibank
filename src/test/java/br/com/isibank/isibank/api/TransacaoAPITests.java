package br.com.isibank.isibank.api;

import br.com.isibank.isibank.dto.ExtratoDTO;
import br.com.isibank.isibank.dto.PagamentoDTO;
import br.com.isibank.isibank.dto.TransferenciaDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDateTime;

@SpringBootTest
@AutoConfigureMockMvc
public class TransacaoAPITests {

    @Autowired
    private MockMvc mockMvc;

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
    public void setup() {
        pagamentoValido = new PagamentoDTO(10, LocalDateTime.parse("2025-09-09T23:00:00"),
                "1234567", "Boleto", 100.00);

        pgtoSaldoInsuficiente = new PagamentoDTO(20, LocalDateTime.parse("2025-09-09T23:05:00"),
                "1234567", "Boleto", 1000000.00);

        pgtoContaInvalida = new PagamentoDTO(100, LocalDateTime.parse("2025-90-09T23:10:00"),
                "1234567", "Boleto", 100.00);

        transferenciaValida = new TransferenciaDTO(10, 20, LocalDateTime.parse("2025-90-09T23:10:00"), 150.00, "Manda o PIX");
        transfOrigemInvalida = new TransferenciaDTO(100, 20, LocalDateTime.parse("2025-90-09T23:10:00"), 160.00, "Manda o PIX");
        transfDestinoInvalida = new TransferenciaDTO(10, 100, LocalDateTime.parse("2025-90-09T23:10:00"), 170.00, "Manda o PIX");
        transfSaldoInsuficiente = new TransferenciaDTO(10, 20, LocalDateTime.parse("2025-90-09T23:10:00"), 180.00, "Manda o PIX");

        extratoValido = new ExtratoDTO(10, LocalDateTime.parse("2024-01-01T00:00"), LocalDateTime.parse("2024-02-02T00:00"));
        extratoContaInvalida = new ExtratoDTO(100, LocalDateTime.parse("2024-01-01T00:00"), LocalDateTime.parse("2024-02-02T00:00"));
        extratoDatasInvalidas = new ExtratoDTO(10, LocalDateTime.parse("2024-02-02T00:00"), LocalDateTime.parse("2024-01-01T00:00"));
    }

        @Test
    public void shouldPerformPayment() throws Exception {
            ObjectMapper mapper = new ObjectMapper();
            mapper.registerModule(new JavaTimeModule());
            //registerModule(new JavaTimeModule()) → registra o módulo JavaTimeModule,
            //que ensina o Jackson a lidar com as classes de datas e horas do Java 8+ (LocalDate, LocalDateTime, ZonedDateTime, etc.)
            String str = mapper.writeValueAsString(pgtoSaldoInsuficiente);

        mockMvc.perform(MockMvcRequestBuilders.post("/pagamentos")
                .contentType("application/json")
                .content(str)).andExpect(MockMvcResultMatchers.status().is(400));
    }

    @Test
    public void shouldInvalidPaymentDueToBalance() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        //registerModule(new JavaTimeModule()) → registra o módulo JavaTimeModule,
        //que ensina o Jackson a lidar com as classes de datas e horas do Java 8+ (LocalDate, LocalDateTime, ZonedDateTime, etc.)
        String str = mapper.writeValueAsString(pgtoContaInvalida);

        mockMvc.perform(MockMvcRequestBuilders.post("/pagamentos")
                .contentType("application/json")
                .content(str)).andExpect(MockMvcResultMatchers.status().is(400));
    }

    @Test
    public void shouldInvalidPaymentDueToDestinationAccount() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        //registerModule(new JavaTimeModule()) → registra o módulo JavaTimeModule,
        //que ensina o Jackson a lidar com as classes de datas e horas do Java 8+ (LocalDate, LocalDateTime, ZonedDateTime, etc.)
        String str = mapper.writeValueAsString(pgtoContaInvalida);

        mockMvc.perform(MockMvcRequestBuilders.post("/pagamentos")
                .contentType("application/json")
                .content(str)).andExpect(MockMvcResultMatchers.status().is(400));
    }
}
