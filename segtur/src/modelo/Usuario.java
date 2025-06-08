package modelo;

import java.util.Objects;

public class Usuario {
    private String cpf;
    private String nome;
    private String senha;
    private String email;
    private String telefone;

    public Usuario(String cpf, String nome, String senha, String email, String telefone) {
        this.cpf = cpf;
        this.nome = nome;
        this.senha = senha;
        this.email = email;
        this.telefone = telefone;
    }

    public String getCpf() {return cpf;}

    public String getEmail() {return email;}

    public void setEmail(String email) {this.email = email;}

    public String getNome() {return nome;}

    public void setNome(String nome) {this.nome = nome;}

    public String getSenha() {return senha;}

    public void setSenha(String senha) { this.senha = senha;}

    public String getTelefone() {return telefone;}

    public void setTelefone(String telefone) {this.telefone = telefone;}

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Usuario usuario)) return false;
        return Objects.equals(cpf, usuario.cpf) && Objects.equals(nome, usuario.nome) && Objects.equals(email, usuario.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cpf, nome, email);
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "cpf='" + cpf + '\'' +
                ", email='" + email + '\'' +
                ", nome='" + nome + '\'' +
                ", telefone='" + telefone + '\'' +
                ", senha='" + senha + '\'' +
                '}';
    }
}