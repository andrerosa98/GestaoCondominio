package main.java.com.gestaoCondominio;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        System.out.println("Cadastro de Usuário");
        System.out.print("Nome: ");
        String nome = input.nextLine();
        System.out.print("Email: ");
        String email = input.nextLine();
        System.out.print("Senha: ");
        String senha = input.nextLine();
        System.out.print("CPF (Somente números: ");
        String cpf = input.nextLine();
        System.out.print("Data de Nascimento (YYYY-MM-DD): ");
        String dataNascimento = input.nextLine();

        CadastroUsuario.cadastrarUsuario(nome, email, senha, cpf, dataNascimento);
    }
}
