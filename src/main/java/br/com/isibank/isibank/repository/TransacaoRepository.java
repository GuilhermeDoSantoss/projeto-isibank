package br.com.isibank.isibank.repository;

import br.com.isibank.isibank.model.Conta;
import br.com.isibank.isibank.model.Transacao;
import org.springframework.data.repository.CrudRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface TransacaoRepository extends CrudRepository<Transacao, Long> {
    public List<Transacao> findByContaAndDataHoraBetweeen(Conta conta, LocalDateTime inicio, LocalDateTime fim);
}
