package AnalizadorLexico;

import org.junit.*;

import java.io.*;
import java.util.Scanner;


import static org.junit.jupiter.api.Assertions.assertEquals;

import Analizadorlexico.AnalizadorLexico;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CapturaErrorFaseLexica {

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;
    private final PrintStream originalErr = System.err;

    @BeforeEach
    public void setUpStreams() {
        System.setOut(new PrintStream(outContent));
        System.setErr(new PrintStream(errContent));
    }

    @AfterEach
    public void restoreStreams() {
        System.setOut(originalOut);
        System.setErr(originalErr);
    }

    @Test
    public void ManejoError() throws FileNotFoundException {

        File file = new File("src/test/resources/PMC-4.txt");
        Scanner scanner = new Scanner(file);
        String codigo = " ";
        while (scanner.hasNext()) {
            codigo += scanner.nextLine() + "\n";
        }
        AnalizadorLexico analizadorLexico = new AnalizadorLexico(codigo);
        analizadorLexico.imprimirTokens();

        assertEquals("Carácter inrreconocible: (38) & en la línea 2, posición 6", outContent.toString());



    }

}



