import biblioteca.Analisador;
import biblioteca.ResultadoAnalise;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner entrada = new Scanner(System.in);

        Analisador analisador = new Analisador();

        System.out.println("======================================");
        System.out.println("     PROVADOR DE LOGICA PROPOSICIONAL");
        System.out.println("======================================");

        System.out.println();
        System.out.println("Simbolos aceitos:");
        System.out.println("¬  Negacao");
        System.out.println("∧  Conjuncao");
        System.out.println("∨  Disjuncao");
        System.out.println("⊕  Disjuncao exclusiva");
        System.out.println("→  Condicional");
        System.out.println("↔  Bicondicional");
        System.out.println("( ) Parenteses");

        System.out.println();
        System.out.println("Exemplos:");
        System.out.println("p");
        System.out.println("¬p");
        System.out.println("p ∧ q");
        System.out.println("(p → q) ∧ (q → p)");
        System.out.println("p ∨ ¬p");

        System.out.println();
        System.out.println("--------------------------------------");

        while (true) {

            System.out.print("\nDigite uma formula ou 'sair': ");

            String expressao = entrada.nextLine();

            if (expressao.equalsIgnoreCase("sair")) {
                break;
            }

            try {

                ResultadoAnalise resultado =
                        analisador.analisar(expressao);

                System.out.println();
                System.out.println("Formula valida.");
                System.out.println();

                resultado.imprimirTabela();

            } catch (IllegalArgumentException erro) {

                System.out.println();
                System.out.println("ERRO:");
                System.out.println(erro.getMessage());
            }
        }

        entrada.close();

        System.out.println();
        System.out.println("Programa encerrado.");
    }
}