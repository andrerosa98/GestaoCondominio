package com.gestaoCondominio.controller;

import com.gestaoCondominio.model.Usuario;
import com.gestaoCondominio.service.*;

import java.io.Console;
import java.sql.SQLException;
import java.util.Scanner;

public class Menu {

    public static void inicial(){
        System.out.println("------------------------");
        System.out.println("Sistema GestCondo");
        System.out.println("Menu");
        System.out.println("1 - Login");
        System.out.println("2 - Mais informações");
        System.out.println("0 - Sair");
        System.out.print("Digite a opção desejada: ");
    }

    public static void identificaUsuario() throws SQLException {
        String email;
        String senha;
        Scanner dado = new Scanner(System.in);
        Console console = System.console();

        System.out.println("Bem-vindo ao sistema de gestão do condomínio");

        System.out.print("Digite o seu e-mail: ");
        email = dado.nextLine();
        if (console != null) {
            char[] senhaArray = console.readPassword("Senha: ");
            senha = new String(senhaArray);
        } else {
            System.out.print("Senha (não seguro, console não disponível): ");
            senha = dado.nextLine();
        }
        Usuario usuario = Login.loginUsuario(email, senha);
        if(usuario !=null)
        {
            if ("condomino".equals(usuario.getTipoUsuario())) {
                System.out.println("Bem-vindo, " + usuario.getNome() + "!");
                Menu.condomino(usuario);
            } else if ("sindico".equals(usuario.getTipoUsuario())) {
                System.out.println("Bem-vindo, síndico " + usuario.getNome() + "!");
                Menu.sindico(usuario);
            }
        } else {
            System.out.println("Usuário não cadastrado ou senha incorreta");
            CadastroUsuario.continuarNovo();
        }

    }

    public static void condomino(Usuario usuario) throws SQLException {
        Usuario usuarioLogado = usuario;
        Scanner input = new Scanner(System.in);
        System.out.println("------------------------------");
        System.out.println("Sistema GestCondo - Condôminos");
        System.out.println("Menu");
        System.out.println("1 - Reservar Área Comum");
        System.out.println("2 - Agendar Mudança");
        System.out.println("3 - Cadastrar Usuário");
        System.out.println("0 - Sair");
        System.out.print("Digite a opção desejada: ");
        int opcao = input.nextInt();
        input.nextLine(); // Limpa o buffer do scanner
        switch (opcao){
            case 1:
                try {
                    ReservaAreaComum.realizarReserva("condomino", usuarioLogado);
                } catch (SQLException e) {
                    System.out.println("Erro ao processar a reserva: " + e.getMessage());
                    condomino(usuarioLogado); // Volta ao menu de condômino em caso de erro
                }
                break;
            case 2:
                System.out.println("Em desenvolvimento...");
                condomino(usuarioLogado); // Volta ao menu de condômino
                break;
            case 3:
                CadastroUsuario.cadastroPorCondomino(usuarioLogado);
                condomino(usuarioLogado);
                break;
            case 0:
                System.out.println("Saindo...");
                break;
            default:
                System.out.println("Opção inválida. Tente novamente.");
                condomino(usuarioLogado); // Volta ao menu de condômino em caso de opção inválida
                break;
        }
    }

    public static void sindico(Usuario usuario) throws SQLException {
        Usuario usuarioLogado = usuario;
        int opcao;

        System.out.println("------------------------");
        System.out.println("Sistema GestCondo");
        System.out.println("Menu");
        System.out.println("1 - Cadastrar Usuário");
        System.out.println("2 - Reservar Área Comum");
        System.out.println("3 - Agendar Mudança");
        System.out.println("4 - Aprovar Cadastro de Usuário");
        System.out.println("5 - Aprovar Solicitação de Reserva de Área Comum");
        System.out.println("6 - Aprovar agendamento de mudança");
        System.out.println("7 - Relatórios");
        System.out.println("0 - Sair");
        System.out.print("Digite a opção desejada: ");
        opcao = new Scanner(System.in).nextInt();

        switch (opcao){
            case 1:
                CadastroUsuario.cadastro();
                sindico(usuarioLogado); // Retorna ao menu do síndico
                break;
            case 2:

                ReservaAreaComum.realizarReserva("sindico", usuarioLogado);
                break;
            case 3:
                System.out.println("Em desenvolvimento...");
                sindico(usuarioLogado); // Retorna ao menu do síndico
                break;
            case 4:
                CadastroUsuario.aprovarUsuariosPendentes();
                sindico(usuarioLogado); // Retorna ao menu do síndico
                break;
            case 5:
                ReservaAreaComum.aprovarReservasPendentes();
                sindico(usuarioLogado); // Retorna ao menu do síndico
                break;
            case 6:
                System.out.println("Em desenvolvimento...");
                sindico(usuarioLogado); // Retorna ao menu do síndico
                break;
            case 7:
                Menu.relatorios();
                sindico(usuarioLogado); // Retorna ao menu do síndico após sair dos relatórios
                break;
            case 0:
                System.out.println("Saindo...");
                break;
            default:
                System.out.println("Opção inválida. Tente novamente.");
                sindico(usuarioLogado); // Retorna ao menu do síndico em caso de opção inválida
                break;
        }
    }

    public static void relatorios() throws SQLException {
        int opcao;

        System.out.println("------------------------");
        System.out.println("Sistema GestCondo");
        System.out.println("Menu Relatórios");
        System.out.println("1 - Relatório Geral de Usuários");
        System.out.println("2 - Estatísticas de Moradores");
        System.out.println("0 - Sair");
        System.out.print("Digite a opção desejada: ");
        opcao = new Scanner(System.in).nextInt();

        switch (opcao){
            case 1:
                Relatorio.listaCondominos();
                break;
            case 2:
                Estatistica.moradores();
                break;
            case 0:
                System.out.println("Saindo...");
                break;
            default:
                System.out.println("Opção inválida. Tente novamente.");
        }
    }

    public static void maisInformacoes() {
        int opcao;

        System.out.println("------------------------");
        System.out.println("Mais Informações");
        System.out.println("1 - Sobre o Sistema");
        System.out.println("2 - Contato");
        System.out.println("0 - Voltar ao Menu Principal");
        System.out.print("Digite a opção desejada: ");
        opcao = new Scanner(System.in).nextInt();

        switch (opcao) {
            case 1:
                System.out.println("----------------------------------------------------------------------------------------------------------");
                System.out.println("Sistema de gestão de condomínios, desenvolvido para facilitar a administração e o dia a dia dos moradores.");
                System.out.println("Criado por André Luis, Caio Medeiros e Rodrigo Pinheiro.");
                System.out.println("----------------------------------------------------------------------------------------------------------");
                maisInformacoes(); // Retorna ao menu de mais informações
                break;
            case 2:
                System.out.println("Contato: gestcondo@gmail.com");
                maisInformacoes();
                break;
            case 0:
                inicial();
                break;
            default:
                System.out.println("Opção inválida. Tente novamente.");
                maisInformacoes();
        }
    }
}
