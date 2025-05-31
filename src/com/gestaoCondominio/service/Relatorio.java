package com.gestaoCondominio.service;

import java.io.Console;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Scanner;

import com.gestaoCondominio.model.Usuario;

public class Relatorio {

    public static ArrayList<Usuario> relatoriogeral(){
        String sql = "SELECT * FROM usuarios";
        Usuario usuario = new Usuario ();
        ArrayList<Usuario> listaUsuarios = new ArrayList<>();
        

        try (Connection conexao = ConexaoBD.getConexao();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {
                ResultSet rs = stmt.executeQuery();
                while(rs.next()) {
                    // apto usuario.setApto(rs.getString("apto"));  ----> implementar depois que houver essa tabela
                    usuario.setNome(rs.getString("nome"));
                    usuario.setEmail(rs.getString("email"));
                    usuario.setCpf(rs.getString("cpf"));
                    usuario.setDataNascimento(rs.getString("data_nascimento"));
                    //usuario.setIdade(rs.getString("idade")); ----> implementar idade


                    listaUsuarios.add(usuario);
                    
                } return listaUsuarios;


             }            


    }
    public static void main(String[] args) {
        ArrayList<Usuario> listaUsuarios = relatoriogeral();
        Usuario usuario = new Usuario();
        System.out.println("APTO -- NOME ------------- E-MAIL ---------- CPF -- DATA DE NASCIMENTO");
        for (i = 0; i< listaUsuarios.size(); i++) {
                    usuario = listaUsuarios.get(i);
                    //System.out.println(usuario.getApto()); ---> implementar depois que tiver a tabela de apto
                    System.out.print(usuario.getNome() + " -- ");
                    System.out.print(usuario.getEmail()+ " -- ");
                    System.out.print(usuario.getCpf()+ " -- ");
                    System.out.print(usuario.getDataNascimento()+ "/n");
                    //System.out.print(usuario.getIdade()+ "/n"); ------> implementar coluna idade no banco de dados

                    

            
        }


    }

    
    
}
