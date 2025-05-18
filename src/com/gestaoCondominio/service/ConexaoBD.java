package com.gestaoCondominio.service;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConexaoBD {
    private static final String PROPRIEDADES = "src/resources/sql/bd_config.properties";

    public static Connection getConexao() throws SQLException {
        Properties pv = new Properties();
        try (FileInputStream fis = new FileInputStream(PROPRIEDADES)) {
            pv.load(fis);
        }catch (IOException erro){
            throw new RuntimeException("Erro ao carregar arquivo de configuração: " + erro.getMessage());
        }

        String url = pv.getProperty("bd.url");
        String usuario = pv.getProperty("bd.user");
        String senha = pv.getProperty("bd.password");

    return DriverManager.getConnection(url, usuario, senha);
    }
}