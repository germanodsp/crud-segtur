package modelo;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

// 1. Implemente a interface
public class UsuarioDAO implements OperacoesDAO<Usuario> {

    // 2. Use uma lista estática para "simular" o banco de dados em memória
    private static List<Usuario> listaDeUsuarios = new ArrayList<>();

    @Override
    public void inserir(Usuario usuario) {
        listaDeUsuarios.add(usuario);
        System.out.println("Usuário " + usuario.getNome() + " inserido com sucesso!");
    }

    @Override
    public void excluir(Usuario usuario) {
        listaDeUsuarios.remove(usuario);
    }

    @Override
    public void editar(Usuario usuario) {
        // Para editar, você pode remover o antigo e adicionar o novo
        // (precisa do equals/hashCode funcionando bem na classe Usuario)
        int index = listaDeUsuarios.indexOf(usuario);
        if (index != -1) {
            listaDeUsuarios.set(index, usuario);
        }
    }

    @Override
    public List<Usuario> pesquisar(String nome) {
        // Usando stream para filtrar usuários pelo nome
        return listaDeUsuarios.stream()
                .filter(u -> u.getNome().toLowerCase().contains(nome.toLowerCase()))
                .collect(Collectors.toList());
    }

    // Método extra para buscar todos os usuários
    public List<Usuario> buscarTodos() {
        return new ArrayList<>(listaDeUsuarios); // Retorna uma cópia da lista
    }

    public static void main(String[] args) {
        UsuarioDAO dao = new UsuarioDAO();
        Usuario usuario = new Usuario("12312332152", "Germano", "Senha", "email@gmail.com", "519999999");
        dao.inserir(usuario);

        dao.excluir(usuario);
        dao.editar(usuario);
        dao.pesquisar("João");
        dao.buscarTodos();
    }
}

