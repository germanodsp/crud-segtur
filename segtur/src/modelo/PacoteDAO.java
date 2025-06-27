package modelo;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PacoteDAO {

    private Connection connection;

    public PacoteDAO(Connection connection) {
        this.connection = connection;
    }

    public void insert(Pacote pacote) throws SQLException {
        String sql = "INSERT INTO pacotes (destino, preco, data_inicio, data_fim) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, pacote.getDestino());
            stmt.setDouble(2, pacote.getPreco());
            stmt.setDate(3, new java.sql.Date(pacote.getDataInicio().getTime()));
            stmt.setDate(4, new java.sql.Date(pacote.getDataFim().getTime()));
            stmt.executeUpdate();
        }
    }

    public List<Pacote> findAll() throws SQLException {
        List<Pacote> pacotes = new ArrayList<>();
        String sql = "SELECT * FROM pacotes";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Pacote pacote = new Pacote(
                    rs.getInt("id"),
                    rs.getString("destino"),
                    rs.getDouble("preco"),
                    rs.getDate("data_inicio"),
                    rs.getDate("data_fim")
                );
                pacotes.add(pacote);
            }
        }
        return pacotes;
    }
}