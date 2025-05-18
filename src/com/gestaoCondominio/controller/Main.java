package com.gestaoCondominio.controller;

import com.gestaoCondominio.service.CadastroUsuario;

import java.util.Scanner;
import java.io.Console;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

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

        LocalDate dataNascimentoFinal = null;
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
}
