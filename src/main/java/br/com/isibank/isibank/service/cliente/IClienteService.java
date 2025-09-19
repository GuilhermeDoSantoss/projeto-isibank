package br.com.isibank.isibank.service.cliente;

import br.com.isibank.isibank.dto.ClienteDTO;

public interface IClienteService {

    public Integer cadastrarCliente(ClienteDTO novo);
    public Integer alterarDados(ClienteDTO cliente);

}
