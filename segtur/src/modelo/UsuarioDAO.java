package modelo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class UsuarioDAO implements OperacoesDAO<Usuario> {

    private static final Map<String, Usuario> repositorioDeUsuarios = new HashMap<>();

    @Override
    public void inserir(Usuario usuario) {
        repositorioDeUsuarios.put(usuario.cpf(), usuario);
        System.out.println("Usuário " + usuario.nome() + " inserido com sucesso!");
    }

    @Override
    public void excluir(Usuario usuario) {
        if (repositorioDeUsuarios.remove(usuario.cpf()) != null) {
            System.out.println("Usuário " + usuario.nome() + " removido com sucesso!");
        } else {
            System.out.println("Usuário não encontrado para exclusão.");
        }
    }

    @Override
    public void editar(Usuario usuarioAtualizado) {
        if (repositorioDeUsuarios.containsKey(usuarioAtualizado.cpf())) {
            repositorioDeUsuarios.put(usuarioAtualizado.cpf(), usuarioAtualizado);
            System.out.println("Usuário " + usuarioAtualizado.nome() + " editado com sucesso!");
        } else {
            System.out.println("Usuário não encontrado para edição.");
        }
    }

    @Override
    public List<Usuario> pesquisar(String nome) {
        return repositorioDeUsuarios.values().stream()
                .filter(u -> u.nome().toLowerCase().contains(nome.toLowerCase()))
                .collect(Collectors.toList());
    }

    @Override
    public List<Usuario> listarTodos() {
        return new ArrayList<>(repositorioDeUsuarios.values());
    }

    public Usuario pesquisarPorCpf(String cpf) {
        return repositorioDeUsuarios.get(cpf);
    }
}