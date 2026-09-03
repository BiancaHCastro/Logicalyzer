package biblioteca;

import java.util.ArrayList;
import java.util.List;

public class Analisador {

    private AnalisadorLexico analisadorLexico;
    private AnalisadorSintatico analisadorSintatico;
    private GeradorInterpretacoes geradorInterpretacoes;
    private Avaliador avaliador;

    public Analisador() {

        analisadorLexico = new AnalisadorLexico();
        analisadorSintatico = new AnalisadorSintatico();
        geradorInterpretacoes = new GeradorInterpretacoes();
        avaliador = new Avaliador();
    }

    public ResultadoAnalise analisar(String expressao) {

        // ETAPA I - Análise léxica
        List<UnidadeLexica> unidades =
                analisadorLexico.analisar(expressao);

        // ETAPA II - Análise sintática
        List<UnidadeLexica> expressaoPosfixa =
                analisadorSintatico.analisar(unidades);

        // Obtém as proposições
        List<String> proposicoes =
                obterProposicoes(unidades);

        if (proposicoes.isEmpty()) {

            throw new IllegalArgumentException(
                    "A expressão precisa possuir pelo menos uma proposição."
            );
        }

        // Gera todas as interpretações
        List<Interpretacao> interpretacoes =
                geradorInterpretacoes.gerar(proposicoes);

        // Avalia cada interpretação
        List<Boolean> resultados = new ArrayList<>();

        for (Interpretacao interpretacao : interpretacoes) {

            boolean resultado =
                    avaliador.avaliar(
                            expressaoPosfixa,
                            interpretacao
                    );

            resultados.add(resultado);
        }

        String classificacao =
                classificar(resultados);

        return new ResultadoAnalise(
                proposicoes,
                interpretacoes,
                resultados,
                classificacao
        );
    }

    private List<String> obterProposicoes(
            List<UnidadeLexica> unidades) {

        List<String> proposicoes = new ArrayList<>();

        for (UnidadeLexica unidade : unidades) {

            if (unidade.getTipo().equals(
                    Constantes.PROPOSICAO)) {

                String proposicao =
                        unidade.getTexto();

                if (!proposicoes.contains(proposicao)) {

                    proposicoes.add(proposicao);
                }
            }
        }

        return proposicoes;
    }

    private String classificar(
            List<Boolean> resultados) {

        boolean todasVerdadeiras = true;
        boolean todasFalsas = true;

        for (boolean resultado : resultados) {

            if (resultado) {
                todasFalsas = false;
            } else {
                todasVerdadeiras = false;
            }
        }

        if (todasVerdadeiras) {
            return "TAUTOLOGIA";
        }

        if (todasFalsas) {
            return "CONTRADIÇÃO";
        }

        return "CONTINGÊNCIA";
    }
}