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
        // Validação dos dados para garantir a integridade do objeto
        if (nome == null || nome.isBlank()) {
            throw new IllegalArgumentException("Nome do pacote não pode ser vazio.");
        }
        if (destino == null) {
            throw new IllegalArgumentException("Destino não pode ser nulo.");
        }
        if (preco <= 0) {
            throw new IllegalArgumentException("Preço deve ser um valor positivo.");
        }
        if (dataFim.before(dataInicio)) {
            throw new IllegalArgumentException("Data final não pode ser anterior à data inicial.");
        }
        if (vagasDisponiveis < 0) {
            throw new IllegalArgumentException("Número de vagas não pode ser negativo.");
        }
        this.id = id;
        this.nome = nome;
        this.destino = destino;
        this.preco = preco;
        this.dataInicio = dataInicio;
        this.dataFim = dataFim;
        this.itinerario = itinerario;
        this.vagasDisponiveis = vagasDisponiveis;
    }

    public Pacote(int id, String nome, Destino destino, double preco, Date dataInicio, Date dataFim, int vagasDisponiveis) {
        // "this(...)" chama o outro construtor desta mesma classe
        this(id, nome, destino, preco, dataInicio, dataFim, "Itinerário a ser definido.", vagasDisponiveis);
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
        if (nome == null || nome.isBlank()) {
            throw new IllegalArgumentException("Nome do pacote não pode ser vazio.");
        }
        this.nome = nome;
    }

    public Destino getDestino() {
        return destino;
    }

    public void setDestino(Destino destino) {
        if (destino == null) {
            throw new IllegalArgumentException("Destino não pode ser nulo.");
        }
        this.destino = destino;
    }

    public double getPreco() {
        return preco;
    }

    public void setPreco(double preco) {
        if (preco <= 0) {
            throw new IllegalArgumentException("Preço deve ser um valor positivo.");
        }
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
        if (vagasDisponiveis < 0) {
            throw new IllegalArgumentException("Número de vagas não pode ser negativo.");
        }
        this.vagasDisponiveis = vagasDisponiveis;
    }

    /**
     * Método para decrementar o número de vagas ao fazer uma reserva.
     * Retorna true se a operação foi bem-sucedida, false se não havia vagas.
     */
    public void reservarVaga() {
        if (this.vagasDisponiveis > 0) {
            this.vagasDisponiveis--;
        } else {
            // Lançar uma exceção é melhor do que retornar um boolean,
            // pois torna o erro mais explícito e difícil de ser ignorado.
            //
            throw new IllegalStateException("Não há vagas disponíveis para este pacote.");
        }
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