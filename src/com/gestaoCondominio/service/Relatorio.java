package com.gestaoCondominio.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.gestaoCondominio.model.Usuario;

public class Relatorio {

    public static ArrayList<Usuario> relatorioGeral() throws SQLException {
        String sql = "SELECT * FROM usuarios";
        ArrayList<Usuario> listaUsuarios = new ArrayList<>();
        

        try (Connection conexao = ConexaoBD.getConexao();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {
                ResultSet rs = stmt.executeQuery();
                while(rs.next()) {
                    Usuario usuario = new Usuario();
                    // apto usuario.setApto(rs.getString("apto"));  ----> implementar depois que houver essa tabela
                    usuario.setNome(rs.getString("nome"));
                    usuario.setEmail(rs.getString("email"));
                    usuario.setCpf(rs.getString("cpf"));
                    usuario.setDataNascimento(rs.getString("data_nascimento"));
                    usuario.setIdade(rs.getInt("idade"));


                    listaUsuarios.add(usuario);
                    
                } return listaUsuarios;
             }
    }

    public static void listaCondominos() throws SQLException {
        ArrayList<Usuario> listaUsuarios = relatorioGeral();
        System.out.println("+-----------------+------------------------------------------+------------------------------------------+----------------+-------------------+--------+");
        System.out.println("| APTO            | NOME                                     | E-MAIL                                   | CPF            | DATA NASCIMENTO   | IDADE  |");
        System.out.println("+-----------------+------------------------------------------+------------------------------------------+----------------+-------------------+--------+");
        for (int i = 0; i < listaUsuarios.size(); i++) {
            Usuario usuario = listaUsuarios.get(i);
            System.out.printf("| %-15s | %-40s | %-40s | %-14s | %-17s | %-6d |\n",
                    "Apto", // implementar depois que tiver a tabela de apto
                    usuario.getNome(),
                    usuario.getEmail(),
                    usuario.getCpf(),
                    usuario.getDataNascimento(),
                    usuario.getIdade());

        }
        System.out.println("+-----------------+------------------------------------------+------------------------------------------+----------------+-------------------+--------+");
    }
}
