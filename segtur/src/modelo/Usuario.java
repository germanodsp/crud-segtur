package modelo;

import java.util.Objects;

public record Usuario(String cpf, String nome, String senha, String email, String telefone) {

    public Usuario {
        // Validação basica dos dados do usuário.
        if (cpf == null || cpf.isBlank()) {
            throw new IllegalArgumentException("CPF é obrigatório e não pode estar vazio.");
        }
        if (nome == null || nome.isBlank()) {
            throw new IllegalArgumentException("Nome é obrigatório e não pode estar vazio.");
        }
        if (senha == null || senha.length() < 8) {
            throw new IllegalArgumentException("Senha deve ter pelo menos 6 caracteres.");
        }
        if (email == null || !email.contains("@")) {
            throw new IllegalArgumentException("Email deve ser válido.");
        }
    }

    //construtor adicional para sobrecarga do construtor compacto
    public Usuario(String cpf, String nome, String senha, String email) {
        this(cpf, nome, senha, email, ""); // Chama o construtor canônico
    }
//Sobrescrita do equals
@Override
public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Usuario usuario = (Usuario) o;
    return Objects.equals(cpf, usuario.cpf);
}

    @Override
    public String toString() {
        return "Nome=" + nome() + '\n' +
                "CPF=" + cpf() + '\n' +
                "Senha=" + senha() + '\n' +
                "Email="+ email() + '\n'+
                "Telefone="+ telefone();
    }

}