package com.gestaoCondominio.service;
import com.gestaoCondominio.controller.Menu;
import com.gestaoCondominio.model.Usuario;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Scanner;
import java.sql.SQLException;

public class ReservaAreaComum {
    public static void realizarReserva(String tipoUsuario, Usuario usuarioLogado) throws SQLException {
        int opcaoArea;
        int ano;
        int mes;
        int dia;
        int idArea;
        int idUsuario = usuarioLogado.getId();
        int espacamento;
        String areaEscolhida;

        Scanner dado = new Scanner(System.in);

        System.out.println("-------------------------");
        System.out.println("Áreas disponíveis:");
        System.out.println("1 - Salão de Festas");
        System.out.println("2 - Churrasqueira");
        System.out.println("3 - Quadra");
        System.out.print("Escolha uma área (1-3): ");
        opcaoArea = dado.nextInt();
        dado.nextLine();

        idArea = opcaoArea;
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


        espacamento = (diaSemana.getValue() % 7);

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

        LocalDate dataReserva = LocalDate.of(ano, mes, dia);

        if (reservaDuplicada(idUsuario, dataReserva)) {
            System.out.println("Você já possui uma reserva para esta data. Reserva não realizada.");
            voltarAoMenu(tipoUsuario);
            return;
        }

        inserirReserva(idUsuario, idArea, dataReserva);
        System.out.println("Reserva realizada com sucesso para " + areaEscolhida + " no dia " + dia + " de " + nomeMes + " de " + ano + ".");
        System.out.println("Aguardar aprovação do sindico para liberação da reserva.");
        voltarAoMenu(tipoUsuario);
    }

    private static boolean reservaDuplicada(int idUsuario, LocalDate dataReserva) {
        String sql = "SELECT COUNT(*) FROM reservas WHERE id_usuario = ? AND DATE(data_reserva) = ?";
        try (Connection conexao = ConexaoBD.getConexao();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setInt(1, idUsuario);
            stmt.setDate(2, java.sql.Date.valueOf(dataReserva));
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            System.err.println("Erro ao verificar duplicidade de reserva: " + e.getMessage());
        }
        return false;
    }

    private static void inserirReserva(int idUsuario, int idArea, LocalDate dataReserva) {
        String sql = "INSERT INTO reservas (id_usuario, id_area, data_reserva, status_reserva) VALUES (?, ?, ?, 'pendente')";
        try (Connection conexao = ConexaoBD.getConexao();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setInt(1, idUsuario);
            stmt.setInt(2, idArea);
            stmt.setDate(3, java.sql.Date.valueOf(dataReserva));
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Erro ao inserir reserva: " + e.getMessage());
        }
    }

    private static void voltarAoMenu(String tipoUsuario) throws SQLException {
        if ("condomino".equals(tipoUsuario)) {
            Menu.condomino(null);
        } else if ("sindico".equals(tipoUsuario)) {
            Menu.sindico(null);
        }
    }

    public static void aprovarReservasPendentes() throws SQLException {
        String sql = "SELECT r.id_reserva, u.nome, a.nome_area, r.data_reserva " +
                "FROM reservas r " +
                "JOIN usuarios u ON r.id_usuario = u.id_usuario " +
                "JOIN areas_comuns a ON r.id_area = a.id_area " +
                "WHERE r.status_reserva = 'pendente'";
        try (Connection conexao = ConexaoBD.getConexao();
             PreparedStatement stmt = conexao.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            Scanner scanner = new Scanner(System.in);
            boolean encontrou = false;
            while (rs.next()) {
                encontrou = true;
                int idReserva = rs.getInt("id_reserva");
                String nomeUsuario = rs.getString("nome");
                String nomeArea = rs.getString("nome_area");
                String dataReserva = rs.getString("data_reserva");

                System.out.printf("Reserva #%d - %s - %s em %s\n", idReserva, nomeUsuario, nomeArea, dataReserva);
                System.out.print("Aprovar (1) / Rejeitar (2) / Pular (0): ");
                int opcao = scanner.nextInt();
                if (opcao == 1) {
                    atualizarStatusReserva(idReserva, "aprovada");
                    System.out.println("Reserva aprovada.");
                } else if (opcao == 2) {
                    atualizarStatusReserva(idReserva, "rejeitada");
                    System.out.println("Reserva rejeitada.");
                }
            }
            if (!encontrou) {
                System.out.println("Não há reservas pendentes para aprovação.");
            }
        }
    }

    private static void atualizarStatusReserva(int idReserva, String status) throws SQLException {
        String sql = "UPDATE reservas SET status_reserva = ? WHERE id_reserva = ?";
        try (Connection conexao = ConexaoBD.getConexao();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setString(1, status);
            stmt.setInt(2, idReserva);
            stmt.executeUpdate();
        }
    }
}
