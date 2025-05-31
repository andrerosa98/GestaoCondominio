
import java.util.Scanner;

import com.gestaoCondominio.service.CadastroUsuario;

import com.gestaoCondominio.model.Usuario;
//import com.gestaoCondominio.service.CadastroUsuario;

public class MenuBNovoUsuario {
    Usuario novoUsuario = new Usuario();

    Scanner dado = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("Usuário não cadastrado. Realize o seu cadastro a seguir: ");

        CadastroUsuario cadastroUsuario = new CadastroUsuario();
        cadastroUsuario.cadastro();


        System.out.println(" cadastro realizado com sucesso. Aguarde a aprovação para acessar os serviços");
    }
    







    
}
