package AnalizadorSemantico;

import AnalizadorSintactico.AnalizadorSintactico;
import AnalizadorSintactico.Nodo;
import Analizadorlexico.AnalizadorLexico;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.Scanner;

import static Analizadorlexico.AnalizadorLexico.error;
import static org.junit.jupiter.api.Assertions.*;

class AnalizadorSemanticoTest {

    //Variables para capturar la impresion en consola en los tests.
    private final ByteArrayOutputStream out = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    // Se utiliza para crear el arbol de nodos que se utilizara para imprimir los resultados de las operaciones.
    @BeforeEach
    public void setUp(){
        System.setOut(new PrintStream(out));
        String input = "a = 25;\n b = 300;\n c = a+b;\nimprime c;";
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


    // Intenta crear un arbol con 5000 nodos y espera un StackOverFlowError.
    @Test
    public void pruebaEstresCon10000ValoresParatabladeSimbolos(){
        boolean error = true;
        try {
            StringBuilder input = new StringBuilder(10000);
            for (int i = 0; i < 5000; i++) {
                input.append("a" + i + " = 2;\n");
            }
            AnalizadorLexico analizadorLexico = new AnalizadorLexico(input.toString());
            AnalizadorSintactico analizadorSintactico = new AnalizadorSintactico(analizadorLexico.obtenerTokens());
            Nodo raiz = analizadorSintactico.parse();
            AnalizadorSemantico analizadorSemantico = new AnalizadorSemantico(raiz);
            analizadorSemantico.recorrerArbol(raiz);
        }catch (RuntimeException e){
            error = false;
        }
        assertTrue(error);

    }


    // Libera variables que se utilizan para probar la impresion en consola.
    @AfterEach
    public void restoreInitialStreams() {
        System.setOut(originalOut);
    }
}