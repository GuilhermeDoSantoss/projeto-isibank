package br.com.isibank.isibank.repository;

import br.com.isibank.isibank.model.Cliente;
import org.springframework.data.repository.CrudRepository;

public interface ClienteRepository extends CrudRepository<Cliente, Integer> {

    public Cliente findByEmailOrCpfOrTelefone(String email, String cpf, String telefone);
}

// CrudRepository -> Fornece operações básicas de CRUD: save, findById, findAll, deleteById, etc