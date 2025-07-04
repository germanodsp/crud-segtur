package modelo;

import java.util.Date;
import java.util.Objects;

/**
 * contem todas as informacoes necessarias para reserva
 */

public class Reserva {
    private int idReserva;
    private Usuario usuario;
    private Pacote pacote;
    private Date dataReserva;
    private Date dataLimiteCancelamento;
    private StatusReserva status;

    /**
     * criado um construtor unico pois é necessario todos os dados para uma reserva completa
     * @param usuario
     * @param pacote
     * @param dataReserva
     * @param dataLimiteCancelamento
     * @param status
     */

    public Reserva(Usuario usuario, Pacote pacote, Date dataReserva, Date dataLimiteCancelamento, StatusReserva status) {
        if (usuario == null) {
            throw new IllegalArgumentException("O usuário não pode ser nulo.");
        }
        if (pacote == null) {
            throw new IllegalArgumentException("O pacote não pode ser nulo.");
        }
        if (dataLimiteCancelamento.before(dataReserva)) {
            throw new IllegalArgumentException("A data limite de cancelamento não pode ser anterior à data da reserva.");
        }

        this.usuario = usuario;
        this.pacote = pacote;
        this.dataReserva = dataReserva;
        this.dataLimiteCancelamento = dataLimiteCancelamento;
        this.status = status;
    }


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
        if (usuario == null) {
            throw new IllegalArgumentException("O usuário não pode ser nulo.");
        }
        this.usuario = usuario;
    }

    public Pacote getPacote() {
        return pacote;
    }

    public void setPacote(Pacote pacote) {
        if (pacote == null) {
            throw new IllegalArgumentException("O pacote não pode ser nulo.");
        }
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

    private void setStatus(StatusReserva status) {
        this.status = status;
    }

    /**
     * altera o status de acordo com o enum (StatusReserva) para pendente e confirmada
     */

    public void confirmar() {
        if (this.status == StatusReserva.PENDENTE) {
            this.setStatus(StatusReserva.CONFIRMADA);
            System.out.println("Reserva " + idReserva + " confirmada com sucesso.");
        } else {
            System.out.println("Ação não permitida: a reserva não está com o status PENDENTE.");
        }
    }

    /**
     * altera o status de acordo com o enum (StatusReserva) para cancelado
     */

    public void cancelar() {
        if (this.status == StatusReserva.PENDENTE || this.status == StatusReserva.CONFIRMADA) {
            this.setStatus(StatusReserva.CANCELADA);
            System.out.println("Reserva " + idReserva + " cancelada.");
        } else {
            System.out.println("Ação não permitida: a reserva já está cancelada.");
        }
    }

    @Override
    public String toString() {
        return "idReserva=" + idReserva + "\n" +
                "usuario=" + usuario.nome() + "\n" +
                "pacote=" + pacote.getDestino().nome() + "\n" +
                "dataReserva=" + dataReserva + "\n" +
                "status=" + status;
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