package Analizadorlexico;

public class Token {
    public TipoToken tipoToken;
    public String valor;
    public int linea;
    public int pos;

    public Token(TipoToken tipoToken, String valor, int linea, int pos) {
        this.tipoToken = tipoToken;
        this.valor = valor;
        this.linea = linea;
        this.pos = pos;
    }

    @Override
    public String toString() {
        String resultado = String.format("%5d  %5d %-15s", this.linea, this.pos, this.tipoToken);
        switch (this.tipoToken) {
            case Digito:
                resultado += String.format("  %4s", this.valor);
                break;
            case Identificador:
                resultado += String.format(" %s", valor);
                break;
        }
        return resultado;
    }

    public String toString2() {
        return String.format("%5d  %5d %-15s %s", this.linea, this.pos, this.tipoToken, this.valor);
    }
    
}
