package AnalizadorSemantico;

import AnalizadorSintactico.AnalizadorSintactico;
import Analizadorlexico.AnalizadorLexico;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import AnalizadorSintactico.Nodo;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Set;

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
    void retornarColumnaIndentificadoresDadasLasEntradas() {

        Set<String> identificadores = analizadorSemantico.getTablaSimbolos().getTabla().keySet();
        for (String i : identificadores
        ) {
            System.out.println("identificador: " + i);
        }
        assertEquals("identificador: a\r\nidentificador: b\r\nidentificador: c\r\nidentificador: d\r\nidentificador: f\r\n",outContent.toString());
    }

    @Test
    void retornarColumnaValoresDadasLasEntradas() {

        Set<String> identificadores = analizadorSemantico.getTablaSimbolos().getTabla().keySet();
        for (String i : identificadores
        ) {
            System.out.println("valor: " + analizadorSemantico.getTablaSimbolos().getTabla().get(i));
        }
        assertEquals("valor: 2\r\nvalor: 5\r\nvalor: 10\r\nvalor: 10\r\nvalor: 270000\r\n",outContent.toString());
    }
}