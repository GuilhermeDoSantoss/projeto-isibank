package br.com.isibank.isibank.service.transacao;

import br.com.isibank.isibank.dto.PagamentoDTO;
import br.com.isibank.isibank.dto.TransferenciaDTO;
import br.com.isibank.isibank.repository.ContaRepository;
import br.com.isibank.isibank.repository.TransacaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TransacaoServiceImpl implements ITransacaoService {

    @Autowired
    private TransacaoRepository transacaoRepository;

    @Autowired
    private ContaRepository contaRepository;

    @Override
    public boolean efetuarPagamento(PagamentoDTO pagamento) {
        return false;
    }

    @Override
    public boolean efetuarTransferencia(TransferenciaDTO transferencia) {
        return false;
    }
}
