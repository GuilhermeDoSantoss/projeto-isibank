package br.com.isibank.isibank.service.transacao;

import br.com.isibank.isibank.dto.ExtratoDTO;
import br.com.isibank.isibank.dto.PagamentoDTO;
import br.com.isibank.isibank.dto.TransferenciaDTO;
import br.com.isibank.isibank.exceptions.InvalidAccountException;
import br.com.isibank.isibank.exceptions.InvalidDateIntervalException;
import br.com.isibank.isibank.exceptions.NotEnoughBalanceException;
import br.com.isibank.isibank.model.Conta;
import br.com.isibank.isibank.model.Transacao;
import br.com.isibank.isibank.repository.ContaRepository;
import br.com.isibank.isibank.repository.TransacaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.InvalidParameterException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class TransacaoServiceImpl implements ITransacaoService {

    @Autowired
    private TransacaoRepository transacaoRepository;

    @Autowired
    private ContaRepository contaRepository;

    @Override
    public boolean efetuarPagamento(PagamentoDTO pagamento) {
        Conta conta = contaRepository.findById(pagamento.numeroConta()).orElse(null);
        if (conta == null){
            throw new InvalidParameterException("Account " + pagamento.numeroConta() + "does not exist");
        }
        if (conta.getSaldo() + conta.getLimite() < pagamento.valor()) {
            throw new NotEnoughBalanceException("Not enough balance available for Account #" + pagamento.numeroConta());
        }
        Transacao transacao = new Transacao();
        Double saldoIni, saldoFim;
        saldoIni = conta.getSaldo();
        saldoFim = conta.getSaldo() - pagamento.valor();

        transacao.setConta(conta);
        transacao.setValor(pagamento.valor());
        transacao.setSaldoInicial(saldoIni);
        transacao.setDataHora(pagamento.dataHora());
        transacao.setDescricao(pagamento.descricao());
        transacao.setNumeroDocumento(pagamento.numDoc());
        transacao.setTipo(-1);
        conta.setSaldo(conta.getSaldo() - pagamento.valor());
        transacao.setSaldoFinal(saldoFim);
        transacaoRepository.save(transacao);

        conta.setSaldo(saldoFim);
        contaRepository.save(conta);

        return true;
    }

    @Override
    public boolean efetuarTransferencia(TransferenciaDTO transferencia) {
        Conta contaOrigem;
        Conta contaDestino;

        contaOrigem = contaRepository.findById(transferencia.contaOrigem()).orElse(null);
        contaDestino = contaRepository.findById(transferencia.contaDestino()).orElse(null);
        if (contaOrigem == null || contaDestino == null) {
            throw new InvalidParameterException("Invalid ource or Destination Account");
        }
        if (contaOrigem.getSaldo() + contaOrigem.getLimite() < transferencia.valor()) {
            throw new NotEnoughBalanceException("Not Enough Balance   for Account #" + contaOrigem.getNumeroConta());
        }

        Transacao trDebito, trCredito;
        Double saldoIniO, saldoIniD, saldoFimO, saldoFimD;
        saldoIniO = contaOrigem.getSaldo();
        saldoFimO = contaOrigem.getSaldo() - transferencia.valor();

        saldoIniD = contaDestino.getSaldo();
        saldoFimD = contaDestino.getSaldo() + transferencia.valor();

        trDebito = new Transacao();
        trDebito.setConta(contaOrigem);
        trDebito.setDataHora(transferencia.dataHora());
        trDebito.setValor(transferencia.valor());
        trDebito.setSaldoInicial(saldoIniO);
        trDebito.setSaldoFinal(saldoFimO);
        trDebito.setTipo(-1);
        trDebito.setDescricao(transferencia.descricao());
        trDebito.setNumeroDocumento(UUID.randomUUID().toString());

        trCredito = new Transacao();
        trCredito.setConta(contaDestino);
        trCredito.setDataHora(transferencia.dataHora());
        trCredito.setValor(transferencia.valor());
        trCredito.setSaldoInicial(saldoIniD);
        trCredito.setSaldoFinal(saldoFimD);
        trCredito.setTipo(1);
        trCredito.setDescricao(transferencia.descricao());
        trCredito.setNumeroDocumento(UUID.randomUUID().toString());

        transacaoRepository.save(trCredito);
        transacaoRepository.save(trDebito);

        contaOrigem.setSaldo(saldoFimO);
        contaDestino.setSaldo(saldoFimD);

        contaRepository.save(contaOrigem);
        contaRepository.save(contaDestino);
        return true;
    }

    @Override
    public List<Transacao> getExtrato(ExtratoDTO extrato) {
        Conta conta = contaRepository.findById(extrato.numeroConta()).orElse(null);
        if (conta == null){
            throw new InvalidAccountException("Invalid Account number # " + extrato.numeroConta());
        }
        if(extrato.inicio().isAfter(extrato.fim())) {
            throw new InvalidDateIntervalException("Invalid Date Interval");
        }

        return transacaoRepository.findByContaAndDataHoraBetweeen(conta, extrato.inicio(), extrato.fim());
    }

}
