package br.com.isibank.isibank.dto;

import br.com.isibank.isibank.model.Cliente;
import br.com.isibank.isibank.model.Conta;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

public record ContaDTO(@NotNull Integer numeroBanco,
                       @NotNull Integer mumeroAgencia,
                       @NotNull @PositiveOrZero Double saldo,
                       //@PositiveOrZero = só permite 0 ou valores positivos.
                       //Útil para cenários em que números negativos não fazem sentido (ex.: saldo bancário, estoque, quantidade).
                       @NotNull @PositiveOrZero Double limite,
                       @NotNull Integer IdCliente) {

    public Conta toConta(){
        Conta conta = new Conta();
        conta.setNumeroBanco(numeroBanco);
        conta.setNumeroAgencia(mumeroAgencia);
        conta.setSaldo(saldo);
        conta.setLimite(limite);
        conta.setAtiva(1);
        Cliente cliente = new Cliente();
        cliente.setIdCliente(idCliente);
        cliente.setCpf("123434465656");
        cliente.setEmail("email@email.com");
        cliente.setTelefone("11948547547");
        conta.setCliente(cliente);
        return conta;
    }
}
