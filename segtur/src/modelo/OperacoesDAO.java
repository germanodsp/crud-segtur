package modelo;

import java.util.List;

public interface OperacoesDAO<T> {
    void inserir(T obj);

    void excluir(T obj);

    void editar(T obj);

    List<T> pesquisar(String key);
}