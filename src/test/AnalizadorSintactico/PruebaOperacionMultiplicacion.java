package AnalizadorSintactico;

import AnalizadorSintactico.AnalizadorSintactico;
import AnalizadorSemantico.AnalizadorSemantico;
import AnalizadorSintactico.Nodo;
import Analizadorlexico.AnalizadorLexico;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;

public class PruebaOperacionMultiplicacion {

    //Variables para capturar la impresion en consola en los tests.
    private final ByteArrayOutputStream out = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    @BeforeEach
    public void setUp(){
        System.setOut(new PrintStream(out));
        String input = "a = 25;\n b = 400;\n c = a*b;\nimprime c;";
        AnalizadorLexico analizadorLexico = new AnalizadorLexico(input);
        AnalizadorSintactico analizadorSintactico = new AnalizadorSintactico(analizadorLexico.obtenerTokens());
        Nodo raiz = analizadorSintactico.parse();
        AnalizadorSemantico analizadorSemantico = new AnalizadorSemantico(raiz);
        analizadorSemantico.recorrerArbol(raiz);
    }
    // Test que compara el valor del resultado de la multiplicacion generado por el codigo con el correcto.
    @Test
    public void correctitud_valor_resultado_multiplicacion_fase_semantica() {
        String lines[] = out.toString().split("\\r?\\n");
        assertEquals("10000",lines[0]);
    }
}
