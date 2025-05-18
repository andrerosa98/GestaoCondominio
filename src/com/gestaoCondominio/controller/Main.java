package com.gestaoCondominio.controller;

import com.gestaoCondominio.service.CadastroUsuario;
import com.gestaoCondominio.service.Menu;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        int opcao;

        do {
            Menu.inicial();
            opcao = input.nextInt();
            System.out.println("------------------------");
            switch (opcao) {
                case 1:
                    CadastroUsuario.Cadastro();
                    CadastroUsuario.Continuar();
                    break;
                case 0:
                    System.out.println("Saindo...");
                    break;
                default:
                    System.out.println("Opção inválida. Tente novamente.");
            }
        } while (opcao != 0);
    }

}
