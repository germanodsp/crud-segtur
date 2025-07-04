package view;

import modelo.Destino;
import modelo.DestinoDAO;

import java.util.List;

public class MainDestino {
    public static void main(String[] args) {
        Destino d1 = new Destino(1, "Brasil", "Carnavel e praias lindas");
        Destino d2 = new Destino(2, "Itália", "Comida boa e coliseu");
        Destino d3 = new Destino(3, "Espanha", "Belas casas");
        Destino d4 = new Destino(4, "França", "Croassaint");
        Destino d5 = new Destino(5, "Alemanha", "Loiras e Loiros");
        
        DestinoDAO dao = new DestinoDAO();
        dao.inserir(d1);
        dao.inserir(d2);
        System.out.println("\n--- LISTANDO TODOS OS DESTINOS APoS INSERÇÃO ---");
        List<Destino> listaDeDestino = dao.listarTodos();
        for (Destino u : listaDeDestino) {
            System.out.println(u);
        }
        System.out.println("\n--- RELIZADAS OPERACOES DO DAO ---");
        dao.excluir(d2);
        dao.editar(new Destino(1, "BRASIL", "Mulheres muito quentes e praia lindas"));
        System.out.println("\n--DEPOIS DE EXCLUIR ITALIA E EDITAR BRASIL");
        listaDeDestino = dao.listarTodos();
        for (Destino u : listaDeDestino) {
            System.out.println(u);
        }
        System.out.println("\n--- ADICIONADO MAIS DESTINOS E PESQUISANDO USUARIO ---");
        dao.inserir(d3);
        dao.inserir(d4);
        dao.inserir(d5);
        listaDeDestino = dao.pesquisar("Brasil");
        for (Destino u : listaDeDestino) {
            System.out.println(u);
            System.out.println();
        }
    }
}
