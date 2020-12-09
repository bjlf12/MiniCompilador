package AnalizadorSemantico;

import AnalizadorSintactico.AnalizadorSintactico;

import AnalizadorSintactico.Nodo;
import Analizadorlexico.AnalizadorLexico;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;

import java.util.Set;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.Scanner;
<<<<<<< HEAD
import java.util.Set;

=======
>>>>>>> aa090ae3bce9534a0472cab0ae6118a76b62f031
import static Analizadorlexico.AnalizadorLexico.error;


import static org.junit.jupiter.api.Assertions.*;

class AnalizadorSemanticoTest {


    //Variables para capturar la impresion en consola en los tests.
    private final ByteArrayOutputStream out = new ByteArrayOutputStream();
    private AnalizadorSemantico analizadorSemantico;
<<<<<<< HEAD

=======
    private Nodo raiz;
    private final PrintStream originalOut = System.out;
>>>>>>> aa090ae3bce9534a0472cab0ae6118a76b62f031

    // Se utiliza para crear el arbol de nodos que se utilizara para imprimir los resultados de las operaciones.
    @BeforeEach
<<<<<<< HEAD
    void setUp() {
        System.setOut(originalOut);
        System.setOut(new PrintStream(outContent));
        String input = "a = 25;\n b = 300;\n c = a+b;\n d=b/a;\n e=a-b;\n imprime c;\n imprime d;\n imprime e;";
        AnalizadorLexico analizadorLexico = new AnalizadorLexico(input);
        AnalizadorSintactico analizadorSintactico = new AnalizadorSintactico(analizadorLexico.obtenerTokens());
        Nodo raiz = analizadorSintactico.parse();
        analizadorSemantico = new AnalizadorSemantico(raiz);
        analizadorSemantico.recorrerArbol(raiz);
=======
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
>>>>>>> aa090ae3bce9534a0472cab0ae6118a76b62f031

    // Test que compara el valor del resultado de la resta generado por el codigo
    @Test
    public void correctitud_valor_resultado_resta_fase_semantica() {
        String lines[] = out.toString().split("\\r?\\n");
        assertEquals("-275",lines[2]);
    }

<<<<<<< HEAD
=======


    @AfterEach
    void tearDown() {
        System.setOut(originalOut);
    }
>>>>>>> aa090ae3bce9534a0472cab0ae6118a76b62f031

    @Test
    void retornarColumnaIndentificadoresDadasLasEntradas() {

        Set<String> identificadores = analizadorSemantico.getTablaSimbolos().getTabla().keySet();
        for (String i : identificadores
        ) {
            System.out.println("identificador: " + i);
        }
        assertEquals("325\r\n" +
                "12\r\n" +
                "-275\r\n" +
                "identificador: a\r\n" +
                "identificador: b\r\n" +
                "identificador: c\r\n" +
                "identificador: d\r\n" +
                "identificador: e\r\n",outContent.toString());
    }

    @Test
    void retornarColumnaValoresDadasLasEntradas() {

        Set<String> identificadores = analizadorSemantico.getTablaSimbolos().getTabla().keySet();
        for (String i : identificadores
        ) {
            System.out.println("valor: " + analizadorSemantico.getTablaSimbolos().getTabla().get(i));
        }
        assertEquals("325\r\n" +
                "12\r\n" +
                "-275\r\n" +
                "valor: 25\r\n" +
                "valor: 300\r\n" +
                "valor: 325\r\n" +
                "valor: 12\r\n" +
                "valor: -275\r\n", outContent.toString());
    }

    @Test
    void recorrerArbol() {
        analizadorSemantico.getTablaSimbolos().imprimirTabla();
        assertEquals("325\r\n" +
                "12\r\n" +
                "-275\r\n" +
                "|identificador: a | valor:25|\r\n" +
                "|identificador: b | valor:300|\r\n" +
                "|identificador: c | valor:325|\r\n" +
                "|identificador: d | valor:12|\r\n" +
                "|identificador: e | valor:-275|\r\n", outContent.toString());
    }


    // Test que compara el valor del resultado de la suma generado por el codigo con el correcto.
    @Test
    public void correctitud_valor_resultado_suma_fase_semantica() {
        String lines[] = outContent.toString().split("\\r?\\n");
        assertEquals("325",lines[0]);
    }


    @Test
    public void correctitud_valor_resultado_division_fase_semantica() {
        String lines[] = outContent.toString().split("\\r?\\n");
        assertEquals("12",lines[1]);
    }

    // Test que compara el valor del resultado de la resta generado por el codigo
    @Test
    public void correctitud_valor_resultado_resta_fase_semantica() {
        String lines[] = outContent.toString().split("\\r?\\n");
        assertEquals("-275",lines[2]);
    }


r
    // Libera variables que se utilizan para probar la impresion en consola.



    @AfterEach
    public void restoreInitialStreams() {
        System.setOut(originalOut);


    }
<<<<<<< HEAD

}
=======
}

>>>>>>> aa090ae3bce9534a0472cab0ae6118a76b62f031
