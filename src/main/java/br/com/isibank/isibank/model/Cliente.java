package br.com.isibank.isibank.model;

import jakarta.persistence.*;

@Entity
@Table(name = "tbl_cliente")
public class Cliente {

//nullable = false → constraint de NOT NULL. O campo é obrigatório, não aceita valores nulos no banco.
//unique = true → cria um índice único. Garante que nenhum registro terá o mesmo valor nesse campo (exemplo: não podem existir dois clientes com o mesmo e-mail).
//length = 100 → define o tamanho máximo da coluna no banco de dados, ou seja, VARCHAR(100).

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_cliente")
    private Integer idCliente;
    @Column(name = "nome_cliente", nullable = false, length = 150)
    private String nome;
    @Column(name = "email_cliente", nullable = false, unique = true, length = 100)
    private String email;
    @Column(name = "cpf_cliente", nullable = false, unique = true, length = 11)
    private String cpf;
    @Column(name = "telefone_cliente", nullable = false, unique = true, length = 20)
    private String telefone;
    @Column(name = "senha_cliente", nullable = false, length = 200)
    private String senha;


    public Integer getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(Integer idCliente) {
        this.idCliente = idCliente;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }
}

