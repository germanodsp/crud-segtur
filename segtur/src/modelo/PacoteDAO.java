package modelo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Classe de Objeto de Acesso a Dados (DAO) para a entidade Pacote.
 * Utiliza um HashMap para simular a persistência de dados em memória,
 * garantindo acesso rápido e eficiente aos pacotes através de um ID único.
 */
public class PacoteDAO implements OperacoesDAO<Pacote> {

    // Utiliza um HashMap para um armazenamento otimizado por chave (ID do pacote).
    private static final Map<Integer, Pacote> repositorioDePacotes = new HashMap<>();
    private static int proximoId = 1;

    /**
     * Insere um novo pacote no repositório.
     * Um ID único é gerado e atribuído ao pacote antes da inserção.
     * @param pacote O objeto Pacote a ser inserido.
     */
    @Override
    public void inserir(Pacote pacote) {
        // Gera e atribui um novo ID para o pacote
        int id = proximoId++;
        pacote.setId(id);
        // Adiciona o pacote ao mapa usando o ID como chave.
        repositorioDePacotes.put(id, pacote);
        System.out.println("Pacote '" + pacote.getNome() + "' inserido com o ID: " + id);
    }

    /**
     * Exclui um pacote do repositório usando o seu ID como chave.
     * @param pacote O objeto Pacote a ser excluído.
     */
    @Override
    public void excluir(Pacote pacote) {
        // A remoção no HashMap é muito eficiente quando se tem a chave.
        Pacote removido = repositorioDePacotes.remove(pacote.getId());
        if (removido != null) {
            System.out.println("Pacote '" + removido.getNome() + "' removido com sucesso!");
        } else {
            System.out.println("Pacote com ID " + pacote.getId() + " não encontrado para exclusão.");
        }
    }

    /**
     * Edita um pacote existente no repositório.
     * A operação verifica se a chave (ID) existe antes de substituir o objeto.
     * @param pacoteAtualizado O objeto Pacote com os dados atualizados.
     */
    @Override
    public void editar(Pacote pacoteAtualizado) {
        // O método put substitui o valor se a chave já existir.
        if (repositorioDePacotes.containsKey(pacoteAtualizado.getId())) {
            repositorioDePacotes.put(pacoteAtualizado.getId(), pacoteAtualizado);
            System.out.println("Pacote '" + pacoteAtualizado.getNome() + "' editado com sucesso!");
        } else {
            System.out.println("Pacote com ID " + pacoteAtualizado.getId() + " não encontrado para edição.");
        }
    }

    /**
     * Pesquisa pacotes cujo nome do destino contém a chave de busca.
     * @param nomeDestino A chave de busca para o nome do destino.
     * @return Uma lista de pacotes que correspondem ao critério.
     */
    @Override
    public List<Pacote> pesquisar(String nomeDestino) {
        // Para pesquisar por um atributo, percorremos os valores do mapa.
        return repositorioDePacotes.values().stream()
                .filter(p -> p.getDestino().nome().toLowerCase().contains(nomeDestino.toLowerCase()))
                .collect(Collectors.toList());
    }

    /**
     * Retorna uma lista com todos os pacotes cadastrados.
     * @return Uma List<Pacote> com todos os pacotes.
     */
    @Override
    public List<Pacote> listarTodos() {
        // Retorna uma nova ArrayList contendo todos os valores (objetos Pacote) do mapa.
        return new ArrayList<>(repositorioDePacotes.values());
    }

    public Pacote pesquisarPorId(int id) {
        // O método get() do HashMap é a forma mais eficiente de buscar por chave.
        return repositorioDePacotes.get(id);
    }

}