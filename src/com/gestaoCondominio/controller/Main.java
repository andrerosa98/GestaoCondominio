package com.gestaoCondominio.controller;

import com.gestaoCondominio.service.CadastroUsuario;

import java.util.Scanner;
import java.io.Console;

public class Main {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        Console console = System.console();

        System.out.println("Cadastro de Usuário");

        System.out.print("Nome: ");
        String nome = input.nextLine();

        System.out.print("Email: ");
            String email = input.nextLine();
                while (!com.gestaoCondominio.util.Verificar.verificarEmail(email)) {
                    System.out.print("Email inválido. Digite novamente: ");
                    email = input.nextLine();
                }

        String senha;
        if (console != null) {
            char[] senhaArray = console.readPassword("Senha: ");
            senha = new String(senhaArray);
        } else {
            System.out.print("Senha (não seguro, console não disponível): ");
            senha = input.nextLine();
        }

        System.out.print("CPF (Somente números): ");
            String cpf = input.nextLine();
                while (!com.gestaoCondominio.util.Verificar.verificarCPF(cpf)) {
                    System.out.print("CPF inválido. Digite novamente: ");
                    cpf = input.nextLine();
                }

        System.out.print("Data de Nascimento (YYYY-MM-DD): ");
        String dataNascimento = input.nextLine();

        CadastroUsuario.cadastrarUsuario(nome, email, senha, cpf, dataNascimento);
    }
}
