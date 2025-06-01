package com.gestaoCondominio.util;

import java.sql.*;

import com.gestaoCondominio.service.ConexaoBD;
import org.mindrot.jbcrypt.BCrypt;

public class AtualizarSenhas {
    public static void main(String[] args) throws Exception {
        String sqlSelect = "SELECT id_usuario, senha FROM usuarios";
        String sqlUpdate = "UPDATE usuarios SET senha = ? WHERE id_usuario = ?";

        try (Connection conexao = ConexaoBD.getConexao();
             PreparedStatement stmtSelect = conexao.prepareStatement(sqlSelect);
             PreparedStatement stmtUpdate = conexao.prepareStatement(sqlUpdate)) {

            ResultSet rs = stmtSelect.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id_usuario");
                String senha = rs.getString("senha");
                // Se não está hasheada (bcrypt sempre começa com $2a$, $2b$ ou $2y$)
                if (!senha.startsWith("$2")) {
                    String hash = BCrypt.hashpw(senha, BCrypt.gensalt());
                    stmtUpdate.setString(1, hash);
                    stmtUpdate.setInt(2, id);
                    stmtUpdate.executeUpdate();
                    System.out.println("Senha do usuário " + id + " atualizada.");
                }
            }
        }
    }
}
