package br.com.isibank.isibank.repository;

import br.com.isibank.isibank.model.Transacao;
import org.springframework.data.repository.CrudRepository;

public interface TransacaoRepository extends CrudRepository<Transacao, Long> {
}
