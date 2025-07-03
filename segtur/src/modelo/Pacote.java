package modelo;

import java.util.Date;

public class Pacote {
    private int id;
    private String destino;
    private double preco;
    private Date dataInicio;
    private Date dataFim;

    public Pacote() {}

    public Pacote(int id, String destino, double preco, Date dataInicio, Date dataFim) {
        this.id = id;
        this.destino = destino;
        this.preco = preco;
        this.dataInicio = dataInicio;
        this.dataFim = dataFim;
    }

    // Getters e Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDestino() {
        return destino;
    }

    public void setDestino(String destino) {
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
    
    @Override
    public String toString() {
        return "Pacote{id=" + id + ", destino='" + destino + '\'' +
               ", preco=" + preco + ", dataInicio=" + dataInicio +
               ", dataFim=" + dataFim + '}';
    }


//public static void main(String[] args) {
//    // Substitua os valores pelos valores corretos para o pacote
//    int id = 1;
//    String destino = "Brasil";
//    double preco = 1500.0;
//    Date dataInicio = new Date(); // Data de início, pode ser ajustada
//    Date dataFim = new Date(System.currentTimeMillis() + 7 * 24 * 60 * 60 * 1000); // Exemplo: fim daqui 7 dias
//
//    // Criação do objeto Pacote com os valores
//    Pacote p = new Pacote(id, destino, preco, dataInicio, dataFim);
//
//    // Exibe os dados do objeto para verificar resultado
//    System.out.println(p);
//}

}