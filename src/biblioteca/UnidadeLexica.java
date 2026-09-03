package biblioteca;

public class UnidadeLexica {

    private String tipo;
    private String texto;

    public UnidadeLexica(String tipo, String texto) {
        this.tipo = tipo;
        this.texto = texto;
    }

    public String getTipo() {
        return tipo;
    }

    public String getTexto() {
        return texto;
    }

    @Override
    public String toString() {
        return texto + " [" + tipo + "]";
    }
}