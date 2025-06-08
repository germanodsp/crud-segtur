package modelo;

import java.util.Objects;

public class Usuario {
    private String cpf;
    private String nome;
    private String senha;
    private String email;
    private String telefone;

    // Constructor principal
    public Usuario(String cpf, String nome, String senha, String email, String telefone) {
        if (cpf == null || cpf.isEmpty()) {
            throw new IllegalArgumentException("CPF é obrigatório e não pode estar vazio.");
        }
        if (nome == null || nome.isEmpty()) {
            throw new IllegalArgumentException("Nome é obrigatório e não pode estar vazio.");
        }
        this.cpf = cpf;
        this.nome = nome;
        this.senha = senha;
        this.email = email;
        this.telefone = telefone;
    }

    // Sobrecarga de construtor
    public Usuario(String cpf, String nome, String senha, String email) {
        this(cpf, nome, senha, email, ""); // Inicializa telefone como vazio
    }

    // Getters e Setters
    public String getCpf() {
        return cpf;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        if (nome == null || nome.isEmpty()) {
            throw new IllegalArgumentException("Nome não pode ser vazio.");
        }
        this.nome = nome;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        if (senha == null || senha.length() < 6) {
            throw new IllegalArgumentException("Senha deve ter pelo menos 6 caracteres.");
        }
        this.senha = senha;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        if (email == null || !email.contains("@")) {
            throw new IllegalArgumentException("Email deve ser válido.");
        }
        this.email = email;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    // Sobrescrita de métodos padrão
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Usuario)) return false;
        Usuario usuario = (Usuario) o;
        return Objects.equals(cpf, usuario.cpf);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cpf);
    }

    @Override
    public String toString() {
        return String.format(
                "Usuario{cpf='%s', nome='%s', email='%s', telefone='%s'}",
                cpf, nome, email, telefone
        );
    }
}