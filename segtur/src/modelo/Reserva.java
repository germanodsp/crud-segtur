package modelo;

import java.util.Date; // Import necessário

public class Reserva {
    private int idReserva;
    private Usuario usuario; // Referência para o objeto Usuario
    private Pacote pacote;   // Referência para o objeto Pacote
    private Date dataReserva;
    private Date dataLimiteCancelamento;
    private StatusReserva status; // Use o Enum que você criou

    // Construtor, Getters, Setters, toString()...
}