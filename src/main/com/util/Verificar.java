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
}
