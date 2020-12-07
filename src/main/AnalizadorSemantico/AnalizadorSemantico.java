package AnalizadorSemantico;

import AnalizadorSintactico.Nodo;
import AnalizadorSintactico.TipoNodo;

public class AnalizadorSemantico {
    private TablaSimbolos tablaSimbolos;
    private Nodo nodo;

    public AnalizadorSemantico(Nodo nodo) {
        this.tablaSimbolos = TablaSimbolos.obtenerInstancia();
        this.nodo = nodo;
    }

    public void recorrerArbol(Nodo nodo) {
        if (nodo == null) {
        } else if (nodo.tipoNodo == TipoNodo.nodo_Asignar) {
            tablaSimbolos.calcularValor(nodo.izquiendo.valor, nodo.derecho);
        } else if (nodo.tipoNodo == TipoNodo.nodo_Imprimir) {
            System.out.println(tablaSimbolos.obtenerId(nodo.izquiendo.valor));
        } else {
            if (!(nodo.tipoNodo == TipoNodo.nodo_Identificador || nodo.tipoNodo == TipoNodo.nodo_Digito)) {
                recorrerArbol(nodo.izquiendo);
                recorrerArbol(nodo.derecho);
            }
        }
    }

    public TablaSimbolos getTablaSimbolos() {
        return tablaSimbolos;
    }
}
