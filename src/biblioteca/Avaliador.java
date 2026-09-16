package biblioteca;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class Avaliador {

    public List<String> expressoes = new ArrayList<>();

    public List<Boolean> avaliar(
            List<UnidadeLexica> expressaoPosfixa,
            Interpretacao interpretacao) {

        Stack<Boolean> valores = new Stack<>();
        Stack<String> textos = new Stack<>();
        Stack<Boolean> compostos = new Stack<>();

        List<Boolean> resultados = new ArrayList<>();
        List<String> expressoesLocal = new ArrayList<>();

        for (UnidadeLexica unidade : expressaoPosfixa) {

            String tipo = unidade.getTipo();

            if (tipo.equals(Constantes.PROPOSICAO)) {

                boolean valor = interpretacao.obterValor(
                        unidade.getTexto()
                );

                valores.push(valor);
                textos.push(unidade.getTexto());
                compostos.push(false);
            }

            else if (tipo.equals(Constantes.NEGACAO)) {

                verificarQuantidade(valores, 1);

                boolean valor = valores.pop();
                String texto = textos.pop();
                boolean composto = compostos.pop();

                boolean resultado = !valor;

                String textoResultado;

                if (composto) {
                    textoResultado = "¬(" + texto + ")";
                } else {
                    textoResultado = "¬" + texto;
                }

                valores.push(resultado);
                textos.push(textoResultado);
                compostos.push(false);

                resultados.add(resultado);
                expressoesLocal.add(textoResultado);
            }

            else if (ehOperadorBinario(tipo)) {

                verificarQuantidade(valores, 2);

                boolean direita = valores.pop();
                boolean esquerda = valores.pop();

                String textoDireita = textos.pop();
                String textoEsquerda = textos.pop();

                boolean compostoDireita = compostos.pop();
                boolean compostoEsquerda = compostos.pop();

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

                String textoEsquerdaFormatado =
                        compostoEsquerda ? "(" + textoEsquerda + ")" : textoEsquerda;

                String textoDireitaFormatado =
                        compostoDireita ? "(" + textoDireita + ")" : textoDireita;

                String textoResultado =
                        textoEsquerdaFormatado + " " + unidade.getTexto()
                                + " " + textoDireitaFormatado;

                valores.push(resultado);
                textos.push(textoResultado);
                compostos.push(true);

                resultados.add(resultado);
                expressoesLocal.add(textoResultado);
            }
        }

        if (valores.size() != 1) {

            throw new IllegalArgumentException(
                    "Expressão inválida para avaliação."
            );
        }

        if (resultados.isEmpty()) {

            resultados.add(valores.peek());
            expressoesLocal.add(textos.peek());
        }

        expressoes = expressoesLocal;

        return resultados;
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