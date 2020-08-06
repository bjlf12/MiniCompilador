package AnalisadorSintactico;

import AnalisadorLexico.TipoToken;
import AnalisadorLexico.Token;
import jdk.nashorn.internal.parser.TokenType;

import java.util.List;

public class AnalisadorSintactico {
    private List<Token> tokens;
    private Token token;
    private  int posicion;

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

    void esperar(String mensaje, TipoToken tipoToken) {
        if(this.token.tipoToken == tipoToken) {
            obtenerSiguienteToken();
            return;
        }
        error(this.token.linea, this.token.pos, mensaje + ": Esperada '" + tipoToken + "', found: '" + this.token.tipoToken + "'");
    }

    void imprimirArbol(Nodo nodo) {
        int i = 0;
        if (nodo == null){
            System.out.println(";");
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