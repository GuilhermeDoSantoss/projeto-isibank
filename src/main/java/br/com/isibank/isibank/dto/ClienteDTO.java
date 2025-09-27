package br.com.isibank.isibank.dto;

import br.com.isibank.isibank.model.Cliente;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

public record ClienteDTO(@NotNull String nome,
                         @Email @NotNull String email,
                         @NotNull String cpf,
                         @NotNull  @Length(min = 11) String telefone,
                         @NotNull @Length(min = 8) String senha)
//@NotNull → campo obrigatório, não pode ser null, dado mandatório
//@Email → valida se o valor tem formato de e-mail válido.
//@Min → valida se o valor numérico, valor mínimo permitido (apenas para tipos numéricos).

    {

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