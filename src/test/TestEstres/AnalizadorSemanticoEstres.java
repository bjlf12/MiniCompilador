package TestEstres;

import AnalizadorSemantico.AnalizadorSemantico;
import AnalizadorSintactico.AnalizadorSintactico;
import Analizadorlexico.AnalizadorLexico;
import org.junit.jupiter.api.Test;
import AnalizadorSintactico.Nodo;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class AnalizadorSemanticoEstres {

    @Test
    public void pruebaEstresCon5000ValoresParatabladeSimbolos(){
        boolean error = true;
        try {
            StringBuilder input = new StringBuilder(10000);
            for (int i = 0; i < 5000; i++) {
                input.append("a" + i + " = 2;\n");
            }
            AnalizadorLexico analizadorLexico1 = new AnalizadorLexico(input.toString());
            AnalizadorSintactico analizadorSintactico1 = new AnalizadorSintactico(analizadorLexico1.obtenerTokens());
            Nodo raiz = analizadorSintactico1.parse();
            AnalizadorSemantico analizadorSemantico1 = new AnalizadorSemantico(raiz);
            analizadorSemantico1.recorrerArbol(raiz);
        }catch (RuntimeException e){
            error = false;
        }

        assertTrue(error);



    }

}
