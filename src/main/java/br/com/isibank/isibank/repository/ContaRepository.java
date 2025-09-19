package br.com.isibank.isibank.repository;

import br.com.isibank.isibank.model.Cliente;
import br.com.isibank.isibank.model.Conta;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ContaRepository extends CrudRepository<Conta, Integer> {

    public List<Conta> findByCliente(Cliente cliente);
}
