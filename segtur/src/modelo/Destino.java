package modelo;

import java.util.Objects;

public record Destino(int idDestino, String nome, String descricao) {
    public Destino {
        if (Objects.isNull(nome) || nome.isBlank())
            throw new IllegalArgumentException("Nome obrigatorio para criacao do Destino");
//        if(Objects.isNull(descricao) || descricao.isBlank())
//            throw new IllegalArgumentException("Descricao obrigatoria para criacao do Destino");
        if (Objects.isNull(idDestino) || idDestino < 0)
            throw new IllegalArgumentException("ID menor que zer ou vazio!");
    }
    public Destino(int idDestino, String nome) {
        this(idDestino, nome, "");
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Destino destino = (Destino) o;
        return Objects.equals(idDestino, destino.idDestino);
    }

    @Override
    public String toString() {
        return "nome=" + nome + '\'' +
                "descricao=" + descricao + '\'' +
                "idDestino=" + idDestino;
    }
}
