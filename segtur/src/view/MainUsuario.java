package view;

import modelo.Usuario;
import modelo.UsuarioDAO;
import java.util.List;

public class MainUsuario {
    public static void main(String[] args) {
        Usuario u1 = new Usuario("123456789-10", "Alice Coltrane", "25122025", "turiya@gmail.com");
        Usuario u2 = new Usuario("123456789-11", "Itamar Assumpção", "04201420", "negodito@gmail.com","551234567");
        Usuario u3 = new Usuario("518932186-95", "Donald Byrd", "54853215", "wherearewe@gmail.com");
        Usuario u4 = new Usuario("181318463-98", "Horace Silver", "12743712", "SoulSearchin@gmail.com");
        Usuario u5 = new Usuario("126854651-53", "Arthur Verocai", "12345678", "NaBocaDoSol@gmail.com");
        Usuario u6 = new Usuario("154613514-58", "Idris Muhammad", "87654321", "pieceofmind@gmail.com");
        Usuario u7 = new Usuario("128516458-82", "Alice no pais", "87654321", "paisdasmaravilhas@gmail.com");
        UsuarioDAO dao = new UsuarioDAO();
        dao.inserir(u1);
        dao.inserir(u2);
//        dao.inserir(u3);
//        dao.inserir(u4);
//        dao.inserir(u5);
//        dao.inserir(u6);
        System.out.println("\n--- LISTANDO TODOS OS USUÁRIOS APÓS INSERÇÃO ---");
        List<Usuario> listaDeUsuarios = dao.listarTodos();
        for (Usuario u : listaDeUsuarios) {
            System.out.println(u);
        }
        System.out.println("\n--- RELIZADAS OPERACOES DO DAO ---");
        dao.excluir(u2);
        dao.editar(new Usuario("123456789-10", "Alice Coltrane.", "25122025", "turiyaeramakrishna@gmail.com", "551234567"));
        System.out.println("\n--DEPOIS DE EXCLUIR ITAMAR E EDITAR ALICE");
        listaDeUsuarios = dao.listarTodos();
        for (Usuario u : listaDeUsuarios) {
            System.out.println(u);
        }
        System.out.println("\n--- ADICIONADO MAIS USERS E PESQUISANDO USUARIO ---");
        dao.inserir(u3);
        dao.inserir(u4);
        dao.inserir(u7);
        listaDeUsuarios = dao.pesquisar("Alice");
        for (Usuario u : listaDeUsuarios) {
            System.out.println(u);
            System.out.println();
        }
    }
}
