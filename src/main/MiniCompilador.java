import Analizadorlexico.AnalizadorLexico;
import AnalizadorSemantico.AnalizadorSemantico;
import AnalizadorSintactico.AnalizadorSintactico;
import AnalizadorSintactico.Nodo;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import static Analizadorlexico.AnalizadorLexico.error;

public class MiniCompilador {
    public static void main(String[] args) {
        if (args.length > 0) {
            try {
                File file = new File(args[0]);
                Scanner scanner = new Scanner(file);
                String codigo = " ";
                while (scanner.hasNext()) {
                    codigo += scanner.nextLine() + "\n";
                }
                AnalizadorLexico analizadorLexico = new AnalizadorLexico(codigo);
                //analizadorLexico.imprimirTokens();

                //System.out.println(analizadorLexico.obtenerTokens());
                AnalizadorSintactico analizadorSintactico = new AnalizadorSintactico(analizadorLexico.obtenerTokens());
                Nodo raiz = analizadorSintactico.parse();
                //analizadorSintactico.imprimirArbol(raiz);
                AnalizadorSemantico analizadorSemantico = new AnalizadorSemantico(raiz);
                analizadorSemantico.recorrerArbol(raiz);
                //analizadorSemantico.getTablaSimbolos().imprimirTabla();


                /**GeneradorCodigo generadorCodigo = new GeneradorCodigo();
                 generadorCodigo.generarCodigo(raiz);**/
            } catch (FileNotFoundException e) {
                error(-1, -1, "Excepción: " + e.getMessage());
            } catch (Exception e) {
                error(-1, -1, "Excepción: " + e.getMessage());
            }
        } else {
            error(-1, -1, "Entrada invalida");
        }
    }
}
