package AnalisadorSintactico;

import AnalisadorLexico.TipoToken;
import AnalisadorLexico.Token;

import java.util.List;

public class AnalisadorSintactico {
    private List<Token> tokens;
    private Token token;
    private int posicion;

    public void error(int linea, int pos, String mensaje) {
        if (linea > 0 && pos > 0) {
            System.out.printf("%s en la linea %d, posición %d\n", mensaje, linea, pos);
        } else {
            System.out.println(mensaje);
        }
        System.exit(1);
    }

    public AnalisadorSintactico (List<Token> tokens){
        this.tokens = tokens;
        this.token = null;
        this.posicion = 0;
    }

    public Token obtenerSiguienteToken() {
        this.token = this.tokens.get(this.posicion++);
        return this.token;
    }

    public Nodo expresion(int precedencia) {
        Nodo resultado = null, nodo;
        TipoToken tipoToken;
        int q;
        if(this.token.tipoToken == TipoToken.ParentesisIzquierdo) {
            resultado = exp_parentesis();
        }
        else if (this.token.tipoToken == TipoToken.ExprecionSum || this.token.tipoToken == TipoToken.ExpresionRes) {
            tipoToken = (this.token.tipoToken == TipoToken.ExpresionRes) ? TipoToken.ExpresionRes : TipoToken.ExprecionSum;
            obtenerSiguienteToken();
            nodo = expresion(TipoToken.ExpresionRes.getPrecedencia());
            resultado = (tipoToken == TipoToken.ExpresionRes) ? Nodo.nuevoNodo(TipoNodo.nodo_Rest, nodo) : nodo;
        }
        else if (this.token.tipoToken == TipoToken.Identificador) {
            resultado = Nodo.nuevaHoja(TipoNodo.nodo_Identificador, this.token.valor);
            obtenerSiguienteToken();
        }
        else if (this.token.tipoToken == TipoToken.Digito){
            resultado = Nodo.nuevaHoja(TipoNodo.nodo_Digito, this.token.valor);
            obtenerSiguienteToken();
        }
        else {
            error(this.token.linea, this.token.pos, "Esperando un primario, encontrado: " + this.token.tipoToken);
         }
        while (this.token.tipoToken.isEsBinario() && this.token.tipoToken.getPrecedencia() >= precedencia) {
            tipoToken = this.token.tipoToken;
            obtenerSiguienteToken();
            q = tipoToken.getPrecedencia();
            if (!tipoToken.isAsociacionPorDerecha()) {
                q++;
            }
            nodo = expresion(q);
            resultado = Nodo.nuevoNodo(tipoToken.getTipoNodo(), resultado, nodo);
        }
        return  resultado;
    }
    public Nodo exp_parentesis() {
        esperar("exp_parentesis", TipoToken.ParentesisIzquierdo);
        Nodo node = expresion(0);
        esperar("exp_parentesis", TipoToken.ParentesisDerecho);
        return node;
    }

    public Nodo sentencia() {
        Nodo n1, n2, n3, n4, n5, n6;
        Nodo resultado = null;
        if (this.token.tipoToken == TipoToken.Imprime) {
            n1 = Nodo.nuevaHoja(TipoNodo.nodo_Imprimir, "imprime");
            obtenerSiguienteToken();

            n2 = Nodo.nuevaHoja(TipoNodo.nodo_Identificador, this.token.valor);
            esperar("imprime", TipoToken.Identificador);

            esperar("imprime", TipoToken.PuntoComa);
            n3 = Nodo.nuevaHoja(TipoNodo.nodo_PuntoComa, ";");

            n4 = Nodo.nuevoNodo(TipoNodo.nodo_Imprimir, n2, n3);
            resultado = Nodo.nuevoNodo(TipoNodo.nodo_Imprimir, n1, n4);
        }
        else if(this.token.tipoToken == TipoToken.Identificador) {
            n1 = Nodo.nuevaHoja(TipoNodo.nodo_Identificador, this.token.valor);
            obtenerSiguienteToken();

            esperar("asignacion", TipoToken.Asignacion);
            n2 = Nodo.nuevaHoja(TipoNodo.nodo_Asignar, "=");

            n3 = expresion(0);

            esperar("imprime", TipoToken.PuntoComa);
            n4 = Nodo.nuevaHoja(TipoNodo.nodo_PuntoComa, ";");

            n5 = Nodo.nuevoNodo(TipoNodo.nodo_Asignar, n3, n4);
            n6 = Nodo.nuevoNodo(TipoNodo.nodo_Asignar, n2, n5);
            resultado = Nodo.nuevoNodo(TipoNodo.nodo_Asignar, n1, n6);
        }
        else {
            error(this.token.linea, this.token.pos, "Esperando el inicio de una declaracion, encontrado: " + this.token.tipoToken);
        }

        return resultado;
    }

    void esperar(String mensaje, TipoToken tipoToken) {
        if(this.token.tipoToken == tipoToken) {
            obtenerSiguienteToken();
            return;
        }
        error(this.token.linea, this.token.pos, mensaje + ": Esperada '" + tipoToken + "', found: '" + this.token.tipoToken + "'");
    }

    public Nodo parse() {
        Nodo t = null;
        obtenerSiguienteToken();
        while (this.token.tipoToken != TipoToken.EOI) {
            t = Nodo.nuevoNodo(TipoNodo.nodo_Declaracion, t, sentencia());
        }
        return t;
    }

    public void imprimirArbol(Nodo nodo) {
        int i = 0;
        if (nodo == null){
            //System.out.println("kaka");
        } else {
            System.out.printf("%-14s", nodo.tipoNodo);
            if (nodo.tipoNodo == TipoNodo.nodo_Identificador || nodo.tipoNodo == TipoNodo.nodo_Digito) {
                System.out.println(" " + nodo.valor);
            } else {
                System.out.println();
                imprimirArbol(nodo.izquiendo);
                imprimirArbol(nodo.derecho);
            }
        }
    }
}