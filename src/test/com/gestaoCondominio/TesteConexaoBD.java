package com.gestaoCondominio;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class TesteConexaoBD {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Solicita o endereço do servidor do banco de dados
        System.out.print("Informe o endereço do servidor (exemplo: localhost:3306): ");
        String serverAddress = scanner.nextLine();

        // Solicita o nome de usuário do banco de dados
        System.out.print("Informe o usuário do banco de dados: ");
        String user = scanner.nextLine();

        // Solicita a senha do banco de dados
        System.out.print("Informe a senha do banco de dados: ");
        String password = scanner.nextLine();

        // Monta a URL de conexão com o banco
        String dbUrl = "jdbc:mysql://" + serverAddress + "/";

        try (Connection connection = DriverManager.getConnection(dbUrl, user, password)) {
            connection.setAutoCommit(false); // Desativa o commit automático
            Statement statement = connection.createStatement();

            // Executa os comandos
            statement.executeUpdate("CREATE DATABASE IF NOT EXISTS condominio");
            statement.execute("USE condominio");

            statement.executeUpdate("""
                    CREATE TABLE usuarios (
                        id_usuario INT AUTO_INCREMENT PRIMARY KEY,
                        nome VARCHAR(100) NOT NULL,
                        email VARCHAR(100) NOT NULL UNIQUE,
                        senha VARCHAR(255) NOT NULL,
                        tipo_usuario ENUM('condomino', 'sindico') NOT NULL,
                        cpf VARCHAR(14) NOT NULL UNIQUE,
                        data_nascimento DATE,
                        data_cadastro DATETIME DEFAULT CURRENT_TIMESTAMP,
                        status_aprovacao ENUM('pendente', 'aprovado', 'reprovado') DEFAULT 'pendente'
                    ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
                    """);

            statement.executeUpdate("""
                    CREATE TABLE areas_comuns (
                        id_area INT AUTO_INCREMENT PRIMARY KEY,
                        nome_area VARCHAR(100) NOT NULL,
                        descricao TEXT,
                        disponibilidade BOOLEAN DEFAULT TRUE
                    ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
                    """);

            System.out.println("\nComandos executados, mas um rollback será realizado agora.");

            connection.rollback(); // Reverte as alterações
            System.out.println("Rollback realizado. Nenhuma alteração foi feita no banco de dados.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}