package main.com.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Verificar {

    public static boolean verificarEmail(String email) {
        String regex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$"; // Uso de regex para validar o email.
        //                                                                  O início deve ser um caractere alfanumérico,
        //                                                                  seguido de @, seguido de um domínio e uma
        //                                                                  extensão de pelo menos 3 caracteres.
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    public static boolean verificarCPF(String cpf) {
        cpf = cpf.replaceAll("\\D", ""); // Remove todos os caracteres não numéricos. O \\D é uma classe
        //                                                   de caracteres que representa qualquer caractere que não seja um dígito.

        String regex = "^[0-9]{11}$"; // O CPF deve conter exatamente 11 dígitos numéricos.

        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(cpf);
        return matcher.matches();
    }
}
