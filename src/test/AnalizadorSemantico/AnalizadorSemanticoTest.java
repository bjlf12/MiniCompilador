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


    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    private AnalizadorSemantico analizadorSemantico;

    private Nodo raiz;



    @BeforeEach
    void setUp() {


        AnalizadorLexico analizadorLexico = new AnalizadorLexico("a = 2; b = 5; c = b * a; d = 100 / c; f = (d * 50 + (5 * 8)) * (100 * (58 / d)); imprime f;");
        AnalizadorSintactico analizadorSintactico = new AnalizadorSintactico(analizadorLexico.obtenerTokens());
        raiz = analizadorSintactico.parse();
        analizadorSemantico = new AnalizadorSemantico(raiz);
        analizadorSemantico.recorrerArbol(raiz);
        System.setOut(new PrintStream(outContent));


    }

    @AfterEach
    void tearDown() {
        System.setOut(originalOut);
    }

    @Test

    void recorrerArbol() {
        analizadorSemantico.getTablaSimbolos().imprimirTabla();
        assertEquals("|identificador: w | valor:-51|\r\n|identificador: x | valor:100|\r\n|identificador: z | valor:-5140|\r\n",outContent.toString());

    void retornarColumnaIndentificadoresDadasLasEntradas() {

        Set<String> identificadores = analizadorSemantico.getTablaSimbolos().getTabla().keySet();
        for (String i : identificadores
        ) {
            System.out.println("identificador: " + i);
        }
        assertEquals("identificador: a\nidentificador: b\nidentificador: c\nidentificador: d\nidentificador: f\n",outContent.toString());
    }

    @Test
    void retornarColumnaValoresDadasLasEntradas() {

        Set<String> identificadores = analizadorSemantico.getTablaSimbolos().getTabla().keySet();
        for (String i : identificadores
        ) {
            System.out.println("valor: " + analizadorSemantico.getTablaSimbolos().getTabla().get(i));
        }
        assertEquals("valor: 2\nvalor: 5\nvalor: 10\nvalor: 10\nvalor: 270000\n",outContent.toString());


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