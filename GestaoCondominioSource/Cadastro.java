import java.util.ArrayList;
import java.util.Scanner;

public class Cadastro {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        ArrayList<Integer> idCondomino = new ArrayList<>();
        ArrayList<String> nomeCondomino = new ArrayList<>();
        ArrayList<String> cpfCondomino = new ArrayList<>();
        ArrayList<String> emailCondomino = new ArrayList<>();
        ArrayList<String> senhaCondomino = new ArrayList<>();
        ArrayList<String> telefoneCondomino = new ArrayList<>();

        Usuario usuario0 = new Usuario();
        usuario0.setIdCondomino(idCondomino.size() + 1);
        idCondomino.add(usuario0.getIdCondomino());
        System.out.println("Digite o nome do condômino: ");
        usuario0.setNomeCondomino(input.nextLine());
        nomeCondomino.add(usuario0.getNomeCondomino());
        System.out.println("Digite o CPF do condômino: ");
        usuario0.setCpfCondomino(input.nextLine());
        cpfCondomino.add(usuario0.getCpfCondomino());
        System.out.println("Digite o email do condômino: ");
        usuario0.setEmailCondomino(input.nextLine());
        emailCondomino.add(usuario0.getEmailCondomino());
        System.out.println("Digite a senha do condômino: ");
        usuario0.setSenhaCondomino(input.nextLine());
        senhaCondomino.add(usuario0.getSenhaCondomino());
        System.out.println("Digite o telefone do condômino: ");
        usuario0.setTelefoneCondomino(input.nextLine());
        telefoneCondomino.add(usuario0.getTelefoneCondomino());

        System.out.println("Cadastro realizado com sucesso!");
        System.out.println("Dados do condômino ID ()" + usuario0.getIdCondomino() + ") : ");
        System.out.println("ID      : " + usuario0.getIdCondomino());
        System.out.println("Nome    : " + usuario0.getNomeCondomino());
        System.out.println("CPF     : " + usuario0.getCpfCondomino());
        System.out.println("Email   : " + usuario0.getEmailCondomino());
        System.out.println("Senha   : " + usuario0.getSenhaCondomino());
        System.out.println("Telefone: " + usuario0.getTelefoneCondomino());
        System.out.println("--------------------------------------------------");

        

    }
}