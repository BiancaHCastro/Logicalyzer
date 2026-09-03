package biblioteca;

import java.util.List;
import java.util.Map;

public class ResultadoAnalise {

    private List<String> proposicoes;
    private List<Interpretacao> interpretacoes;
    private List<Boolean> resultados;
    private String classificacao;

    public ResultadoAnalise(
            List<String> proposicoes,
            List<Interpretacao> interpretacoes,
            List<Boolean> resultados,
            String classificacao) {

        this.proposicoes = proposicoes;
        this.interpretacoes = interpretacoes;
        this.resultados = resultados;
        this.classificacao = classificacao;
    }

    public List<String> getProposicoes() {
        return proposicoes;
    }

    public List<Interpretacao> getInterpretacoes() {
        return interpretacoes;
    }

    public List<Boolean> getResultados() {
        return resultados;
    }

    public String getClassificacao() {
        return classificacao;
    }

    public void imprimirTabela() {

        for (String proposicao : proposicoes) {
            System.out.print(proposicao + "\t");
        }

        System.out.println("| Resultado");

        System.out.println("--------------------------------");

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

            System.out.println(
                    "| " + (resultados.get(i) ? "V" : "F")
            );
        }

        System.out.println();
        System.out.println("Classificação: " + classificacao);
    }
}