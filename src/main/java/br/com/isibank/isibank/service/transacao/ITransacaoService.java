package br.com.isibank.isibank.service.transacao;

import br.com.isibank.isibank.dto.ExtratoDTO;
import br.com.isibank.isibank.dto.PagamentoDTO;;
import br.com.isibank.isibank.dto.TransferenciaDTO;
import br.com.isibank.isibank.model.Conta;
import br.com.isibank.isibank.model.Transacao;

import java.time.LocalDateTime;
import java.util.List;

public interface ITransacaoService {

    public boolean efetuarPagamento(PagamentoDTO pagamento);
    public boolean efetuarTransferencia(TransferenciaDTO transferencia);
    public List<Transacao> getExtrato(ExtratoDTO extrato);
}
