package com.gestaoCondominio.service;

import java.io.Console;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;
import com.gestaoCondominio.model.Usuario;
import org.mindrot.jbcrypt.BCrypt;

import static com.gestaoCondominio.model.Usuario.excluirUsuario;

public class CadastroUsuario {
    public static void cadastrarUsuario(String nome, String email, String senha, String cpf, String dataNascimentoFinal, int numeroApartamento, String tipoMorador) throws SQLException {
        LocalDate dataNasc = LocalDate.parse(dataNascimentoFinal); // yyyy-MM-dd
        int idade = Period.between(dataNasc, LocalDate.now()).getYears();
        String sql = "INSERT INTO usuarios (nome, email, senha, cpf, data_nascimento, tipo_usuario, idade, status_aprovacao) VALUES (?, ?, ?, ?, ?, 'condomino', ?, 'pendente')";

        try (Connection conexao = ConexaoBD.getConexao();
             PreparedStatement stmt = conexao.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {

            int idApartamento = garantirApartamento(numeroApartamento, conexao);

            String senhaHash = org.mindrot.jbcrypt.BCrypt.hashpw(senha, org.mindrot.jbcrypt.BCrypt.gensalt());
            stmt.setString(1, nome);
            stmt.setString(2, email);
            stmt.setString(3, senhaHash);
            stmt.setString(4, cpf);
            stmt.setString(5, dataNascimentoFinal);
            stmt.setInt(6, idade);
            stmt.executeUpdate();

            ResultSet rs = stmt.getGeneratedKeys();
            int idUsuario = 0;
            if (rs.next()) {
                idUsuario = rs.getInt(1);
            }
            if (idUsuario == 0) {
                System.err.println("Erro ao obter o ID do usuário cadastrado.");
                return;
            }
            String sqlRelacao = "INSERT INTO usuarios_apartamentos (id_usuario, id_apartamento, tipo_morador) VALUES (?, ?, ?)";
            try (PreparedStatement stmtRelacao = conexao.prepareStatement(sqlRelacao)) {
                stmtRelacao.setInt(1, idUsuario);
                stmtRelacao.setInt(2, idApartamento);
                stmtRelacao.setString(3, tipoMorador);
                stmtRelacao.executeUpdate();
            }

            System.out.println("Usuário cadastrado com sucesso!");
        } catch (SQLException erro) {
            System.err.println("Erro ao cadastrar usuário: " + erro.getMessage());
        }
    }

    private static int garantirApartamento(int numeroApartamento, Connection conexao) throws SQLException {
        String selectSql = "SELECT id_apartamento FROM apartamentos WHERE numero = ?";
        try (PreparedStatement selectStmt = conexao.prepareStatement(selectSql)) {
            selectStmt.setInt(1, numeroApartamento);
            ResultSet rs = selectStmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("id_apartamento");
            }
        }

        String insertSql = "INSERT INTO apartamentos (numero) VALUES (?)";
        try (PreparedStatement insertStmt = conexao.prepareStatement(insertSql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            insertStmt.setInt(1, numeroApartamento);
            insertStmt.executeUpdate();
            ResultSet rs = insertStmt.getGeneratedKeys();
            if (rs.next()) {
                return rs.getInt(1);
            }
        }
        throw new SQLException("Não foi possível criar ou obter o apartamento.");
    }
    public static void cadastro() throws SQLException {
        String nome;
        String email;
        String senha;
        String cpf;
        int numAp;
        LocalDate dataNascimentoFinal = null;

        Scanner input = new Scanner(System.in);
        Console console = System.console();

        System.out.println("Cadastro de Usuário");

        System.out.print("Nome: ");
        nome = input.nextLine();
        while (nome.isEmpty()) {
            System.out.print("Nome não pode ser vazio. Digite novamente: ");
            nome = input.nextLine();
        }

        System.out.print("Email: ");
        email = input.nextLine();
        while (!com.gestaoCondominio.util.Verificar.verificarEmail(email)) {
            System.out.print("Email inválido. Digite novamente: ");
            email = input.nextLine();
        }

        if (console != null) {
            char[] senhaArray = console.readPassword("Senha: ");
            senha = new String(senhaArray);
        } else {
            System.out.print("Senha (não seguro, console não disponível): ");
            senha = input.nextLine();
        }

        System.out.print("CPF (Somente números): ");
        cpf = input.nextLine();
        while (!com.gestaoCondominio.util.Verificar.verificarCPF(cpf)) {
            System.out.print("CPF inválido. Digite novamente: ");
            cpf = input.nextLine();
            input.nextLine();
        }

        System.out.print("Número do apartamento: ");
        while (!input.hasNextInt()) {
            System.out.print("Digite um número válido para o apartamento: ");
            input.next();
        }
        numAp = input.nextInt();
        input.nextLine(); // Limpa o buffer

        while (dataNascimentoFinal == null) {
            try {
                System.out.print("Data de Nascimento (dd/MM/yyyy): ");
                String dataNascimento = input.nextLine();
                DateTimeFormatter formatoEntrada = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                LocalDate dataNascimentoFormatada = LocalDate.parse(dataNascimento, formatoEntrada);
                if (dataNascimentoFormatada.isAfter(LocalDate.now())) {
                    System.out.println("Data de nascimento não pode ser futura.");
                } else {
                    dataNascimentoFinal = dataNascimentoFormatada;
                }
            } catch (DateTimeParseException erro) {
                System.out.println("Formato de data inválido! Por favor, use o formato dd/MM/yyyy.");
            }
        }

        System.out.println("Tipo de morador:");
        System.out.println("1 - Proprietário");
        System.out.println("2 - Dependente");
        int tipoMoradorOpcao = input.nextInt();
        input.nextLine();
        String tipoMorador = (tipoMoradorOpcao == 2) ? "dependente" : "proprietario";

        cadastrarUsuario(nome, email, senha, cpf, dataNascimentoFinal.toString(), numAp, tipoMorador);
    }

    public static void continuar() throws SQLException {
        Scanner input = new Scanner(System.in);
        System.out.print("Deseja cadastrar outro usuário? (1 - Sim, 0 - Não): ");
        int continuar = input.nextInt();
        input.nextLine();
        while (continuar == 1) {
            cadastro();
            System.out.print("Deseja cadastrar outro usuário? (1 - Sim, 0 - Não): ");
            continuar = input.nextInt();
            input.nextLine();
        }
    }

    public static void continuarNovo() throws SQLException {
        Scanner input = new Scanner(System.in);
        System.out.print("Deseja cadastrar um novo usuário? (1 - Sim, 0 - Não): ");
        int continuar = input.nextInt();
        input.nextLine();
        while (continuar == 1) {
            cadastro();
            System.out.print("Deseja cadastrar outro usuário? (1 - Sim, 0 - Não): ");
            continuar = input.nextInt();
            input.nextLine();
        }
    }

    public static void cadastroDependente(int idApartamento) {
        String nome;
        String email;
        String senha;
        String cpf;
        LocalDate dataNascimentoFinal = null;

        Scanner input = new Scanner(System.in);
        Console console = System.console();

        System.out.println("Cadastro de Usuário");

        System.out.print("Nome: ");
        nome = input.nextLine();
        while (nome.isEmpty()) {
            System.out.print("Nome não pode ser vazio. Digite novamente: ");
            nome = input.nextLine();
        }

        System.out.print("Email: ");
        email = input.nextLine();
        while (!com.gestaoCondominio.util.Verificar.verificarEmail(email)) {
            System.out.print("Email inválido. Digite novamente: ");
            email = input.nextLine();
        }

        if (console != null) {
            char[] senhaArray = console.readPassword("Senha: ");
            senha = new String(senhaArray);
        } else {
            System.out.print("Senha (não seguro, console não disponível): ");
            senha = input.nextLine();
        }

        System.out.print("CPF (Somente números): ");
        cpf = input.nextLine();
        while (!com.gestaoCondominio.util.Verificar.verificarCPF(cpf)) {
            System.out.print("CPF inválido. Digite novamente: ");
            cpf = input.nextLine();
        }

        while (dataNascimentoFinal == null) {
            try {
                System.out.print("Data de Nascimento (dd/MM/yyyy): ");
                String dataNascimento = input.nextLine();
                DateTimeFormatter formatoEntrada = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                LocalDate dataNascimentoFormatada = LocalDate.parse(dataNascimento, formatoEntrada);
                if (dataNascimentoFormatada.isAfter(LocalDate.now())) {
                    System.out.println("Data de nascimento não pode ser futura.");
                } else {
                    dataNascimentoFinal = dataNascimentoFormatada;
                }
            } catch (DateTimeParseException erro) {
                System.out.println("Formato de data inválido! Por favor, use o formato dd/MM/yyyy.");
            }
        }

        System.out.println("Tipo de morador:");
        System.out.println("1 - Proprietário");
        System.out.println("2 - Dependente");
        int tipoOpcao = input.nextInt();
        input.nextLine();

        String tipoMorador = (tipoOpcao == 1) ? "proprietario" : "dependente";

        cadastrarMoradorNoApartamento(nome, email, senha, cpf, dataNascimentoFinal.toString(), idApartamento, tipoMorador);
    }

    public static void cadastroPorCondomino(Usuario usuarioLogado) {
        if (usuarioLogado == null) {
            System.out.println("Erro: Usuário não logado.");
            return;
        }

        int idApartamento = obterApartamentoDoUsuario(usuarioLogado.getId());
        if (idApartamento == 0) {
            System.out.println("Erro: Apartamento não encontrado para o usuário logado.");
            return;
        }

        System.out.println("Cadastro de novo morador para o apartamento " + idApartamento);
        cadastroDependente(idApartamento);
    }

    private static int obterApartamentoDoUsuario(int idUsuario) {
        String sql = "SELECT id_apartamento FROM usuarios_apartamentos WHERE id_usuario = ?";

        try (Connection conexao = ConexaoBD.getConexao();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setInt(1, idUsuario);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return rs.getInt("id_apartamento");
            }
        } catch (SQLException erro) {
            System.err.println("Erro ao buscar apartamento do usuário: " + erro.getMessage());
        }

        return 0;
    }

    private static void cadastrarMoradorNoApartamento(String nome, String email, String senha,
                                                      String cpf, String dataNascimentoFinal, int idApartamento, String tipoMorador) {
        String sqlUsuario = "INSERT INTO usuarios (nome, email, senha, cpf, data_nascimento, tipo_usuario, idade) VALUES (?, ?, ?, ?, ?, 'condomino', ?)";
        String sqlRelacao = "INSERT INTO usuarios_apartamentos (id_usuario, id_apartamento, tipo_morador) VALUES (?, ?, ?)";
        Connection conexao = null;
        try {
            conexao = ConexaoBD.getConexao();
            conexao.setAutoCommit(false);

            String senhaHash = BCrypt.hashpw(senha, BCrypt.gensalt());

            try (PreparedStatement stmtUsuario = conexao.prepareStatement(sqlUsuario, PreparedStatement.RETURN_GENERATED_KEYS)) {
                stmtUsuario.setString(1, nome);
                stmtUsuario.setString(2, email);
                stmtUsuario.setString(3, senhaHash);
                stmtUsuario.setString(4, cpf);
                stmtUsuario.setString(5, dataNascimentoFinal);
                LocalDate dataNasc = LocalDate.parse(dataNascimentoFinal);
                int idade = Period.between(dataNasc, LocalDate.now()).getYears();
                stmtUsuario.setInt(6, idade);
                stmtUsuario.executeUpdate();

                ResultSet rs = stmtUsuario.getGeneratedKeys();
                int idUsuario = 0;
                if (rs.next()) {
                    idUsuario = rs.getInt(1);
                }

                try (PreparedStatement stmtRelacao = conexao.prepareStatement(sqlRelacao)) {
                    stmtRelacao.setInt(1, idUsuario);
                    stmtRelacao.setInt(2, idApartamento);
                    stmtRelacao.setString(3, tipoMorador);
                    stmtRelacao.executeUpdate();
                }
            }

            conexao.commit();
            System.out.println("Morador cadastrado com sucesso!");
        } catch (SQLException erro) {
            try {
                if (conexao != null) conexao.rollback();
            } catch (SQLException e) {
                System.err.println("Erro ao realizar rollback: " + e.getMessage());
            }
            System.err.println("Erro ao cadastrar morador: " + erro.getMessage());
        } finally {
            try {
                if (conexao != null) {
                    conexao.setAutoCommit(true);
                    conexao.close();
                }
            } catch (SQLException e) {
                System.err.println("Erro ao fechar conexão: " + e.getMessage());
            }
        }
    }

    public static void aprovarUsuariosPendentes() throws SQLException {
        String sql = "SELECT u.id_usuario, u.nome, u.email, a.numero AS apartamento " +
                "FROM usuarios u " +
                "JOIN usuarios_apartamentos ua ON u.id_usuario = ua.id_usuario " +
                "JOIN apartamentos a ON ua.id_apartamento = a.id_apartamento " +
                "WHERE u.status_aprovacao = 'pendente'";
        try (Connection conexao = ConexaoBD.getConexao();
             PreparedStatement stmt = conexao.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            Scanner scanner = new Scanner(System.in);
            boolean encontrou = false;
            while (rs.next()) {
                encontrou = true;
                int idUsuario = rs.getInt("id_usuario");
                String nome = rs.getString("nome");
                String email = rs.getString("email");
                int apartamento = rs.getInt("apartamento");

                System.out.printf("Usuário #%d - %s (%s) - Apartamento: %d\n", idUsuario, nome, email, apartamento);
                System.out.print("Aprovar (1) / Rejeitar (2) / Pular (0): ");
                int opcao = scanner.nextInt();
                if (opcao == 1) {
                    atualizarStatusUsuario(idUsuario, "aprovado");
                    System.out.println("Usuário aprovado.");
                } else if (opcao == 2) {
                    atualizarStatusUsuario(idUsuario, "reprovado");
                    excluirUsuario(idUsuario);
                    System.out.println("Usuário reprovado e excluído.");
                }
            }
            if (!encontrou) {
                System.out.println("Não há usuários pendentes para aprovação.");
            }
        }
    }

    private static void atualizarStatusUsuario(int idUsuario, String status) throws SQLException {
        String sql = "UPDATE usuarios SET status_aprovacao = ? WHERE id_usuario = ?";
        try (Connection conexao = ConexaoBD.getConexao();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setString(1, status);
            stmt.setInt(2, idUsuario);
            stmt.executeUpdate();
        }
    }
}