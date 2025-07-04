package modelo;

import java.util.Objects;

/**
 *
 *  destino implementado com java record, pois nao vai precisar ser mudado
 *  usamos arraylist pelo mesmo motivo pois nao vai precisar ser acessado com muita frequencia, apenas listado
 * @param idDestino
 * @param nome
 * @param descricao
 */

public record Destino(int idDestino, String nome, String descricao) {
    public Destino {
        if (Objects.isNull(nome) || nome.isBlank())
            throw new IllegalArgumentException("Nome obrigatorio para criacao do Destino");
//        if(Objects.isNull(descricao) || descricao.isBlank())
//            throw new IllegalArgumentException("Descricao obrigatoria para criacao do Destino");
        if (Objects.isNull(idDestino) || idDestino < 0)
            throw new IllegalArgumentException("ID menor que zer ou vazio!");
    }

    /**
     * criado apenas para ter uma sobrecarga do construtor
     * metodos equals e tostring com leves alteracoes para o funcionamento
     * @param idDestino
     * @param nome
     */

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
        return "idDestino=" + idDestino + '\n' +
                "nome=" + nome + '\n' +
                "descricao=" + descricao;
    }
}
