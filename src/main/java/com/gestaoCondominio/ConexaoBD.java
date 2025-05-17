package main.java.com.gestaoCondominio;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexaoBD {
    private static final String URL = "jdbc:mysql://localhost:3306/cadastro_usuarios";
    private static final String USUARIO = "seu_usuario";
    private static final String SENHA = "sua_senha";

    public static Connection getConexao() throws SQLException {
        return DriverManager.getConnection(URL, USUARIO, SENHA);
    }
}

