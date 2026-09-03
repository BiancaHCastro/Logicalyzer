package biblioteca;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class AnalisadorSintatico {

    public List<UnidadeLexica> analisar(List<UnidadeLexica> unidades) {

        if (unidades == null || unidades.isEmpty()) {
            throw new IllegalArgumentException(
                    "Não existem unidades para analisar."
            );
        }

        validarEstrutura(unidades);

        return converterParaPosfixa(unidades);
    }

    private void validarEstrutura(List<UnidadeLexica> unidades) {

        boolean esperaOperando = true;
        int quantidadeParenteses = 0;

        for (int i = 0; i < unidades.size(); i++) {

            UnidadeLexica unidade = unidades.get(i);

            String tipo = unidade.getTipo();

            if (tipo.equals(Constantes.PROPOSICAO)) {

                if (!esperaOperando) {
                    throw new IllegalArgumentException(
                            "Duas proposições não podem aparecer consecutivamente."
                    );
                }

                esperaOperando = false;
            }

            else if (tipo.equals(Constantes.NEGACAO)) {

                // Negação só pode aparecer quando esperamos um operando
                if (!esperaOperando) {
                    throw new IllegalArgumentException(
                            "Negação em posição inválida."
                    );
                }
            }

            else if (tipo.equals(Constantes.ABRE_PARENTESE)) {

                if (!esperaOperando) {
                    throw new IllegalArgumentException(
                            "Parêntese aberto em posição inválida."
                    );
                }

                quantidadeParenteses++;
            }

            else if (tipo.equals(Constantes.FECHA_PARENTESE)) {

                if (esperaOperando) {
                    throw new IllegalArgumentException(
                            "Parêntese fechado em posição inválida."
                    );
                }

                quantidadeParenteses--;

                if (quantidadeParenteses < 0) {
                    throw new IllegalArgumentException(
                            "Parênteses não estão balanceados."
                    );
                }
            }

            else if (ehOperadorBinario(tipo)) {

                if (esperaOperando) {
                    throw new IllegalArgumentException(
                            "Operador em posição inválida: "
                                    + unidade.getTexto()
                    );
                }

                esperaOperando = true;
            }
        }

        if (quantidadeParenteses != 0) {
            throw new IllegalArgumentException(
                    "Parênteses não estão balanceados."
            );
        }

        if (esperaOperando) {
            throw new IllegalArgumentException(
                    "A expressão não pode terminar com um operador."
            );
        }
    }

    private boolean ehOperadorBinario(String tipo) {

        return tipo.equals(Constantes.CONJUNCAO)
                || tipo.equals(Constantes.DISJUNCAO)
                || tipo.equals(Constantes.EXCLUSIVA)
                || tipo.equals(Constantes.CONDICIONAL)
                || tipo.equals(Constantes.BICONDICIONAL);
    }

    private List<UnidadeLexica> converterParaPosfixa(
            List<UnidadeLexica> unidades) {

        List<UnidadeLexica> saida = new ArrayList<>();

        Stack<UnidadeLexica> operadores = new Stack<>();

        for (UnidadeLexica unidade : unidades) {

            String tipo = unidade.getTipo();

            if (tipo.equals(Constantes.PROPOSICAO)) {

                saida.add(unidade);
            }

            else if (tipo.equals(Constantes.NEGACAO)) {

                operadores.push(unidade);
            }

            else if (ehOperadorBinario(tipo)) {

                while (!operadores.empty()
                        && !operadores.peek().getTipo()
                        .equals(Constantes.ABRE_PARENTESE)
                        && deveRetirarOperador(
                                operadores.peek(),
                                unidade)) {

                    saida.add(operadores.pop());
                }

                operadores.push(unidade);
            }

            else if (tipo.equals(Constantes.ABRE_PARENTESE)) {

                operadores.push(unidade);
            }

            else if (tipo.equals(Constantes.FECHA_PARENTESE)) {

                while (!operadores.empty()
                        && !operadores.peek().getTipo()
                        .equals(Constantes.ABRE_PARENTESE)) {

                    saida.add(operadores.pop());
                }

                if (operadores.empty()) {
                    throw new IllegalArgumentException(
                            "Parênteses não estão balanceados."
                    );
                }

                operadores.pop();

                // Se houver uma negação antes do parêntese,
                // ela deve ser aplicada depois da expressão.
                if (!operadores.empty()
                        && operadores.peek().getTipo()
                        .equals(Constantes.NEGACAO)) {

                    saida.add(operadores.pop());
                }
            }
        }

        while (!operadores.empty()) {

            UnidadeLexica unidade = operadores.pop();

            if (unidade.getTipo().equals(Constantes.ABRE_PARENTESE)
                    || unidade.getTipo().equals(Constantes.FECHA_PARENTESE)) {

                throw new IllegalArgumentException(
                        "Parênteses não estão balanceados."
                );
            }

            saida.add(unidade);
        }

        return saida;
    }

    private boolean deveRetirarOperador(
            UnidadeLexica operadorTopo,
            UnidadeLexica operadorAtual) {

        int prioridadeTopo = prioridade(operadorTopo);
        int prioridadeAtual = prioridade(operadorAtual);

        if (prioridadeTopo > prioridadeAtual) {
            return true;
        }

        if (prioridadeTopo < prioridadeAtual) {
            return false;
        }

        // Condicional e bicondicional são associativos à direita.
        if (operadorAtual.getTipo().equals(Constantes.CONDICIONAL)
                || operadorAtual.getTipo().equals(Constantes.BICONDICIONAL)) {

            return false;
        }

        return true;
    }

    private int prioridade(UnidadeLexica unidade) {

        String tipo = unidade.getTipo();

        if (tipo.equals(Constantes.NEGACAO)) {
            return 5;
        }

        if (tipo.equals(Constantes.CONJUNCAO)) {
            return 4;
        }

        if (tipo.equals(Constantes.DISJUNCAO)) {
            return 3;
        }

        if (tipo.equals(Constantes.EXCLUSIVA)) {
            return 2;
        }

        if (tipo.equals(Constantes.CONDICIONAL)) {
            return 1;
        }

        if (tipo.equals(Constantes.BICONDICIONAL)) {
            return 0;
        }

        return -1;
    }
}