package biblioteca;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ResultadoAnalise {

    private final List<String> proposicoes;
    private final List<Interpretacao> interpretacoes;
    private final List<List<Boolean>> resultadosDetalhados;
    private final List<Boolean> resultados;
    private final List<String> expressoes;
    private final String classificacao;

    public ResultadoAnalise(
            List<String> proposicoes,
            List<Interpretacao> interpretacoes,
            List<List<Boolean>> resultadosDetalhados,
            List<String> expressoes,
            String classificacao) {

        this.proposicoes = proposicoes;
        this.interpretacoes = interpretacoes;
        this.resultadosDetalhados = resultadosDetalhados;
        this.expressoes = expressoes;
        this.classificacao = classificacao;

        this.resultados = new ArrayList<>();

        for (List<Boolean> passos : resultadosDetalhados) {
            this.resultados.add(passos.get(passos.size() - 1));
        }
    }


    public void imprimirTabela() {

        for (String proposicao : proposicoes) {
            System.out.print(proposicao + "\t");
        }

        System.out.print("|");

        for (String expressao : expressoes) {
            System.out.print("\t" + expressao + "\t|");
        }

        System.out.println();

        int totalColunas = proposicoes.size() + expressoes.size() + 1;

        for (int i = 0; i < totalColunas; i++) {
            System.out.print("--------");
        }

        System.out.println();

        for (int i = 0; i < interpretacoes.size(); i++) {

            Interpretacao interpretacao = interpretacoes.get(i);

            Map<String, Boolean> valores =
                    interpretacao.getValores();

            for (String proposicao : proposicoes) {

                boolean valor = valores.get(proposicao);

                System.out.print(
                        (valor ? "V" : "F") + "\t"
                );
            }

            System.out.print("|");

            List<Boolean> passos = resultadosDetalhados.get(i);

            for (boolean passo : passos) {
                System.out.print("\t" + (passo ? "V" : "F") + "\t|");
            }

            System.out.println();
        }

        System.out.println();
        System.out.println("Classificação: " + classificacao);
    }
}