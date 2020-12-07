package AnalizadorSemantico;

import AnalizadorSintactico.Nodo;
import AnalizadorSintactico.TipoNodo;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class TablaSimbolos {

    private static Map<String, Integer> tabla;
    private static TablaSimbolos tablaSimbolos;

    private TablaSimbolos() {
        this.tabla = new HashMap<>();
    }

    public static TablaSimbolos obtenerInstancia() {
        if (tablaSimbolos == null) {
            tablaSimbolos = new TablaSimbolos();
        }
        return tablaSimbolos;
    }

    public void error(String mensaje, Nodo nodo) {
        System.out.println("Error semantico: " + nodo.tipoNodo + " , ha ocurrido un error de tipo: " + mensaje);
        System.exit(-1);
    }

    public Map<String, Integer> getTabla() {
        return this.tabla;
    }

    private void ingresarId(String identificador, int valor) {
        this.tabla.put(identificador, valor);
    }

    public boolean existeIdentificador(String identificador) {
        return this.tabla.containsKey(identificador);
    }

    public void calcularValor(String identificador, Nodo nodo) {
        ingresarId(identificador, recorrerNodo(nodo));
    }

    private int recorrerNodo(Nodo nodo) {
        int valor = 0;
        if (nodo.tipoNodo == TipoNodo.nodo_Digito) {
            return Integer.parseInt(nodo.valor);
        } else if (nodo.tipoNodo == TipoNodo.nodo_Identificador) {
            try {
                return tabla.get(nodo.valor); //todo Verificar si existe el id.
            } catch (Exception e) {
                error("Identificador no definido", nodo);
            }
        }
        if (nodo.tipoNodo == TipoNodo.nodo_ParentesisDerecho) {
            recorrerNodo(nodo.izquiendo);
        }
        if (nodo.tipoNodo == TipoNodo.nodo_Div) {
            try {
                return recorrerNodo(nodo.izquiendo) / recorrerNodo(nodo.derecho);//todo Verificar si es division por cero.
            } catch (Exception e) {
                error("Divición por cero", nodo);
            }
        }
        if (nodo.tipoNodo == TipoNodo.nodo_Mult) {
            return recorrerNodo(nodo.izquiendo) * recorrerNodo(nodo.derecho);
        }
        if (nodo.tipoNodo == TipoNodo.nodo_Sum) {
            return recorrerNodo(nodo.izquiendo) + recorrerNodo(nodo.derecho);
        }
        if (nodo.tipoNodo == TipoNodo.nodo_Rest) {
            return recorrerNodo(nodo.izquiendo) - recorrerNodo(nodo.derecho);
        }
        return 0;
    }

    public void imprimirTabla() {
        Set<String> identificadores = tabla.keySet();
        for (String i : identificadores
        ) {
            System.out.println("|identificador: " + i + " | valor:" + tabla.get(i) + "|");
        }
    }

    public int obtenerId(String identificador) {
        return tabla.get(identificador);
    }
}
