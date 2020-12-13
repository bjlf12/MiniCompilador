package Analizadorlexico;

import AnalizadorSintactico.TipoNodo;

public enum TipoToken {
    EOI(false, false, false, -1, TipoNodo.nodo_Nada), TerminoMult(false, true, false, 13, TipoNodo.nodo_Mult),  TerminoDiv(false, true, false, 13, TipoNodo.nodo_Div), ExprecionSum(false, true, false, 12, TipoNodo.nodo_Sum), ExpresionRes(false, true, false, 12, TipoNodo.nodo_Rest),
    Asignacion(false, false, false, -1, TipoNodo.nodo_Asignar), Identificador(false, false, false, -1, TipoNodo.nodo_Identificador), PuntoComa(false, false, false, -1, TipoNodo.nodo_Nada), Digito(false, false, false, -1, TipoNodo.nodo_Digito), ParentesisDerecho(false, false, false, -1, TipoNodo.nodo_Nada),
    ParentesisIzquierdo(false, false, false, -1, TipoNodo.nodo_Nada), Imprime(false, false, false, -1, TipoNodo.nodo_Nada);

    private boolean asociacionPorDerecha;
    private boolean esBinario;
    private boolean esUnario;
    private int precedencia;
    private TipoNodo tipoNodo;

    TipoToken(boolean asociacionPorDerecha, boolean esBinario, boolean esUnario, int precedencia, TipoNodo tipoNodo) {
        this.asociacionPorDerecha = asociacionPorDerecha;
        this.esBinario = esBinario;
        this.esUnario = esUnario;
        this.precedencia = precedencia;
        this.tipoNodo = tipoNodo;
    }

    public boolean isAsociacionPorDerecha() {
        return asociacionPorDerecha;
    }

    public void setAsociacionPorDerecha(boolean asociacionPorDerecha) {
        this.asociacionPorDerecha = asociacionPorDerecha;
    }

    public boolean isEsBinario() {
        return esBinario;
    }

    public void setEsBinario(boolean esBinario) {
        this.esBinario = esBinario;
    }

    public boolean isEsUnario() {
        return esUnario;
    }

    public void setEsUnario(boolean esUnario) {
        this.esUnario = esUnario;
    }

    public int getPrecedencia() {
        return precedencia;
    }

    public void setPrecedencia(int precedencia) {
        this.precedencia = precedencia;
    }

    public TipoNodo getTipoNodo() {
        return tipoNodo;
    }

    public void setTipoNodo(TipoNodo tipoNodo) {
        this.tipoNodo = tipoNodo;
    }
}
