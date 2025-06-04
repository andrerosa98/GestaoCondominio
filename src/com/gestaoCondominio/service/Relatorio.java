package com.gestaoCondominio.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

import com.gestaoCondominio.model.Usuario;

public class Relatorio {


    public static ArrayList<Usuario> relatorioGeral() throws SQLException {
        String sql = "SELECT u.*, a.numero AS apartamento " +
                "FROM usuarios u " +
                "JOIN usuarios_apartamentos ua ON u.id_usuario = ua.id_usuario " +
                "JOIN apartamentos a ON ua.id_apartamento = a.id_apartamento";
        ArrayList<Usuario> listaUsuarios = new ArrayList<>();

        try (Connection conexao = ConexaoBD.getConexao();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();
            while(rs.next()) {
                Usuario usuario = new Usuario();
                usuario.setNome(rs.getString("nome"));
                usuario.setEmail(rs.getString("email"));
                usuario.setCpf(rs.getString("cpf"));
                usuario.setDataNascimento(rs.getString("data_nascimento"));
                usuario.setIdade(rs.getInt("idade"));
                usuario.setApto(rs.getString("apartamento"));
                listaUsuarios.add(usuario);
            }
            return listaUsuarios;
        }
    }

    public static void listaCondominos() throws SQLException {
        ArrayList<Usuario> listaUsuarios = relatorioGeral();
        System.out.println("+-----------------+------------------------------------------+------------------------------------------+----------------+-------------------+--------+");
        System.out.println("| APTO            | NOME                                     | E-MAIL                                   | CPF            | DATA NASCIMENTO   | IDADE  |");
        System.out.println("+-----------------+------------------------------------------+------------------------------------------+----------------+-------------------+--------+");
        for (Usuario usuario : listaUsuarios) {
            System.out.printf("| %-15s | %-40s | %-40s | %-14s | %-17s | %-6d |\n",
                    usuario.getApto() != null ? usuario.getApto() : "-",
                    usuario.getNome(),
                    usuario.getEmail(),
                    usuario.getCpf(),
                    usuario.getDataNascimentoFormatada(),
                    usuario.getIdade());
        }
        System.out.println("+-----------------+------------------------------------------+------------------------------------------+----------------+-------------------+--------+");
    }
}