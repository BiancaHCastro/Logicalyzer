package biblioteca;

import java.util.List;
import java.util.Stack;

public class Avaliador {

    public boolean avaliar(
            List<UnidadeLexica> expressaoPosfixa,
            Interpretacao interpretacao) {

        Stack<Boolean> valores = new Stack<>();

        for (UnidadeLexica unidade : expressaoPosfixa) {

            String tipo = unidade.getTipo();

            // Proposição
            if (tipo.equals(Constantes.PROPOSICAO)) {

                boolean valor = interpretacao.obterValor(
                        unidade.getTexto()
                );

                valores.push(valor);
            }

            // Negação
            else if (tipo.equals(Constantes.NEGACAO)) {

                verificarQuantidade(valores, 1);

                boolean valor = valores.pop();

                valores.push(!valor);
            }

            // Operadores binários
            else if (ehOperadorBinario(tipo)) {

                verificarQuantidade(valores, 2);

                boolean direita = valores.pop();
                boolean esquerda = valores.pop();

                boolean resultado;

                if (tipo.equals(Constantes.CONJUNCAO)) {

                    resultado = esquerda && direita;

                } else if (tipo.equals(Constantes.DISJUNCAO)) {

                    resultado = esquerda || direita;

                } else if (tipo.equals(Constantes.EXCLUSIVA)) {

                    resultado = esquerda != direita;

                } else if (tipo.equals(Constantes.CONDICIONAL)) {

                    resultado = !esquerda || direita;

                } else if (tipo.equals(Constantes.BICONDICIONAL)) {

                    resultado = esquerda == direita;

                } else {

                    throw new IllegalArgumentException(
                            "Operador desconhecido: "
                                    + unidade.getTexto()
                    );
                }

                valores.push(resultado);
            }
        }

        if (valores.size() != 1) {

            throw new IllegalArgumentException(
                    "Expressão inválida para avaliação."
            );
        }

        return valores.pop();
    }

    private boolean ehOperadorBinario(String tipo) {

        return tipo.equals(Constantes.CONJUNCAO)
                || tipo.equals(Constantes.DISJUNCAO)
                || tipo.equals(Constantes.EXCLUSIVA)
                || tipo.equals(Constantes.CONDICIONAL)
                || tipo.equals(Constantes.BICONDICIONAL);
    }

    private void verificarQuantidade(
            Stack<Boolean> valores,
            int quantidade) {

        if (valores.size() < quantidade) {

            throw new IllegalArgumentException(
                    "Quantidade insuficiente de valores para avaliação."
            );
        }
    }
}