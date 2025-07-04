package modelo;

import java.util.List;

/**
 * define o contrato de operacoes de acesso a dados
 *
 * essa interface generica estabelece os metodos de CRUD
 * @param <T>
 */

public interface OperacoesDAO<T> {
    void inserir(T obj);

    void excluir(T obj);

    void editar(T obj);

    List<T> pesquisar(String key);

    List<T> listarTodos();
}