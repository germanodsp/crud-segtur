package modelo;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * implementacao dos metodos da interface de acordo com as necessidades da classe
 */

public class DestinoDAO implements OperacoesDAO<Destino>{
    private static final List<Destino> repositorioDeDestinos = new ArrayList<>();

    @Override
    public void inserir(Destino destino) {
        repositorioDeDestinos.add(destino);
        System.out.println("Destino " + destino.nome() + " inserido com sucesso!");
    }

    @Override
    public void excluir(Destino Destinos) {
        if (repositorioDeDestinos.remove(Destinos)) {
            System.out.println("Destino " + Destinos.nome() + " removido com sucesso!");
        } else {
            System.out.println("Destino não encontrado para exclusão.");
        }
    }

    @Override
    public void editar(Destino destino) {
        int index = repositorioDeDestinos.indexOf(destino);
        if (index != -1) {
            repositorioDeDestinos.set(index, destino);
            System.out.println("Destino " + destino.nome() + " editado com sucesso!");
        } else {
            System.out.println("Destino não encontrado para edição.");
        }
    }

    @Override
    public List<Destino> pesquisar(String nome) {
        return repositorioDeDestinos.stream()
                .filter(d -> d.nome().toLowerCase().contains(nome.toLowerCase()))
                .collect(Collectors.toList());
    }

    @Override
    public List<Destino> listarTodos() {
        return new ArrayList<>(repositorioDeDestinos); // Retorna uma cópia para proteger a lista original.
    }
}
