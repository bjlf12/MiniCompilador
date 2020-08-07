package AnalisadorLexico;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AnalixadorLexico {
    private int linea;
    private int pos;
    private int posicion;
    private char chr;
    private String codigo;
    private Map<String, TipoToken> palabras = new HashMap<>();

    public static void error(int linea, int pos, String mensaje) {
        if (linea > 0 && pos > 0) {
            System.out.printf("%s en la linea %d, posición %d\n", mensaje, linea, pos);
        } else {
            System.out.println(mensaje);
        }
        System.exit(1);
    }

    public AnalixadorLexico (String codigo) {
        this.linea = 1;
        this.pos = 0;
        this.posicion = 0;
        this.codigo = codigo;
        this.chr = this.codigo.charAt(0);
        this.palabras.put("imprime", TipoToken.Imprime);
    }

    public Token follow(char esperar, TipoToken sies, TipoToken sino, int linea, int pos) {
        if (obtenerSiguienteCaracter() == esperar) {
            obtenerSiguienteCaracter();
            return  new Token(sies, "", linea, pos);
        }
        if (sino == TipoToken.EOI) {
            error(linea, pos, String.format("Siguiente: caracter inreconocido (%d) '%c'", (int)this.chr, this.chr));
        }
        return new Token(sino, "", linea, pos);
    }

    Token identificadorODigito(int linea, int pos) {
        boolean esDigito = true;
        String texto = "";

        while (Character.isAlphabetic(this.chr) || Character.isDigit(this.chr) || this.chr == '_') {
            texto += this.chr;
            if (!Character.isDigit(this.chr)) {
                esDigito = false;
            }
            obtenerSiguienteCaracter();
        }

        if (texto.equals("")) {
            error(linea, pos, String.format("Caracter inreconocible: (%d) %c", (int)this.chr, this.chr));
        }

        if (Character.isDigit(texto.charAt(0))) {
            if (!esDigito) {
                error(linea, pos, String.format("Digito invalido: %s", texto));
            }
            return new Token(TipoToken.Digito, texto, linea, pos);
        }

        if (this.palabras.containsKey(texto)) {
            return new Token(this.palabras.get(texto), "", linea, pos);
        }
        return new Token(TipoToken.Identificador, texto, linea, pos);
    }

    Token obtenerToken() {
        int linea, pos;
        while (Character.isWhitespace(this.chr)) {
            obtenerSiguienteCaracter();
        }
        linea = this.linea;
        pos = this.pos;

        switch (this.chr) {
            case '\u0000': return new Token(TipoToken.EOI, "", this.linea, this.pos);
            case '(': obtenerSiguienteCaracter(); return new Token(TipoToken.ParentesisIzquierdo, "", linea, pos);
            case ')': obtenerSiguienteCaracter(); return new Token(TipoToken.ParentesisDerecho, "", linea, pos);
            case '=': obtenerSiguienteCaracter(); return new Token(TipoToken.Asignacion, "", linea, pos);
            case '+': obtenerSiguienteCaracter(); return new Token(TipoToken.ExprecionSum, "", linea, pos);
            case '–': obtenerSiguienteCaracter(); return new Token(TipoToken.ExpresionRes, "", linea, pos);
            case '*': obtenerSiguienteCaracter(); return new Token(TipoToken.TerminoMult, "", linea, pos);
            case '/': obtenerSiguienteCaracter(); return new Token(TipoToken.TerminoDiv, "", linea, pos);
            case ';': obtenerSiguienteCaracter(); return new Token(TipoToken.PuntoComa, "", linea, pos);
            default: return identificadorODigito(linea, pos);
        }
    }

    public char obtenerSiguienteCaracter() {
        this.pos++;
        this.posicion++;
        if (this.posicion >= this.codigo.length()) {
            this.chr = '\u0000';
            return this.chr;
        }
        this.chr = this.codigo.charAt(this.posicion);
        if (this.chr == '\n') {
            this.linea++;
            this.pos = 0;
        }
        return this.chr;
    }

    public List obtenerTokens() {
        List<Token> listaToken = new ArrayList<Token>();
        Token token;
        while ((token = obtenerToken()).tipoToken != TipoToken.EOI) {
            listaToken.add(token);
        }
        listaToken.add(token);
        return listaToken;
    }

    public void imprimirTokens() {
        Token token;
        while ((token = obtenerToken()).tipoToken != TipoToken.EOI) {
            System.out.println(token);
        }
        System.out.println(token);
    }

}
