package AnalisadorSintactico;

public enum TipoNodo {
    nodo_Nada(""), nodo_Mult("Multiplicacion"), nodo_Div("Division"), nodo_Sum("Suma"), nodo_Rest("Resta"),
    nodo_Asignar("Asignar"), nodo_Identificador("Id"), nodo_Digito("Digito"), nodo_PuntoComa("Punto y coma"), nodo_Imprimir("Imprimir"), nodo_Declaracion("Declaracion"), nodo_ParentesisIzquierdo("ParentesisIzquierdo"), nodo_ParentesisDerecho("ParentesisDerecho");

    private String nombre;

    TipoNodo(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public String toString() {
        return this.nombre;
    }
}
