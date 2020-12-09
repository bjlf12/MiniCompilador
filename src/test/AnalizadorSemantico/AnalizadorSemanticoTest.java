package AnalizadorSemantico;

import AnalizadorSintactico.AnalizadorSintactico;
import AnalizadorSintactico.Nodo;
import Analizadorlexico.AnalizadorLexico;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class AnalizadorSemanticoTest {

    //Variables para capturar la impresion en consola en los tests.
    private final ByteArrayOutputStream out = new ByteArrayOutputStream();
    private AnalizadorSemantico analizadorSemantico;
    private Nodo raiz;
    private final PrintStream originalOut = System.out;

    // Se utiliza para crear el arbol de nodos que se utilizara para imprimir los resultados de las operaciones.
    @BeforeEach
    public void setUp(){
        System.setOut(new PrintStream(out));
        String input = "a = 25;\n b = 300;\n c = a+b;\n d=a/b;\n e=a-b;\n imprime c;\n imprime d;\n imprime e;";
        AnalizadorLexico analizadorLexico = new AnalizadorLexico(input);
        AnalizadorSintactico analizadorSintactico = new AnalizadorSintactico(analizadorLexico.obtenerTokens());
        raiz = analizadorSintactico.parse();
        AnalizadorSemantico analizadorSemantico = new AnalizadorSemantico(raiz);
        analizadorSemantico.recorrerArbol(raiz);
        System.setOut(new PrintStream(out));
    }

    // Test que compara el valor del resultado de la resta generado por el codigo
    @Test
    public void correctitud_valor_resultado_resta_fase_semantica() {
        String lines[] = out.toString().split("\\r?\\n");
        assertEquals("-275",lines[2]);
    }

    // Libera variables que se utilizan para probar la impresion en consola.
    @AfterEach
    public void restoreInitialStreams() {
        System.setOut(originalOut);
    }
}
