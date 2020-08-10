package analisadorsintactico;

public class Nodo {
    public TipoNodo tipoNodo;
    public  Nodo izquiendo, derecho;
    public String valor;

    public Nodo () {
        this.tipoNodo = null;
        this.izquiendo = null;
        this.derecho = null;
        this.valor = null;
    }

    public Nodo(TipoNodo tipoNodo, Nodo izquiendo, Nodo derecho, String valor) {
        this.tipoNodo = tipoNodo;
        this.izquiendo = izquiendo;
        this.derecho = derecho;
        this.valor = valor;
    }

    public static Nodo nuevoNodo(TipoNodo tipoNodo, Nodo izquiendo, Nodo derecho) {
        return new Nodo(tipoNodo, izquiendo, derecho, "");
    }
    public static Nodo nuevoNodo(TipoNodo tipoNodo, Nodo left) {
        return new Nodo(tipoNodo, left, null, "");
    }
    public static Nodo nuevaHoja(TipoNodo tipoNodo, String value) {
        return new Nodo(tipoNodo, null, null, value);
    }
}
