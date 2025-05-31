package com.gestaoCondominio.service;//package com.gestaoCondominio.service;
import java.util.Scanner;

import com.gestaoCondominio.service.Login;
import org.mindrot.jbcrypt.BCrypt;

import com.gestaoCondominio.model.Usuario;

public class MenuAIdentificaUsuario {
    //primeira tela que separa usuário condômino de usuário síndico
 

    public static void main(String[] args) {
        String email;
        String senha;
        Scanner dado = new Scanner(System.in);

        System.out.println("Bem-vindo ao sistema de gestão do condomínio");

        System.out.print("Digite o seu e-mail: ");
        email = dado.nextLine();
        System.out.print("Digite a senha:  ");
        senha = dado.nextLine();
        Usuario usuario = Login.loginUsuario(email, senha);

        if (usuario != null) {
            if ("condomino".equals(usuario.getTipoUsuario())) {
                // Menu condomino
                System.out.println("Bem-vindo, " + usuario.getNome() + "!");
                // Lógica para menu do condomino
            } else if ("sindico".equals(usuario.getTipoUsuario())) {
                // Menu sindico
                System.out.println("Bem-vindo, síndico " + usuario.getNome() + "!");
                // Lógica para menu do síndico
            }
        } else {
            System.out.println("Usuário não cadastrado ou senha incorreta");
            CadastroUsuario.continuarNovo();
        }

    }




    
}
