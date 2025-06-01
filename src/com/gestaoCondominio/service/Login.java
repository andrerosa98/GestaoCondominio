package com.gestaoCondominio.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;

import com.gestaoCondominio.model.Usuario;
import org.mindrot.jbcrypt.BCrypt;

public class Login {

    public static Usuario loginUsuario(String email, String senha) {
        String sql = "SELECT * FROM usuarios WHERE email = ?";
        try (Connection conexao = ConexaoBD.getConexao();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                String senhaHash = rs.getString("senha");
                String statusAprovacao = rs.getString("status_aprovacao");
                if (!"aprovado".equalsIgnoreCase(statusAprovacao)) {
                    System.out.println("Usuário não aprovado. Aguarde aprovação do síndico.");
                    return null;
                }
                if (!BCrypt.checkpw(senha, senhaHash)) {
                    System.out.println("Senha incorreta");
                    return null;
                }
                Usuario usuario = new Usuario();
                usuario.setId(rs.getInt("id_usuario"));
                usuario.setNome(rs.getString("nome"));
                usuario.setEmail(rs.getString("email"));
                usuario.setTipoUsuario(rs.getString("tipo_usuario"));
                usuario.setCpf(rs.getString("cpf"));
                usuario.setDataNascimento(rs.getString("data_nascimento"));
                usuario.setIdade(rs.getInt("idade"));
                return usuario;
            }
        } catch (SQLException erro) {
            System.err.println("Erro ao acessar o Banco de Dados: " + erro.getMessage());
        }
        return null;
    }
}