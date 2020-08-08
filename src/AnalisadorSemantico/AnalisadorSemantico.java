package AnalisadorSemantico;

import AnalisadorSintactico.Nodo;
import AnalisadorSintactico.TipoNodo;

public class AnalisadorSemantico {
    private TablaSimbolos tablaSimbolos;
    private Nodo nodo;

    public AnalisadorSemantico(Nodo nodo) {
        this.tablaSimbolos = TablaSimbolos.obtenerInstancia();
        this.nodo = nodo;
        recorrerArbol(this.nodo);
        //tablaSimbolos.imprimirTabla();
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

}
