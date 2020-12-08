package AnalizadorSintactico;

import AnalizadorSintactico.AnalizadorSintactico.*;
import AnalizadorSintactico.Nodo.*;
import AnalizadorSintactico.TipoNodo.*;
import Analizadorlexico.AnalizadorLexico;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class AnalizadorSintacticoTest {


    //Variables para capturar la impresion en consola en los tests.
    private final ByteArrayOutputStream out = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;
    private final PrintStream originalErr = System.err;

    // Se utiliza para crear el arbol de nodos que se utilizara para imprimirlo y probar.
    @BeforeEach
    public void setUp(){
        System.setOut(new PrintStream(out));
        Nodo nodo13 = new Nodo(TipoNodo.nodo_Identificador,null,null,"x");
        Nodo nodo12 = new Nodo(TipoNodo.nodo_Digito,null,null,"50");
        Nodo nodo11 = new Nodo(TipoNodo.nodo_Asignar,null,null,"");
        Nodo nodo10 = new Nodo(TipoNodo.nodo_Declaracion,null,null,"");
        Nodo nodo9 = new Nodo(TipoNodo.nodo_Identificador,null,null,"z");
        Nodo nodo8 = new Nodo(TipoNodo.nodo_Digito,null,null,"300");
        Nodo nodo7 = new Nodo(TipoNodo.nodo_Digito,null,null,"10");
        Nodo nodo6 = new Nodo(TipoNodo.nodo_Identificador,null,null,"x");
        Nodo nodo5 = new Nodo(TipoNodo.nodo_Digito,null,null,"50");
        Nodo nodo4 = new Nodo(TipoNodo.nodo_Rest,null,null,"");
        Nodo nodo3 = new Nodo(TipoNodo.nodo_Mult,null,null,"");
        Nodo nodo2 = new Nodo(TipoNodo.nodo_Sum,null,null,"");
        Nodo nodo1 = new Nodo(TipoNodo.nodo_Asignar,null,null,"");
        Nodo nodo_raiz = new Nodo(TipoNodo.nodo_Declaracion,null,null,"");
        nodo_raiz.izquiendo = nodo10;
        nodo10.derecho = nodo1;
        nodo1.izquiendo = nodo6;
        nodo1.derecho = nodo5;
        nodo_raiz.derecho = nodo11;
        nodo11.izquiendo = nodo9;
        nodo11.derecho = nodo2;
        nodo2.izquiendo = nodo8;
        nodo2.derecho = nodo3;
        nodo3.izquiendo = nodo7;
        nodo3.derecho = nodo4;
        nodo4.izquiendo = nodo13;
        nodo4.derecho = nodo12;
        AnalizadorSintactico analizadorSintactico = new AnalizadorSintactico(new ArrayList<>());
        analizadorSintactico.imprimirArbol(nodo_raiz);
    }

    // Test que compara el arbol creado manualmente con el generado en la prueba.
    @Test
    void correctitud_del_arbol_generado_en_la_fase_sintactica() {
        assertEquals("Declaracion   \r\n" +
                "Declaracion   \r\n" +
                ";\r\n" +
                "Asignar       \r\n" +
                "Id             x\r\n" +
                "Digito         50\r\n" +
                "Asignar       \r\n" +
                "Id             z\r\n" +
                "Suma          \r\n" +
                "Digito         300\r\n" +
                "Multiplicacion\r\n" +
                "Digito         10\r\n" +
                "Resta         \r\n" +
                "Id             x\r\n" +
                "Digito         50\r\n",out.toString());
    }
    // Libera variables que se utiliza para probar la impresion en consola.
    @AfterEach
    public void restoreInitialStreams() {
        System.setOut(originalOut);
        System.setErr(originalErr);
    }


}