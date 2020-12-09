package AnalizadorLexico;

import AnalizadorSemantico.AnalizadorSemantico;
import AnalizadorSintactico.Nodo;
import Analizadorlexico.AnalizadorLexico;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AnalizadorLexicoTest {


    @Test
    public void pruebaEstresCon10000ValoresParaAnalizadorLexico(){
        boolean error = true;
        try {
            StringBuilder input = new StringBuilder(10000);
            for (int i = 0; i < 10000; i++) {
                input.append("a" + i + " = 2;\n");
            }
            AnalizadorLexico analizadorLexico = new AnalizadorLexico(input.toString());
        }catch (RuntimeException e){
            error = false;
        }
        assertTrue(error);
    }

}