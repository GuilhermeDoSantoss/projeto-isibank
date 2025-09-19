package br.com.isibank.isibank.dto;

import br.com.isibank.isibank.model.Cliente;

public record ClienteDTO(String nome, String email, String cpf, String telefone, String senha) {

    public Cliente toCliente(){
        Cliente cliente = new Cliente();
        cliente.setCpf(cpf);
        cliente.setNome(nome);
        cliente.setEmail(email);
        cliente.setTelefone(telefone);
        cliente.setSenha(senha);
        return cliente;
    }
}


// DTO (Data Transfer Object) → Classe tradicional usada para transportar dados entre camadas.
// Normalmente tem getters/setters, pode ter validações, conversões, anotações do Jackson/Bean Validation etc.
// Mais flexível, mas gera muito boilerplate.

// Record → Tipo imutável criado para carregar dados de forma enxuta.
// O compilador já gera equals, hashCode, toString, getters e construtor.
// Ideal para DTOs simples e imutáveis. Porém, é mais restrito (não tem setters, herança limitada).

// DTO = Verbosidade, mas customizável.
// Record = Enxuto, imutável, menos burocracia.