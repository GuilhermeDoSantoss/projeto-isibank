package br.com.isibank.isibank.service;

import br.com.isibank.isibank.dto.ClienteDTO;
import br.com.isibank.isibank.service.cliente.IClienteService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class ClienteServiceTests {

    private ClienteDTO reqValida;
    private ClienteDTO reqInvalida;
    private Integer    idValido;

    @Mock
    // cria objeto fake.
    private IClienteService service;

    @BeforeEach
    // inicializa dados e comportamento dos mocks.
    public void setup(){
        reqValida = new ClienteDTO("Cliente Valido", "email@eemail.com",
                "1234567890", "9992344535", "abc12345");
        reqInvalida = new ClienteDTO("Cliente Invalido", null, null, null, null);
        idValido = (1);

        Mockito.when(service.cadastrarCliente(reqValida)).thenReturn(idValido);
        Mockito.when(service.cadastrarCliente(reqInvalida)).thenReturn(null);
        // Mockito.when é usado para definir o comportamento de um mock.
        // Diz para o Mockito: “quando esse metodo for chamado com esses parâmetros, retorne tal coisa”.
    }


    @Test
    // métodos de verificação com asserções (assertEquals).
    public void deveCadastrarClienteValido(){
        assertEquals(service.cadastrarCliente(reqValida), idValido);
    }
    @Test
    public void deveRejeitarClienteInvalido(){
        assertEquals(service.cadastrarCliente(reqInvalida), null);
    }
}
