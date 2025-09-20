package br.com.isibank.isibank.service.transacao;

import br.com.isibank.isibank.dto.PagamentoDTO;;
import br.com.isibank.isibank.dto.TransferenciaDTO;
import br.com.isibank.isibank.model.Transacao;

public interface ITransacaoService {

    public boolean efetuarPagamento(PagamentoDTO pagamento);
    public boolean efetuarTransferencia(TransferenciaDTO transferencia);
}
