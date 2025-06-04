package com.gestaoCondominio.model;

import com.gestaoCondominio.service.ConexaoBD;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;

public class Usuario {

    private int id;
    private String nome;
    private String email;
    private String senha;
    private String tipoUsuario;
    private String cpf;
    private String dataNascimento;
    private int idade;
    private String apto; // Novo campo para o número do apartamento

    public Usuario(String nome, String email, String senha, String tipoUsuario, String cpf, String dataNascimento) {
        this.nome = nome;
        this.email = email;
        this.senha = senha;
        this.tipoUsuario = tipoUsuario;
        this.cpf = cpf;
        this.dataNascimento = dataNascimento;
    }

    public Usuario() {
    }

    public int calcularIdade() {
        if (dataNascimento == null || dataNascimento.isEmpty()) {
            return 0;
        }
        LocalDate dataNasc = LocalDate.parse(dataNascimento, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        LocalDate hoje = LocalDate.now();
        return Period.between(dataNasc, hoje).getYears();
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getTipoUsuario() {
        return tipoUsuario;
    }

    public void setTipoUsuario(String tipoUsuario) {
        this.tipoUsuario = tipoUsuario;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(String dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public int getIdade() {
        return idade;
    }

    public void setIdade(int idade) {
        this.idade = idade;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getApto() {
        return apto;
    }

    public void setApto(String apto) {
        this.apto = apto;
    }

    public static int getIdUsuario(String email) {
        String sql = "SELECT id_usuario FROM usuarios WHERE email = ?";
        try (Connection conexao = ConexaoBD.getConexao();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("id_usuario");
            }
        } catch (SQLException erro) {
            System.err.println("Erro ao buscar ID do usuário: " + erro.getMessage());
        }
        return 0;
    }

    public String getDataNascimentoFormatada() {
        if (dataNascimento == null || dataNascimento.isEmpty()) {
            return "-";
        }
        try {
            LocalDate data = LocalDate.parse(dataNascimento, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            return data.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        } catch (Exception e) {
            return dataNascimento; // Retorna como está se não conseguir converter
        }
    }

    public static void excluirUsuario(int idUsuario) throws SQLException {
        String sql = "DELETE FROM usuarios WHERE id_usuario = ?";
        try (Connection conexao = ConexaoBD.getConexao();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setInt(1, idUsuario);
            stmt.executeUpdate();
        }
    }
}