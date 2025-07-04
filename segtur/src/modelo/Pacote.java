package modelo;

import java.util.Date;
import java.util.Objects;

public class Pacote {
    private int id;
    private String nome;
    private Destino destino;
    private double preco;
    private Date dataInicio;
    private Date dataFim;
    private String itinerario;
    private int vagasDisponiveis; //

    public Pacote(int id, String nome, Destino destino, double preco, Date dataInicio, Date dataFim, String itinerario, int vagasDisponiveis) {
        this.id = id;
        this.nome = nome;
        this.destino = destino;
        this.preco = preco;
        this.dataInicio = dataInicio;
        this.dataFim = dataFim;
        this.itinerario = itinerario;
        this.vagasDisponiveis = vagasDisponiveis;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Destino getDestino() {
        return destino;
    }

    public void setDestino(Destino destino) {
        this.destino = destino;
    }

    public double getPreco() {
        return preco;
    }

    public void setPreco(double preco) {
        this.preco = preco;
    }

    public Date getDataInicio() {
        return dataInicio;
    }

    public void setDataInicio(Date dataInicio) {
        this.dataInicio = dataInicio;
    }

    public Date getDataFim() {
        return dataFim;
    }

    public void setDataFim(Date dataFim) {
        this.dataFim = dataFim;
    }

    public String getItinerario() {
        return itinerario;
    }

    public void setItinerario(String itinerario) {
        this.itinerario = itinerario;
    }

    public int getVagasDisponiveis() {
        return vagasDisponiveis;
    }

    public void setVagasDisponiveis(int vagasDisponiveis) {
        this.vagasDisponiveis = vagasDisponiveis;
    }

    /**
     * Método para decrementar o número de vagas ao fazer uma reserva.
     * Retorna true se a operação foi bem-sucedida, false se não havia vagas.
     */
    public boolean reservarVaga() {
        if (this.vagasDisponiveis > 0) {
            this.vagasDisponiveis--;
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return "Pacote: " + nome + " (ID: " + id + ")\n" +
                "Destino: " + destino.nome() + "\n" +
                "Preço: R$" + preco + "\n" +
                "Data: " + dataInicio + " a " + dataFim + "\n" +
                "Vagas restantes: " + vagasDisponiveis + "\n" +
                "Itinerário: " + itinerario;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Pacote pacote = (Pacote) o;
        return id == pacote.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}