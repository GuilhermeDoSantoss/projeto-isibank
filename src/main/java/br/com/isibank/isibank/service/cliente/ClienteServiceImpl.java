package br.com.isibank.isibank.service.cliente;

import br.com.isibank.isibank.dto.ClienteDTO;
import br.com.isibank.isibank.model.Cliente;
import br.com.isibank.isibank.repository.ClienteRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClienteServiceImpl implements IClienteService {

    @Autowired
    private ClienteRepository repository;

    @Override
    public Integer cadastrarCliente(@Valid ClienteDTO novo) {
        Cliente teste = repository.findByEmailOrCpfOrTelefone(novo.email(), novo.cpf(), novo.telefone());
        if (teste  != null)
            return null;

        return repository.save(novo.toCliente()).getIdCliente();
    }

    @Override
    public Integer alterarDados(ClienteDTO cliente) {
        return repository.save(cliente.toCliente()).getIdCliente();
    }
}
