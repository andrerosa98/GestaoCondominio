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

public class AgendarMudanca {
    public static void realizarReserva(String tipoUsuario, Usuario usuarioLogado) throws SQLException {
        //int opcaoArea;
        int ano;
        int mes;
        int dia;
        int idArea;
        int idMudanca = usuarioLogado.getId();
        int espacamento;
        //String areaEscolhida;

        Scanner dado = new Scanner(System.in);

        /*System.out.println("-------------------------");
        System.out.println("Áreas disponíveis:");
        System.out.println("1 - Salão de Festas");
        System.out.println("2 - Churrasqueira");
        System.out.println("3 - Quadra");
        System.out.print("Escolha uma área (1-3): ");
        opcaoArea = dado.nextInt();
        dado.nextLine(); */

        System.out.println("Informe a seguir a data para a qual gostaria de agenda a sua mudança: ");

        /*idArea = opcaoArea;
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
        } */

        System.out.print("Informe o ano da mudança (ex: 2025): ");
        ano = dado.nextInt();

        System.out.print("Informe o mês da mudança (1-12): ");
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

        System.out.print("Informe o dia da mudança: ");
        dia = dado.nextInt();

        LocalDate data_mudanca = LocalDate.of(ano, mes, dia);

        if (mudancaDuplicada(idMudanca, data_mudanca)) {
            System.out.println("Você já possui uma mudança agendada para esta data. Agendamento não realizado.");
            voltarAoMenu(tipoUsuario);
            return;
        }

        inserirMudanca(idMudanca, data_mudanca);
        System.out.println("Solicitação de agendamento de mudança realizado com sucesso para o dia " + dia + " de " + nomeMes + " de " + ano + ".");
        System.out.println("Aguardar aprovação do síndico.");
        voltarAoMenu(tipoUsuario);
    }

    private static boolean mudancaDuplicada(int idMudanca, LocalDate data_mudanca) {
        String sql = "SELECT COUNT(*) FROM mudancas WHERE id_mudanca = ? AND DATE(data_mudanca) = ?";
        try (Connection conexao = ConexaoBD.getConexao();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setInt(1, idMudanca);
            stmt.setDate(2, java.sql.Date.valueOf(data_mudanca));
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            System.err.println("Erro ao verificar duplicidade de agendamento: " + e.getMessage());
        }
        return false;
    }

    private static void inserirMudanca(int idMudanca, LocalDate data_mudanca) {
        String sql = "INSERT INTO mudancas (id_mudanca, data_mudanca, status_mudanca) VALUES (?, ?, 'pendente')";
        try (Connection conexao = ConexaoBD.getConexao();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setInt(1, idMudanca);
            //stmt.setInt(2, idArea);
            stmt.setDate(3, java.sql.Date.valueOf(data_mudanca));
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Erro ao inserir agendamento: " + e.getMessage());
        }
    }

    private static void voltarAoMenu(String tipoUsuario) throws SQLException {
        if ("condomino".equals(tipoUsuario)) {
            Menu.condomino(null);
        } else if ("sindico".equals(tipoUsuario)) {
            Menu.sindico(null);
        }
    }

    public static void aprovarMudancasPendentes() throws SQLException {
        String sql = "SELECT r.id_mudanca, u.nome, r.data_mudanca " +
                "FROM mudancas r " +
                "JOIN usuarios u ON r.id_mudanca = u.id_mudanca " +
                //"JOIN areas_comuns a ON r.id_area = a.id_area " +
                "WHERE r.status_mudanca = 'pendente'";
        try (Connection conexao = ConexaoBD.getConexao();
             PreparedStatement stmt = conexao.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            Scanner scanner = new Scanner(System.in);
            boolean encontrou = false;
            while (rs.next()) {
                encontrou = true;
                int idMudanca = rs.getInt("id_mudanca");
                String nomeUsuario = rs.getString("nome");
                //String nomeArea = rs.getString("nome_area");
                String data_mudanca = rs.getString("data_mudanca");

                System.out.printf("Mudança #%d - %s - %s em %s\n", idMudanca, nomeUsuario, data_mudanca);
                System.out.print("Aprovar (1) / Rejeitar (2) / Pular (0): ");
                int opcao = scanner.nextInt();
                if (opcao == 1) {
                    atualizarStatusMudanca(idMudanca, "aprovada");
                    System.out.println("Mudança aprovada.");
                } else if (opcao == 2) {
                    atualizarStatusMudanca(idMudanca, "rejeitada");
                    System.out.println("Mudança rejeitada.");
                }
            }
            if (!encontrou) {
                System.out.println("Não há mudanças pendentes para aprovação.");
            }
        }
    }

    private static void atualizarStatusMudanca(int idMudanca, String status) throws SQLException {
        String sql = "UPDATE mudancas SET status_mudanca = ? WHERE id_mudanca = ?";
        try (Connection conexao = ConexaoBD.getConexao();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setString(1, status);
            stmt.setInt(2, idMudanca);
            stmt.executeUpdate();
        }
    }
}
