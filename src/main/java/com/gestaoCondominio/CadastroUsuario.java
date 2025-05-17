package main.java.com.gestaoCondominio;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class CadastroUsuario {
    public static void cadastrarUsuario(String nome, String email, String senha, String cpf, String dataNascimento) {
        String sql = "INSERT INTO usuarios (nome, email, senha, cpf, data_nascimento) VALUES (?, ?, ?, ?, ?)";

        try (Connection conexao = ConexaoBD.getConexao();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setString(1, nome);
            stmt.setString(2, email);
            stmt.setString(3, senha);
            stmt.setString(4, cpf);
            stmt.setString(5, dataNascimento);

            stmt.executeUpdate();
            System.out.println("Usuário cadastrado com sucesso!");
        } catch (SQLException erro) {
            System.err.println("Erro ao cadastrar usuário: " + erro.getMessage());
        }
    }
}
