package com.gestaoCondominio.service;
import com.gestaoCondominio.controller.Menu;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Scanner;
import java.sql.SQLException;

public class ReservaAreaComum {
    public static void realizarReserva(String tipoUsuario) throws SQLException {
        int opcaoArea;
        int ano;
        int mes;
        int valorDiaSemana;
        int espacamento;
        int dia;
        int hora;
        int minuto;

        Scanner dado = new Scanner(System.in);

        System.out.println("-------------------------");
        System.out.println("Áreas disponíveis:");
        System.out.println("1 - Salão de Festas");
        System.out.println("2 - Churrasqueira");
        System.out.println("3 - Quadra");
        System.out.print("Escolha uma área (1-3): ");
        opcaoArea = dado.nextInt();
        dado.nextLine();

        String areaEscolhida;
        switch (opcaoArea) {
            case 1:
                areaEscolhida = "Salão de Festas";
                break;
            case 2:
                areaEscolhida = "Churrasqueira";
                break;
            case 3:
                areaEscolhida = "Quadra";
                break;
            default:
                System.out.println("Opção inválida. Reserva cancelada.");
                // Retorna ao menu apropriado após cancelar a reserva
                voltarAoMenu(tipoUsuario);
                return;
        }

        System.out.print("Informe o ano da reserva (ex: 2025): ");
        ano = dado.nextInt();

        System.out.print("Informe o mês da reserva (1-12): ");
        mes = dado.nextInt();

        YearMonth anoMes = YearMonth.of(ano, mes);
        int diasNoMes = anoMes.lengthOfMonth();
        LocalDate primeiroDia = anoMes.atDay(1);
        DayOfWeek diaSemana = primeiroDia.getDayOfWeek();

        // Formatando o nome do mês em português
        DateTimeFormatter mesFormatter = DateTimeFormatter.ofPattern("MMMM", new Locale("pt", "BR"));
        String nomeMes = primeiroDia.format(mesFormatter);

        System.out.println("\nCalendário de " + nomeMes + " " + ano);
        System.out.println("Dom Seg Ter Qua Qui Sex Sab");

        valorDiaSemana = diaSemana.getValue();
        espacamento = (valorDiaSemana % 7);

        for (int i = 0; i < espacamento; i++) {
            System.out.print("    ");
        }

        for (int d = 1; d <= diasNoMes; d++) {
            System.out.printf("%3d ", d);
            if ((d + espacamento) % 7 == 0) {
                System.out.println();
            }
        }
        System.out.println();

        System.out.print("Informe o dia da reserva: ");
        dia = dado.nextInt();

        System.out.print("Informe a hora da reserva (0-23): ");
        hora = dado.nextInt();

        System.out.print("Informe os minutos da reserva: ");
        minuto = dado.nextInt();

        System.out.print("Informe a duração da reserva (em horas): ");
        int duracao = dado.nextInt();

        LocalDateTime dataHoraReserva = LocalDateTime.of(ano, mes, dia, hora, minuto);

        SolicitacaoReserva reserva = new SolicitacaoReserva(1, areaEscolhida, dataHoraReserva, duracao);
        //falta adaptar o ID da reserva para cair no menu do sindico
        System.out.println("\nReserva solitada com sucesso:");
        System.out.println(reserva);
        System.out.println("\nAguardar aprovação do sindico para liberação da reserva.");

        // Retorna ao menu apropriado após concluir a reserva
        voltarAoMenu(tipoUsuario);
    }

    private static void voltarAoMenu(String tipoUsuario) throws SQLException {
        if ("condomino".equals(tipoUsuario)) {
            Menu.condomino(null);
        } else if ("sindico".equals(tipoUsuario)) {
            Menu.sindico();
        }
    }
}
