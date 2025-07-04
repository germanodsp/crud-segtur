package modelo;

import java.util.Date;
import java.util.Objects;

public class Reserva {
    private int idReserva;
    private Usuario usuario; // Referência para o objeto Usuario
    private Pacote pacote;   // Referência para o objeto Pacote
    private Date dataReserva;
    private Date dataLimiteCancelamento;
    private StatusReserva status; // Usa o Enum StatusReserva

    /**
     * Construtor para criar uma nova reserva.
     * O ID da reserva é gerado e atribuído pelo DAO.
     */
    public Reserva(Usuario usuario, Pacote pacote, Date dataReserva, Date dataLimiteCancelamento, StatusReserva status) {
        this.usuario = usuario;
        this.pacote = pacote;
        this.dataReserva = dataReserva;
        this.dataLimiteCancelamento = dataLimiteCancelamento;
        this.status = status;
    }

    // Getters e Setters
    public int getIdReserva() {
        return idReserva;
    }

    public void setIdReserva(int idReserva) {
        this.idReserva = idReserva;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Pacote getPacote() {
        return pacote;
    }

    public void setPacote(Pacote pacote) {
        this.pacote = pacote;
    }

    public Date getDataReserva() {
        return dataReserva;
    }

    public void setDataReserva(Date dataReserva) {
        this.dataReserva = dataReserva;
    }

    public Date getDataLimiteCancelamento() {
        return dataLimiteCancelamento;
    }

    public void setDataLimiteCancelamento(Date dataLimiteCancelamento) {
        this.dataLimiteCancelamento = dataLimiteCancelamento;
    }

    public StatusReserva getStatus() {
        return status;
    }

    public void setStatus(StatusReserva status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Reserva{" +
                "idReserva=" + idReserva +
                ", usuario=" + usuario.nome() +
                ", pacote=" + pacote.getDestino().nome() +
                ", dataReserva=" + dataReserva +
                ", status=" + status +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Reserva reserva = (Reserva) o;
        return idReserva == reserva.idReserva;
    }

    @Override
    public int hashCode() {
        return Objects.hash(idReserva);
    }
}