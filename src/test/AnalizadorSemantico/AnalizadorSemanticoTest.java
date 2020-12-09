package AnalizadorSemantico;

import AnalizadorSintactico.AnalizadorSintactico;
import AnalizadorSintactico.Nodo;
import Analizadorlexico.AnalizadorLexico;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;

class AnalizadorSemanticoTest {

    //Variables para capturar la impresion en consola en los tests.
    private final ByteArrayOutputStream out = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    // Se utiliza para crear el arbol de nodos que se utilizara para imprimir los resultados de las operaciones.
    @BeforeEach
    public void setUp(){
        System.setOut(new PrintStream(out));
        String input = "a = 25;\n b = 300;\n c = a+b;\n d=b/a;\nimprime c;\n imprime d;";
        AnalizadorLexico analizadorLexico = new AnalizadorLexico(input);
        AnalizadorSintactico analizadorSintactico = new AnalizadorSintactico(analizadorLexico.obtenerTokens());
        Nodo raiz = analizadorSintactico.parse();
        AnalizadorSemantico analizadorSemantico = new AnalizadorSemantico(raiz);
        analizadorSemantico.recorrerArbol(raiz);
    }
    // Test que compara el valor del resultado de la suma generado por el codigo con el correcto.
    @Test
    public void correctitud_valor_resultado_suma_fase_semantica() {
        String lines[] = out.toString().split("\\r?\\n");
        assertEquals("325",lines[0]);
    }

    @Test
    public void correctitud_valor_resultado_division_fase_semantica() {
        String lines[] = out.toString().split("\\r?\\n");
        assertEquals("12",lines[1]);
    }

    // Libera variables que se utilizan para probar la impresion en consola.
    @AfterEach
    public void restoreInitialStreams() {
        System.setOut(originalOut);
    }
}