package com.gestaoCondominio.service;//package com.gestaoCondominio.service;

import java.io.Console;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;
import com.gestaoCondominio.model.Usuario;



public class CadastroUsuario {
    public static void cadastrarUsuario(String nome, String email, String senha, String cpf, String dataNascimentoFinal) {
        String sql = "INSERT INTO usuarios (nome, email, senha, cpf, data_nascimento) VALUES (?, ?, ?, ?, ?)";

        try (Connection conexao = ConexaoBD.getConexao();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setString(1, nome);
            stmt.setString(2, email);
            stmt.setString(3, senha);
            stmt.setString(4, cpf);
            stmt.setString(5, dataNascimentoFinal);

            stmt.executeUpdate();
            System.out.println("Usuário cadastrado com sucesso!");
        } catch (SQLException erro) {
            System.err.println("Erro ao cadastrar usuário: " + erro.getMessage());
        }
    }

    public static void cadastro() {

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
            //String hash = BCrypt.hashpw(senha, BCrypt.gensalt()); //novo
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

        System.out.print("Número do apartamento: ");
        numAp = input.nextInt();

        while(dataNascimentoFinal == null){
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

        CadastroUsuario.cadastrarUsuario(nome, email, senha, cpf, dataNascimentoFinal.toString());
    }

    public static void continuar(){
        Scanner input = new Scanner(System.in);
        System.out.print("Deseja cadastrar outro usuário? (1 - Sim, 0 - Não): ");
        int continuar = input.nextInt();
        while (continuar == 1) {
            CadastroUsuario.cadastro();
            System.out.print("Deseja cadastrar outro usuário? (1 - Sim, 0 - Não): ");
            continuar = input.nextInt();
        }
    }

    public static void continuarNovo(){
        Scanner input = new Scanner(System.in);
        System.out.print("Deseja cadastrar um novo usuário? (1 - Sim, 0 - Não): ");
        int continuar = input.nextInt();
        while (continuar == 1) {
            CadastroUsuario.cadastro();
            System.out.print("Deseja cadastrar outro usuário? (1 - Sim, 0 - Não): ");
            continuar = input.nextInt();
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
            //String hash = BCrypt.hashpw(senha, BCrypt.gensalt()); //novo
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

        while(dataNascimentoFinal == null){
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

        CadastroUsuario.cadastrarMoradorNoApartamento(nome, email, senha, cpf, dataNascimentoFinal.toString(), idApartamento, tipoMorador);
    }

    public static void cadastroPorCondomino( Usuario usuarioLogado){
        if (usuarioLogado == null){
            System.out.println("Erro: Usuário não logado.");
            return;
        }

        Scanner input = new Scanner(System.in);

        int idApartamento = obterApartamentoDoUsuario(usuarioLogado.getId());
        if (idApartamento == 0){
            System.out.println("Erro: Apartamento não encontrado para o usuário logado.");
            return;
        }

        System.out.println("Cadastro de novo morador para o apartamento " + idApartamento);
        CadastroUsuario.cadastroDependente(idApartamento);
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
        Connection conexao = null;
        try {
            conexao = ConexaoBD.getConexao();
            conexao.setAutoCommit(false);

            // Cadastrar o usuário
            String sqlUsuario = "INSERT INTO usuarios (nome, email, senha, cpf, tipo_usuario) VALUES (?, ?, ?, ?, 'condomino')";
            PreparedStatement stmtUsuario = conexao.prepareStatement(sqlUsuario, PreparedStatement.RETURN_GENERATED_KEYS);
            stmtUsuario.setString(1, nome);
            stmtUsuario.setString(2, email);
            stmtUsuario.setString(3, senha);
            stmtUsuario.setString(4, cpf);
            stmtUsuario.executeUpdate();

            ResultSet rs = stmtUsuario.getGeneratedKeys();
            int idUsuario = 0;
            if (rs.next()) {
                idUsuario = rs.getInt(1);
            }

            // Relacionar usuário ao apartamento
            String sqlRelacao = "INSERT INTO usuarios_apartamentos (id_usuario, id_apartamento, tipo_morador) VALUES (?, ?, ?)";
            PreparedStatement stmtRelacao = conexao.prepareStatement(sqlRelacao);
            stmtRelacao.setInt(1, idUsuario);
            stmtRelacao.setInt(2, idApartamento);
            stmtRelacao.setString(3, tipoMorador);
            stmtRelacao.executeUpdate();

            // Atualizar a data de nascimento do usuário
            String sqlDataNascimento = "UPDATE usuarios SET data_nascimento = ? WHERE id_usuario = ?";
            PreparedStatement stmtDataNascimento = conexao.prepareStatement(sqlDataNascimento);
            stmtDataNascimento.setString(1, dataNascimentoFinal);
            stmtDataNascimento.setInt(2, idUsuario);
            stmtDataNascimento.executeUpdate();

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

    public static int getIdUsuario(String email) {
        String sql = "SELECT id_usuario FROM usuarios WHERE email = ?";
        try (Connection conexao = ConexaoBD.getConexao();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("id_usuario");
            }
        } catch (SQLException erro) {
            System.err.println("Erro ao buscar ID do usuário: " + erro.getMessage());
        }
        return 0;
    }
}
