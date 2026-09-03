package biblioteca;

import java.util.ArrayList;
import java.util.List;

public class AnalisadorLexico {

    public List<UnidadeLexica> analisar(String expressao) {

        List<UnidadeLexica> unidades = new ArrayList<>();

        if (expressao == null || expressao.trim().isEmpty()) {
            throw new IllegalArgumentException("A expressão não pode ser vazia.");
        }

        int posicao = 0;

        while (posicao < expressao.length()) {

            char caractere = expressao.charAt(posicao);

            // Ignora espaços
            if (Character.isWhitespace(caractere)) {
                posicao++;
                continue;
            }

            // Proposição
            if (Character.isLetter(caractere)) {

                String texto = String.valueOf(caractere);

                unidades.add(
                        new UnidadeLexica(
                                Constantes.PROPOSICAO,
                                texto
                        )
                );

                posicao++;
                continue;
            }

            // Negação
            if (caractere == '¬' || caractere == '∼' ||
                    caractere == '~' || caractere == '!') {

                unidades.add(
                        new UnidadeLexica(
                                Constantes.NEGACAO,
                                Constantes.SIMBOLO_NEGACAO
                        )
                );

                posicao++;
                continue;
            }

            // Conjunção
            if (caractere == '∧' || caractere == '&') {

                unidades.add(
                        new UnidadeLexica(
                                Constantes.CONJUNCAO,
                                Constantes.SIMBOLO_CONJUNCAO
                        )
                );

                posicao++;
                continue;
            }

            // Disjunção
            if (caractere == '∨' || caractere == '|') {

                unidades.add(
                        new UnidadeLexica(
                                Constantes.DISJUNCAO,
                                Constantes.SIMBOLO_DISJUNCAO
                        )
                );

                posicao++;
                continue;
            }

            // Disjunção exclusiva
            if (caractere == '⊕' || caractere == '^') {

                unidades.add(
                        new UnidadeLexica(
                                Constantes.EXCLUSIVA,
                                Constantes.SIMBOLO_EXCLUSIVA
                        )
                );

                posicao++;
                continue;
            }

            // Condicional
            if (caractere == '→') {

                unidades.add(
                        new UnidadeLexica(
                                Constantes.CONDICIONAL,
                                Constantes.SIMBOLO_CONDICIONAL
                        )
                );

                posicao++;
                continue;
            }

            // Condicional utilizando ->
            if (caractere == '-') {

                if (posicao + 1 < expressao.length()
                        && expressao.charAt(posicao + 1) == '>') {

                    unidades.add(
                            new UnidadeLexica(
                                    Constantes.CONDICIONAL,
                                    Constantes.SIMBOLO_CONDICIONAL
                            )
                    );

                    posicao += 2;
                    continue;
                }

                throw new IllegalArgumentException(
                        "Símbolo inválido na posição " + posicao + ": -"
                );
            }

            // Bicondicional
            if (caractere == '↔') {

                unidades.add(
                        new UnidadeLexica(
                                Constantes.BICONDICIONAL,
                                Constantes.SIMBOLO_BICONDICIONAL
                        )
                );

                posicao++;
                continue;
            }

            // Bicondicional utilizando <->
            if (caractere == '<') {

                if (posicao + 2 < expressao.length()
                        && expressao.charAt(posicao + 1) == '-'
                        && expressao.charAt(posicao + 2) == '>') {

                    unidades.add(
                            new UnidadeLexica(
                                    Constantes.BICONDICIONAL,
                                    Constantes.SIMBOLO_BICONDICIONAL
                            )
                    );

                    posicao += 3;
                    continue;
                }

                throw new IllegalArgumentException(
                        "Símbolo inválido na posição " + posicao + ": <"
                );
            }

            // Parêntese esquerdo
            if (caractere == '(') {

                unidades.add(
                        new UnidadeLexica(
                                Constantes.ABRE_PARENTESE,
                                "("
                        )
                );

                posicao++;
                continue;
            }

            // Parêntese direito
            if (caractere == ')') {

                unidades.add(
                        new UnidadeLexica(
                                Constantes.FECHA_PARENTESE,
                                ")"
                        )
                );

                posicao++;
                continue;
            }

            throw new IllegalArgumentException(
                    "Símbolo inválido na posição " + posicao + ": "
                            + caractere
            );
        }

        return unidades;
    }
}