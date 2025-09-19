package br.com.isibank.isibank.service.conta;

import br.com.isibank.isibank.dto.ContaDTO;
import br.com.isibank.isibank.model.Cliente;
import br.com.isibank.isibank.model.Conta;
import br.com.isibank.isibank.repository.ClienteRepository;
import br.com.isibank.isibank.repository.ContaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ContaServiceImpl implements IContaService {

    @Autowired
    private ContaRepository contaRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    @Override
    public Integer cadastrarNovaConta(ContaDTO nova) {
        Cliente cli = clienteRepository.findById(nova.IdCliente()).orElse(null);
        if (cli == null){
            return null;
        }
        return contaRepository.save(nova.toConta()).getNumeroConta();
    }

    @Override
    public List<Conta> recuperarPeloCliente(Cliente cliente) {
        return contaRepository.findByCliente(cliente);
    }

    @Override
    public Conta recuperarPeloNumero(Integer numeroConta) {
        return contaRepository.findById(numeroConta).orElse(null);
        // orElse(null) Metodo do Optional.
        //Se findById achar a conta → retorna o objeto.
        //Se não achar → retorna null (para evitar NoSuchElementException).
    }
}
