package br.com.isibank.isibank.exceptions;

public class NotEnoughBalanceException extends RuntimeException{
                                         // RuntimeException permite lançar erros em tempo de execução sem burocracia no código.
                                         // Você só trata se fizer sentido, deixando o código mais limpo.

    public NotEnoughBalanceException(String message){
        super(message);
    }
}
