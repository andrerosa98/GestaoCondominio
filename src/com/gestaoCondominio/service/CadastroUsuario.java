package com.gestaoCondominio.service;

import java.io.Console;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;
import org.mindrot.jbcrypt.BCrypt; //novo


public class CadastroUsuario {
    public static void cadastrarUsuario(String nome, String email, String senha, String cpf, String dataNascimentoFinal) {
        String sql = "INSERT INTO usuarios (nome, email, senha, cpf, data_nascimento) VALUES (?, ?, ?, ?, ?)";

        try (Connection conexao = ConexaoBD.getConexao();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setString(1, nome);
            stmt.setString(2, email);
            stmt.setString(3, senha);
            stmt.setString(4, cpf);
            stmt.setInt(4, numAp);
            stmt.setString(5, dataNascimentoFinal);

            stmt.executeUpdate();
            System.out.println("Usuário cadastrado com sucesso!");
        } catch (SQLException erro) {
            System.err.println("Erro ao cadastrar usuário: " + erro.getMessage());
        }
    }

    public static void Cadastro() {

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

        System.out.print("Email: ");
        email = input.nextLine();
        while (!com.gestaoCondominio.util.Verificar.verificarEmail(email)) {
            System.out.print("Email inválido. Digite novamente: ");
            email = input.nextLine();
        }

        if (console != null) {
            char[] senhaArray = console.readPassword("Senha: ");            
            senha = new String(senhaArray);
            String hash = BCrypt.hashpw(senha, BCrypt.gensalt()); //novo
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

        CadastroUsuario.cadastrarUsuario(nome, email, senha, cpf, numAp, dataNascimentoFinal.toString());
    }

    public static void Continuar(){
        Scanner input = new Scanner(System.in);
        System.out.print("Deseja cadastrar outro usuário? (1 - Sim, 0 - Não): ");
        int continuar = input.nextInt();
        while (continuar == 1) {
            CadastroUsuario.Cadastro();
            System.out.print("Deseja cadastrar outro usuário? (1 - Sim, 0 - Não): ");
            continuar = input.nextInt();
        }
    }
}
