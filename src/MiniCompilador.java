import analisadorlexico.AnalixadorLexico;
import analisadorsemantico.AnalisadorSemantico;
import analisadorsintactico.AnalisadorSintactico;
import analisadorsintactico.Nodo;
import generadorcodigo.GeneradorCodigo;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import static analisadorlexico.AnalixadorLexico.error;

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
                AnalixadorLexico analixadorLexico = new AnalixadorLexico(codigo);
                //analixadorLexico.imprimirTokens();

                //System.out.println(analixadorLexico.obtenerTokens());
                AnalisadorSintactico analisadorSintactico = new AnalisadorSintactico(analixadorLexico.obtenerTokens());
                Nodo raiz = analisadorSintactico.parse();
                //analisadorSintactico.imprimirArbol(raiz);

                AnalisadorSemantico analisadorSemantico = new AnalisadorSemantico(raiz);
                analisadorSemantico.recorrerArbol(raiz);
                //analisadorSemantico.getTablaSimbolos().imprimirTabla();

                GeneradorCodigo generadorCodigo = new GeneradorCodigo();
                generadorCodigo.generarCodigo(raiz);
            } catch (FileNotFoundException e) {
                error(-1, -1, "Excepción: " + e.getMessage());
            } catch (Exception e) {
                System.out.println("Excepción: " + e.getMessage());
            }
        } else {
            error(-1, -1, "Entrada invalida");
        }
    }
}
