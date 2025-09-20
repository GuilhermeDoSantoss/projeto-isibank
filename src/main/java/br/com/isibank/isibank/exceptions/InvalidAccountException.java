package br.com.isibank.isibank.exceptions;

public class InvalidAccountException extends RuntimeException {

    public InvalidAccountException(String message){
        super(message);
        //extends RuntimeException → transforma a classe em uma exceção não verificada (unchecked).
        //Ou seja, não precisa ser declarada no throws.

        //public InvalidAccountException(String message) → construtor que recebe uma mensagem de erro.

        //super(message) → chama o construtor da RuntimeException para armazenar a mensagem
        // e permitir que ela seja exibida no stack trace.
    }
}
