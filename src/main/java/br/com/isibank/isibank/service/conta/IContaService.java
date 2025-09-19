package br.com.isibank.isibank.service.conta;

import br.com.isibank.isibank.dto.ContaDTO;
import br.com.isibank.isibank.model.Cliente;
import br.com.isibank.isibank.model.Conta;

import java.util.List;

public interface IContaService {

    public Integer cadastrarNovaConta(ContaDTO nova);
    public List<Conta> recuperarPeloCliente(Cliente cliente);
    public Conta recuperarPeloNumero(Integer numeroConta);
}
