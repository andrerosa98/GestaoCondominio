//import java.util.Scanner;

import java.util.ArrayList;

import com.gestaoCondominio.model.Usuario;

public class estatistica
{
    public static void main (String[] args)
    {
        //declaração de variáveis
        ArrayList<Usuario> listaUsuarios = relatoriogeral();
        float faixa1 = 0 , faixa2 = 0 , faixa3 = 0 , faixa4 = 0 ;
        float percCrianca, percAdolescente, percAdulto, percIdoso;
        float totalMoradores = listaUsuarios.size();
        Usuario usuario = new Usuario();
        //usuario.getIdade // precisa criar em usuario esse get
        int i;
        for (i = 0; i< listaUsuarios.size(); i++) {
                    usuario = listaUsuarios.get(i);
                    if (usuario.getIdade() <= 12) {faixa1 ++;} 
                    else if (usuario.getIdade() <= 19) {faixa2 ++;}
                    else if (usuario.getIdade() <= 64) {faixa3 ++;}
                    else if (usuario.getIdade() >= 65) {faixa4 ++;}
                    //jogar dentro das faixas
        }
        usuario = listaUsuarios.get(i); 

        
        //Scanner dado = new Scanner(System.in);
        
        //entrada de dados
        
        //processamento

        percCrianca = (faixa1 / totalMoradores) * 100 ;
        percAdolescente = (faixa2 / totalMoradores) * 100;
        percAdulto = (faixa3 / totalMoradores) * 100;
        percIdoso = (faixa4 / totalMoradores) * 100;

        System.out.println("Estatística do perfil etário dos moradores para tomada de decisões:");

        System.out.printf("%.2f dos moradores são são crianças até 12 anos \n" , percCrianca);
        
        System.out.printf("%.2f dos moradores são adolescentes entre 13 e 19 anos \n" , percAdolescente );
        System.out.printf("%.2f dos moradores são adultos entre 20 e 64 anos \n" , percAdulto);
        System.out.printf("%.2f dos moradores são idosos acima de 65 anos \n" , percIdoso);




        
        //saída de dados
        
    }
}
