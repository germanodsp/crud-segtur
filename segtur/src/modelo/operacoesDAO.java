package modelo;

import java.util.ArrayList;

public interface operacoesDAO {
    public void inserir (Object obj);

    public void excluir (Object obj);

    public void editar (Object newObj);

    public ArrayList pesquisar(Object key);
}
