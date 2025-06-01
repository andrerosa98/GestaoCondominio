package com.gestaoCondominio.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.gestaoCondominio.model.Usuario;
import org.mindrot.jbcrypt.BCrypt;

import java.sql.ResultSet;

 
public class Login {
    String email;
    String senha;

    public static Usuario loginUsuario(String email, String senha) {
        Usuario usuario = new Usuario();
        String sql = "SELECT * FROM usuarios WHERE email = ?";
    

    try (Connection conexao = ConexaoBD.getConexao();
         PreparedStatement stmt = conexao.prepareStatement(sql)) {

             stmt.setString(1, email);
             ResultSet rs = stmt.executeQuery();

             if (rs.next()) {
                 String senhaHash = rs.getString("senha");
                    if(BCrypt.checkpw((senha), senhaHash)) {
                    } else {
                        System.out.println("Senha incorreta");
                        return null;
                    }
                 usuario.setId(rs.getInt("id_usuario"));
                 usuario.setNome(rs.getString("nome"));
                 usuario.setEmail(rs.getString("email"));
                 usuario.setTipoUsuario(rs.getString("tipo_usuario"));
                 usuario.setCpf(rs.getString("cpf"));
                 usuario.setDataNascimento(rs.getString("data_nascimento"));
                 return usuario;
             }
        }
        catch (SQLException erro) {
            System.err.println(" Erro ao acessar o Banco de Dados" + erro.getMessage());
        }
         return null;
    }


    
}
