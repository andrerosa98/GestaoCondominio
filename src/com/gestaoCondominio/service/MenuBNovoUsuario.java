
import java.io.Console;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;
import org.mindrot.jbcrypt.BCrypt; //novo
import java.util.Scanner;

import com.gestaoCondominio.model.Usuario;
import com.gestaoCondominio.service.CadastroUsuario;

public class MenuBNovoUsuario {
    Usuario novoUsuario = new Usuario();

    Scanner dado = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("Usuário não cadastrado. Realize o seu cadastro a seguir: ");

        CadastroUsuario cadastroUsuario = new CadastroUsuario();
        cadastroUsuario.Cadastro();


        System.out.println(" Cadastro realizado com sucesso. Aguarde a aprovação para acessar os serviços");
    }
    







    
}
