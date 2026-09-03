package biblioteca;

import java.util.ArrayList;
import java.util.List;

public class GeradorInterpretacoes {

    public List<Interpretacao> gerar(List<String> proposicoes) {

        if (proposicoes.size() > 5) {
            throw new IllegalArgumentException(
                    "A quantidade máxima de proposições é 5."
            );
        }

        List<Interpretacao> interpretacoes = new ArrayList<>();

        gerarRecursivamente(
                proposicoes,
                0,
                new Interpretacao(),
                interpretacoes
        );

        return interpretacoes;
    }

    private void gerarRecursivamente(
            List<String> proposicoes,
            int posicao,
            Interpretacao atual,
            List<Interpretacao> resultado) {

        if (posicao == proposicoes.size()) {

            Interpretacao copia = new Interpretacao();

            for (String proposicao : proposicoes) {
                copia.adicionar(
                        proposicao,
                        atual.obterValor(proposicao)
                );
            }

            resultado.add(copia);

            return;
        }

        String proposicao = proposicoes.get(posicao);

        // Primeiro testa verdadeiro
        atual.adicionar(proposicao, true);

        gerarRecursivamente(
                proposicoes,
                posicao + 1,
                atual,
                resultado
        );

        // Depois testa falso
        atual.adicionar(proposicao, false);

        gerarRecursivamente(
                proposicoes,
                posicao + 1,
                atual,
                resultado
        );
    }
}