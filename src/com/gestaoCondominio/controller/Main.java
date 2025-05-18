package com.gestaoCondominio.controller;

import com.gestaoCondominio.service.CadastroUsuario;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        Scanner input = new Scanner(System.in);
        int opcao;

        System.out.println("Sistema GestCondo");
        System.out.println("Menu");
        System.out.println("1 - Cadastrar Usuário");
        System.out.println("0 - Sair");
        System.out.println("Digite a opção desejada: ");
        opcao = input.nextInt();

        switch (opcao) {
            case 1:
                CadastroUsuario.Cadastro();
                break;
            case 0:
                System.out.println("Saindo...");
                break;
            default:
                System.out.println("Opção inválida. Tente novamente.");
        }
    }
}
