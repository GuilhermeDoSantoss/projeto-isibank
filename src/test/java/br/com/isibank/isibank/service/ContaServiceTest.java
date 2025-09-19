package br.com.isibank.isibank.service;

import br.com.isibank.isibank.dto.ContaDTO;
import br.com.isibank.isibank.service.conta.IContaService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class ContaServiceTest {

    @Mock
    private IContaService service;

    private ContaDTO contaValida;
    private ContaDTO contaInvalida;

    @BeforeEach
    public void setup(){
        contaValida = new ContaDTO(1, 1, 100.0, 0.0, 10);
        contaInvalida = new ContaDTO(1, 1, 100.0, 0.0, 2);

        Mockito.when(service.cadastrarNovaConta(contaValida)).thenReturn(1);
        Mockito.when(service.cadastrarNovaConta(contaInvalida)).thenReturn(null);
    }

    @Test
    public void deveriaAceitarContaComClienteExistente(){
        assertNotNull(service.cadastrarNovaConta(contaValida));
        // assertNotNull → usado quando você espera um resultado válido.
    }

    @Test
    public void deveriaRejeitarContaComClienteInvalido(){
        assertEquals(service.cadastrarNovaConta(contaInvalida), null);
        // assertEquals(..., null) → usado quando você espera rejeição/falha
    }
}
