package br.com.isibank.isibank.service;

import br.com.isibank.isibank.dto.ClienteDTO;
import br.com.isibank.isibank.service.cliente.IClienteService;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
public class ClienteServiceTests {

    private ClienteDTO reqValida;
    private ClienteDTO reqInvalida;
    private ClienteDTO reqEmailDupl;
    private ClienteDTO reqCpfDupl;
    private ClienteDTO reqTelDupl;
    private Integer    idValido;

    // @Mock
    // cria objeto fake.
    @Autowired
    // injeção de dependência do Spring.
    private IClienteService service;

    @BeforeEach
    // inicializa dados e comportamento dos mocks.

    public void setup(){
        reqValida = new ClienteDTO("Cliente Valido", "cliente@email.com", "1234567890", "9992344535", "abc12345");
        reqEmailDupl = new ClienteDTO("Cliente email duplicado", "email@email.com", "1234566567", "11987054353", "#234534");
        reqCpfDupl = new ClienteDTO("Cliente cpf duplicado", "email@outroemail.com", "123455778900", "9879439438453", "1234");
        reqTelDupl = new ClienteDTO("Cliente telefone duplicado", "email@atemail.com", "12345678999", "11975484585", "bcd4");
        reqInvalida = new ClienteDTO("Cliente Invalido", null, null, null, null);
        idValido = (1);

        /*
        Mockito.when(service.cadastrarCliente(reqValida)).thenReturn(idValido);
        Mockito.when(service.cadastrarCliente(reqInvalida)).thenThrow(new ConstraintViolationException(null));
        Mockito.when(service.cadastrarCliente(reqEmailDupl)).thenReturn(null);
        Mockito.when(service.cadastrarCliente(reqCpfDupl)).thenReturn(null);
        Mockito.when(service.cadastrarCliente(reqTelDupl)).thenReturn(null);
         */

        // Mockito.when é usado para definir o comportamento de um mock.
        // Diz para o Mockito: “quando esse metodo for chamado com esses parâmetros, retorne tal coisa”.
        //thenReturn → simula um retorno esperado (sucesso).
        //thenThrow → simula um erro/exceção (falha)
    }


    @Test
    // métodos de verificação com asserções (assertThrows).
    public void deveCadastrarClienteValido(){
        assertEquals(service.cadastrarCliente(reqValida), idValido);
    }
    @Test
    public void deveRejeitarClienteInvalido(){
        assertThrows(DataIntegrityViolationException.class, () ->  {
            service.cadastrarCliente(reqInvalida);
        });
        // assertThrows → Verifica se uma exceção esperada é lançada.

        // ConstraintViolation → Representa um erro de validação em uma propriedade,
        // Ex: se email está nulo, gera uma ConstraintViolation dizendo que violou @
    }

    @Test
    public void deveRejeitarClienteCpfDuplicado(){
        assertEquals(service.cadastrarCliente(reqCpfDupl), null);
    }

    @Test
    public void devevRejeitarClieenteTeleefoneDuplicado(){
        assertEquals(service.cadastrarCliente(reqTelDupl), null);
    }
}
