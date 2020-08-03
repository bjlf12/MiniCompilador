import AnalisadorLexico.AnalixadorLexico;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import static AnalisadorLexico.AnalixadorLexico.error;

public class MiniCompilador {
    public static void main(String[] args) {
        if (args.length > 0) {
            try {

                File file = new File(args[0]);
                Scanner scanner = new Scanner(file);
                String source = " ";
                while (scanner.hasNext()) {
                    source += scanner.nextLine() + "\n";
                }
                AnalixadorLexico analixadorLexico = new AnalixadorLexico(source);
                analixadorLexico.imprimirTokens();
            } catch(FileNotFoundException e) {
                error(-1, -1, "Excepsión: " + e.getMessage());
            }
        } else {
            error(-1, -1, "Entrada invalida");
        }
    }
}
