package com.gestaoCondominio.service;

import java.io.Console;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

import com.gestaoCondominio.model.Usuario;
import com.gestaoCondominio.service.ConexaoBD;

import java.sql.ResultSet;

 
public class Login {
    String email;
    String senha;

    public static Usuario LoginUsuario ( String email, String senha) {
        Usuario usuario = new Usuario();
        String sql = "SELECT * FROM usuarios WHERE email = ?";
    

    try (Connection conexao = ConexaoBD.getConexao();
         PreparedStatement stmt = conexao.prepareStatement(sql)) {

             stmt.setString(1, email);
             ResultSet rs = stmt.executeQuery();

             if (rs.next()) {
                usuario.setNome(rs.getString("nome"));
                usuario.setEmail(rs.getString("email"));
                usuario.setSenha(rs.getString("senha"));
                usuario.setTipoUsuario(rs.getString("tipoUsuario"));
                usuario.setCpf(rs.getString("cpf"));
                usuario.setDataNascimento(rs.getString("dataNascimento"));

                return usuario;
                
                

             }else {
                System.out.println("Usuário não encontrado");
             }


             

        }
        catch (SQLException erro) {
            System.err.println(" Erro ao acessar o Banco de Dados" + erro.getMessage());
        }
         return null;
    }


    
}
