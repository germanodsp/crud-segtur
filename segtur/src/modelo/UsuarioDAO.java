package modelo;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class UsuarioDAO implements OperacoesDAO<Usuario> {

    private static final List<Usuario> repositorioDeUsuarios = new ArrayList<>();

    @Override
    public void inserir(Usuario usuario) {
        repositorioDeUsuarios.add(usuario);
        System.out.println("Usuário " + usuario.nome() + " inserido com sucesso!");
    }

    @Override
    public void excluir(Usuario usuario) {
        if (repositorioDeUsuarios.remove(usuario)) {
            System.out.println("Usuário " + usuario.nome() + " removido com sucesso!");
        } else {
            System.out.println("Usuário não encontrado para exclusão.");
        }
    }

    @Override
    public void editar(Usuario usuario) {
        int index = repositorioDeUsuarios.indexOf(usuario);
        if (index != -1) {
            repositorioDeUsuarios.set(index, usuario);
            System.out.println("Usuário " + usuario.nome() + " editado com sucesso!");
        } else {
            System.out.println("Usuário não encontrado para edição.");
        }
    }

    @Override
    public List<Usuario> pesquisar(String nome) {
        return repositorioDeUsuarios.stream()
                .filter(u -> u.nome().toLowerCase().contains(nome.toLowerCase()))
                .collect(Collectors.toList());
    }


    @Override
    public List<Usuario> listarTodos() {
        return new ArrayList<>(repositorioDeUsuarios); // Retorna uma cópia para proteger a lista original.
    }

}