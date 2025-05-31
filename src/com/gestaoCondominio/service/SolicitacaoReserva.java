package com.gestaoCondominio.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class SolicitacaoReserva {
    private int id;
    private String area;
    private LocalDateTime dataHora;
    private int duracao;

    public SolicitacaoReserva(int id, String area, LocalDateTime dataHora, int duracao) {
        this.id = id;
        this.area = area;
        this.dataHora = dataHora;
        this.duracao = duracao;
    }

    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEEE, d 'de' MMMM 'de' yyyy 'às' HH:mm", new Locale("pt", "BR"));
        String dataHoraFormatada = dataHora.format(formatter);

        return "Área: " + area + "\nData e hora: " + dataHoraFormatada +
                "\nDuração: " + duracao + " hora(s)";
    }
}
