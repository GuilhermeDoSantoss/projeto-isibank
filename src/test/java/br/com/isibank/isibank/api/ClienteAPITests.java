package br.com.isibank.isibank.api;

import br.com.isibank.isibank.dto.ClienteDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@AutoConfigureMockMvc
public class ClienteAPITests {

    @Autowired
    private MockMvc mvc;

    private ClienteDTO reqValida;
    private ClienteDTO reqInvalida;
    private ClienteDTO reqEmailDupl;
    private ClienteDTO reqCpfDupl;
    private ClienteDTO reqTelDupl;

    @BeforeEach
    public void setup(){
    reqValida = new ClienteDTO("Cliente Valido API", "clienteapi@email.com", "12221117890", "9992344535", "abc123456");
    reqEmailDupl = new ClienteDTO("Cliente email duplicado", "email@email.com", "1234566567", "11987054353", "#2345345");
    reqCpfDupl = new ClienteDTO("Cliente cpf duplicado", "email@outroemail.com", "123455778900", "9879439438453", "123456");
    reqTelDupl = new ClienteDTO("Cliente telefone duplicado", "email@atemail.com", "12345678999", "11975484585", "bcd456");
    reqInvalida = new ClienteDTO("Cliente Invalido", null, null, null, null);
    }

    @Test
    public void shouldCallAPIForValidCliente() throws Exception {
        ObjectMapper objMapper = new ObjectMapper();
        String str = objMapper.writeValueAsString(reqValida);
        mvc.perform(MockMvcRequestBuilders.post("/clientes")
                        .contentType("application/json")
                        .content(str)).andExpect(MockMvcResultMatchers.status().is(201));
    }

    @Test
    public void shouldCreateClienteWithDuplicateEmail() throws Exception {
        ObjectMapper objMapper = new ObjectMapper();
        String str = objMapper.writeValueAsString(reqEmailDupl);
        mvc.perform(MockMvcRequestBuilders.post("/clientes")
                .contentType("application/json")
                .content(str)).andExpect(MockMvcResultMatchers.status().is(409));
    }

    @Test
    public void shouldCreateClienteWithDuplicateCpf() throws Exception {
        ObjectMapper objMapper = new ObjectMapper();
        String str = objMapper.writeValueAsString(reqCpfDupl);
        mvc.perform(MockMvcRequestBuilders.post("/clientes")
                .contentType("application/json")
                .content(str)).andExpect(MockMvcResultMatchers.status().is(409));
    }

    @Test
    public void shouldCreateClienteWithDuplicatePhone() throws Exception {
        ObjectMapper objMapper = new ObjectMapper();
        String str = objMapper.writeValueAsString(reqTelDupl);
        mvc.perform(MockMvcRequestBuilders.post("/clientes")
                .contentType("application/json")
                .content(str)).andExpect(MockMvcResultMatchers.status().is(409));
    }

}
