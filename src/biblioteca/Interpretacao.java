package biblioteca;

import java.util.HashMap;
import java.util.Map;

public class Interpretacao {

    private Map<String, Boolean> valores;

    public Interpretacao() {
        valores = new HashMap<>();
    }

    public void adicionar(String proposicao, boolean valor) {
        valores.put(proposicao, valor);
    }

    public boolean obterValor(String proposicao) {

        if (!valores.containsKey(proposicao)) {
            throw new IllegalArgumentException(
                    "Proposição não encontrada: " + proposicao
            );
        }

        return valores.get(proposicao);
    }

    public Map<String, Boolean> getValores() {
        return valores;
    }
}