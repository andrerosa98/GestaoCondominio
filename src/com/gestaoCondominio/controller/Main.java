package com.gestaoCondominio.controller;

import com.gestaoCondominio.service.CadastroUsuario;
import com.gestaoCondominio.service.Menu;

import java.sql.SQLException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws SQLException {
        Scanner input = new Scanner(System.in);
        int opcao;

        do {
            Menu.inicial();
            opcao = input.nextInt();
            System.out.println("------------------------");
            switch (opcao) {
                case 1:
                    Menu.identificaUsuario();
                    break;
                case 2:

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
