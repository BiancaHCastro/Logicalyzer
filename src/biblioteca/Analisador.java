package biblioteca;

import java.util.ArrayList;
import java.util.List;

public class Analisador {

    private final AnalisadorLexico analisadorLexico;
    private final AnalisadorSintatico analisadorSintatico;
    private final GeradorInterpretacoes geradorInterpretacoes;
    private final Avaliador avaliador;

    public Analisador() {

        analisadorLexico = new AnalisadorLexico();
        analisadorSintatico = new AnalisadorSintatico();
        geradorInterpretacoes = new GeradorInterpretacoes();
        avaliador = new Avaliador();
    }

    public ResultadoAnalise analisar(String expressao) {

        List<UnidadeLexica> unidades =
                analisadorLexico.analisar(expressao);

        List<UnidadeLexica> expressaoPosfixa =
                analisadorSintatico.analisar(unidades);

        List<String> proposicoes =
                obterProposicoes(unidades);

        if (proposicoes.isEmpty()) {

            throw new IllegalArgumentException(
                    "A expressão precisa possuir pelo menos uma proposição."
            );
        }

        List<Interpretacao> interpretacoes =
                geradorInterpretacoes.gerar(proposicoes);

        List<List<Boolean>> resultadosDetalhados = new ArrayList<>();
        List<Boolean> resultados = new ArrayList<>();

        for (Interpretacao interpretacao : interpretacoes) {

            List<Boolean> passos =
                    avaliador.avaliar(
                            expressaoPosfixa,
                            interpretacao
                    );

            resultadosDetalhados.add(passos);
            resultados.add(passos.get(passos.size() - 1));
        }

        String classificacao =
                classificar(resultados);

        return new ResultadoAnalise(
                proposicoes,
                interpretacoes,
                resultadosDetalhados,
                avaliador.expressoes,
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