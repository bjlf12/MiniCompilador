package GeneradorCodigo;

import AnalizadorSintactico.Nodo;
import AnalizadorSintactico.TipoNodo;

import java.util.*;

public class GeneradorCodigo {
    private int tamañoPalabra = 4;
    private static byte[] codigo = {};
    private static Map<String, TipoNodo> stringTipoNodo = new HashMap<>();
    private static List<String> variables = new ArrayList<>();
    private static int contadorVariables = 0;

    private static TipoNodo[] operadores = {
            TipoNodo.nodo_Rest, TipoNodo.nodo_Sum, TipoNodo.nodo_Div, TipoNodo.nodo_Mult
    };

    public void agregarCodigo(int bite) {
        codigo = Arrays.copyOf(codigo, codigo.length + 1);
        codigo[codigo.length + 1] = (byte) bite;
    }

    public void emitirByte(Mnemonico mnemonico) {
        agregarCodigo(mnemonico.ordinal());
    }

    public void emitirPalabra(int n) {
        agregarCodigo(n >> 24);
        agregarCodigo(n >> 16);
        agregarCodigo(n >> 8);
        agregarCodigo(n);
    }

    public void emitirPalabraEn(int posicion, int n) {
        this.codigo[posicion] = (byte) (n >> 24);
        this.codigo[posicion + 1] = (byte) (n >> 16);
        this.codigo[posicion + 2] = (byte) (n >> 8);
        this.codigo[posicion + 3] = (byte) n;
    }

    public int obtenerPalabra(int posicion) {
        return ((codigo[posicion] & 0xff) << 24) + ((codigo[posicion + 1] & 0xff) << 16) + ((codigo[posicion + 2] & 0xff) << 8) + (codigo[posicion + 3] & 0xff);
    }

    public int obtenerCompensacionVariable(String nombre) {
        int n = variables.indexOf(nombre);
        if (n == -1) {
            variables.add(nombre);
            n = contadorVariables++;
        }
        return n;
    }

    public int espacio() { //hole
        int t = codigo.length;
        emitirPalabra(0);
        return t;
    }

    public boolean arrayContiene(TipoNodo[] tipoNodos, TipoNodo nodo) {
        for (TipoNodo i : tipoNodos
        ) {
            if (i.equals(nodo)) {
                return true;
            }
        }
        return false;
    }

    public void generarCodigo(Nodo raiz) throws Exception {
        int n;
        if (raiz == null) return;
        System.out.println(raiz.tipoNodo);
        switch (raiz.tipoNodo) {
            case nodo_Nada:
                return;
            case nodo_Identificador:
                emitirByte(Mnemonico.FETCH);
                n = obtenerCompensacionVariable(raiz.valor);
                emitirPalabra(n);
                break;
            case nodo_Digito:
                emitirByte(Mnemonico.PUSH);
                emitirPalabra(Integer.parseInt(raiz.valor));
            case nodo_Asignar:
                n = obtenerCompensacionVariable(raiz.izquiendo.valor);
                generarCodigo(raiz.derecho);
                emitirByte(Mnemonico.STORE);
                emitirPalabra(n);
                break;
            case nodo_Declaracion:
                generarCodigo(raiz.izquiendo);
                generarCodigo(raiz.derecho);
                break;
            case nodo_Imprimir:
                generarCodigo(raiz.izquiendo);
                emitirByte(Mnemonico.IMPR);
                break;
            default:
                if (arrayContiene(operadores, raiz.tipoNodo)) {
                    generarCodigo(raiz.izquiendo);
                    generarCodigo(raiz.derecho);
                    emitirByte(raiz.tipoNodo.getMnemonico());
                } else {
                    throw new Exception("Errror en el generador de codigo. Encontrado " + raiz.tipoNodo + ", esperando un operador.");
                }
        }
    }

    public void listaCodigo() throws Exception {
        int pc = 0, x;
        Mnemonico operador;
        System.out.println("Datasize: " + contadorVariables);
        while (pc < codigo.length) {
            System.out.printf("%4d ", pc);
            operador = Mnemonico.values()[codigo[pc++]];
            switch (operador) {
                case FETCH:
                    x = obtenerPalabra(pc);
                    System.out.printf("fetch [%d]", x);
                    pc += tamañoPalabra;
                    break;
                case STORE:
                    x = obtenerPalabra(pc);
                    System.out.printf("store [%d]", x);
                    pc += tamañoPalabra;
                    break;
                case PUSH:
                    x = obtenerPalabra(pc);
                    System.out.printf("push  %d", x);
                    pc += tamañoPalabra;
                    break;
                case ADD:
                case SUB:
                case MUL:
                case DIV:
                case IMPR:
                case HALT:
                    System.out.print(operador.toString().toLowerCase());
                    break;
                case JMP:
                    x = obtenerPalabra(pc);
                    System.out.printf("jmp     (%d) %d", x, pc + x);
                    pc += tamañoPalabra;
                    break;
                case JZ:
                    x = obtenerPalabra(pc);
                    System.out.printf("jz      (%d) %d", x, pc + x);
                    pc += tamañoPalabra;
                    break;
                default:
                    throw new Exception("Operador desconocido " + codigo[pc] + "@" + (pc - 1));
            }
            System.out.println();
        }
    }

}
