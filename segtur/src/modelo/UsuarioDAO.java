package modelo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Classe de Acesso a Dados (DAO) para a entidade Usuario.
 * Utiliza um HashMap para um armazenamento otimizado por CPF.
 */
public class UsuarioDAO implements OperacoesDAO<Usuario> {

    // CORREÇÃO: A estrutura agora é um Map (HashMap) para permitir busca por chave (CPF).
    private static final Map<String, Usuario> repositorioDeUsuarios = new HashMap<>();

    /**
     * Insere um novo usuário no repositório, usando o CPF como chave única.
     * @param usuario O objeto Usuario a ser inserido.
     */
    @Override
    public void inserir(Usuario usuario) {
        // Usa o CPF do usuário como a chave no mapa.
        repositorioDeUsuarios.put(usuario.cpf(), usuario);
        System.out.println("Usuário " + usuario.nome() + " inserido com sucesso!");
    }

    /**
     * Exclui um usuário do repositório usando o CPF como chave.
     * @param usuario O objeto Usuario a ser excluído.
     */
    @Override
    public void excluir(Usuario usuario) {
        // A remoção pela chave (CPF) é extremamente eficiente.
        if (repositorioDeUsuarios.remove(usuario.cpf()) != null) {
            System.out.println("Usuário " + usuario.nome() + " removido com sucesso!");
        } else {
            System.out.println("Usuário não encontrado para exclusão.");
        }
    }

    /**
     * Edita um usuário existente. A busca é feita pelo CPF.
     * @param usuarioAtualizado O objeto Usuario com os dados atualizados.
     */
    @Override
    public void editar(Usuario usuarioAtualizado) {
        // O método put já substitui o valor se a chave (CPF) existir.
        if (repositorioDeUsuarios.containsKey(usuarioAtualizado.cpf())) {
            repositorioDeUsuarios.put(usuarioAtualizado.cpf(), usuarioAtualizado);
            System.out.println("Usuário " + usuarioAtualizado.nome() + " editado com sucesso!");
        } else {
            System.out.println("Usuário não encontrado para edição.");
        }
    }

    /**
     * Pesquisa usuários cujo nome contém a chave de busca.
     * @param nome A chave de busca para o nome do usuário.
     * @return Uma lista de usuários que correspondem ao critério.
     */
    @Override
    public List<Usuario> pesquisar(String nome) {
        // Para pesquisar por nome, percorremos os valores do mapa.
        return repositorioDeUsuarios.values().stream()
                .filter(u -> u.nome().toLowerCase().contains(nome.toLowerCase()))
                .collect(Collectors.toList());
    }

    @Override
    public List<Usuario> listarTodos() {
        // Retorna uma lista com todos os valores (objetos Usuario) do mapa.
        return new ArrayList<>(repositorioDeUsuarios.values());
    }

    /**
     * Pesquisa um usuário pelo seu CPF (identificador único).
     * @param cpf O CPF a ser pesquisado.
     * @return O objeto Usuario se encontrado, caso contrário, null.
     */
    public Usuario pesquisarPorCpf(String cpf) {
        // CORREÇÃO: Agora o .get(cpf) funciona, pois 'cpf' é a chave do HashMap.
        return repositorioDeUsuarios.get(cpf);
    }
}