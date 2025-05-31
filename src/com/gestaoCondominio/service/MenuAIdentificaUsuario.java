//package com.gestaoCondominio.service;
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

        System.out.println("Digite o seu e-mail: ");
        email = dado.nextLine();
        System.out.println("Digite a senha:  ");
        senha = dado.nextLine();
        senha = BCrypt.hashpw(senha, BCrypt.gensalt());
        Usuario usuario = Login.LoginUsuario(email, senha);
        

        String tipoUsuario = usuario.getTipoUsuario();

        if (usuario != null) {
            //no arquivo login, verifica se o usuario é nulo ou nao

        if (tipoUsuario.equals("condomino")) {
            //entrar no menu de condominos (c)

        }
        else if (tipoUsuario.equals("sindico")) {
            //entrar no menu do síndico (d)
        }}
        else {
            System.out.println("Usuário não cadastrado");
            //entrar no menu do novo usuário (b)
        }
    }




    
}
