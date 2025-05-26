import java.io.Console;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

 
public class Login {
    String email;
    String senha;
    Usuario  usuario = new Usuario();

    public usuario LoginUsuario ( String email, String senha) {
        String sql = "SELECT email, senha FROM usuarios";
    

    try (Connection conexao = ConexaoBD.getConexao();
        PreparedStatement stmt = conexao.prepareStatement(sql)) {
             ResultSet = stmt.executeQuery();
             if (rs.next()) {
                usuario.setNome(rs.getString("nome"));
                usuario.setEmail(rs.getString("email"));
                usuario.setSenha(rs.getString("senha"));
                usuario.setTipoUsuario(rs.getString("tipoUsuario"));
                usuario.setcpf(rs.getString("cpf"));
                usuario.setDataNascimento(rs.getString("dataNascimento"));

                return usuario;
                
                

             }else {
                System.out.println("Usuário não encontrado");
             }


             return null;
             

        }
        catch (SQLException erro) {
            System.err.println(" Erro ao acessar o Banco de Dados" + erro.getMessage());
        }
    }


    
}
