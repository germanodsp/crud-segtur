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
        // Validação de dados para garantir que a reserva seja válida
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

    public void confirmar() {
        if (this.status == StatusReserva.PENDENTE) {
            this.setStatus(StatusReserva.CONFIRMADA);
            System.out.println("Reserva " + idReserva + " confirmada com sucesso.");
        } else {
            System.out.println("Ação não permitida: a reserva não está com o status PENDENTE.");
        }
    }

    public void cancelar() {
        if (this.status == StatusReserva.PENDENTE || this.status == StatusReserva.CONFIRMADA) {
            this.setStatus(StatusReserva.CANCELADA);
            System.out.println("Reserva " + idReserva + " cancelada.");
            // Aqui poderia entrar a lógica para devolver a vaga ao pacote.
            // pacote.devolverVaga();
        } else {
            System.out.println("Ação não permitida: a reserva já está cancelada.");
        }
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