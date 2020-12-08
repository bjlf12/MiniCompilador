package AnalizadorSemantico;

import AnalizadorSintactico.AnalizadorSintactico;
import Analizadorlexico.AnalizadorLexico;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import AnalizadorSintactico.Nodo;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;

class AnalizadorSemanticoTest {

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    private AnalizadorSemantico analizadorSemantico;



    @BeforeEach
    void setUp() {
        System.setOut(new PrintStream(outContent));

        AnalizadorLexico analizadorLexico = new AnalizadorLexico(" x = 100 ; z = 300 + ( 10 - (x + 9) * 50 ) ; w = z / x ;");
        AnalizadorSintactico analizadorSintactico = new AnalizadorSintactico(analizadorLexico.obtenerTokens());
        Nodo raiz = analizadorSintactico.parse();
        analizadorSemantico = new AnalizadorSemantico(raiz);
        analizadorSemantico.recorrerArbol(raiz);

    }

    @AfterEach
    void tearDown() {
        System.setOut(originalOut);
    }

    @Test
    void recorrerArbol() {
        analizadorSemantico.getTablaSimbolos().imprimirTabla();
        assertEquals("|identificador: w | valor:-51|\r\n|identificador: x | valor:100|\r\n|identificador: z | valor:-5140|\r\n",outContent.toString());
    }
}