package java.com.gestaoCondominio;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class TesteConexaoBD {
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/cadastro_usuarios";
        String usuario = "seu_usuario";
        String senha = "sua_senha";

        try (Connection conexao = DriverManager.getConnection(url, usuario, senha)) {
            System.out.println("Conexão com o banco de dados estabelecida com sucesso!");
        } catch (SQLException e) {
            System.err.println("Erro ao conectar ao banco de dados: " + e.getMessage());
        }
    }
}
