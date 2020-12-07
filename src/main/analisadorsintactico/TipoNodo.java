package analisadorsintactico;

import generadorcodigo.Mnemonico;

public enum TipoNodo {
    nodo_Nada("", Mnemonico.NONE), nodo_Mult("Multiplicacion", Mnemonico.MUL), nodo_Div("Division", Mnemonico.DIV), nodo_Sum("Suma", Mnemonico.ADD), nodo_Rest("Resta", Mnemonico.SUB),
    nodo_Asignar("Asignar", Mnemonico.NONE), nodo_Identificador("Id", Mnemonico.NONE), nodo_Digito("Digito", Mnemonico.NONE), nodo_PuntoComa("Punto y coma", Mnemonico.NONE), nodo_Imprimir("Imprimir", Mnemonico.NONE),
    nodo_Declaracion("Declaracion", Mnemonico.NONE), nodo_ParentesisIzquierdo("ParentesisIzquierdo", Mnemonico.NONE), nodo_ParentesisDerecho("ParentesisDerecho", Mnemonico.NONE);

    private String nombre;
    private Mnemonico mnemonico;

    TipoNodo(String nombre, Mnemonico mnemonico) {
        this.nombre = nombre;
        this.mnemonico = mnemonico;
    }

    @Override
    public String toString() {
        return this.nombre;
    }

    public Mnemonico getMnemonico() {
        return mnemonico;
    }

}
