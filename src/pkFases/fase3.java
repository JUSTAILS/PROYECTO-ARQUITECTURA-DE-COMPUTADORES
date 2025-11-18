package pkFases;

import java.util.Scanner;

public class fase3 {

    Scanner input = new Scanner(System.in);
    fase1 f1 = new fase1();
    fase2 f2 = new fase2();

    public void ejecutar() {
        System.out.println("\n=== ARITMÉTICA BINARIA Y COMPLEMENTO A DOS ===");
        //Lee un número decimal positivo o negativo
        System.out.print("Ingresa un numero entero (positivo o negativo): ");
        int numero = input.nextInt();
        //Lee cantidad de bits
        System.out.print("Ingresa el numero de bits (por ejemplo 8, 16, 32): ");
        int n = input.nextInt();
        // Paso A: representación en Ca2
        String ca2 = decimalACa2(numero, n);
        if (ca2 == null) {
            System.out.println("No se puede representar el numero en " + n + " bits (overflow).");
            return;
        }
        System.out.println("Complemento a dos (" + n + " bits): " + ca2);
        // Paso B: verificación (volver a decimal)
        int recuperado = ca2ADecimal(ca2);
        System.out.println("Numero decimal recuperado desde Ca2: " + recuperado);
        System.out.println();
    }
    // Paso A: DECIMAL A Ca2
    public String decimalACa2(int numero, int n) {
        // El rango que se puede representar con n bits en Ca2
        int maxPos = (int) Math.pow(2, n - 1) - 1;
        int minNeg = - (int) Math.pow(2, n - 1);
        if (numero < minNeg || numero > maxPos) {
            return null; // overflow
        }
        if (numero >= 0) {
            // Positivo:se usamo decimalABinario de fase1
            String bin = f1.decimalABinario(numero);     
            while (bin.length() < n) {
                bin = "0" + bin;
            }
            return bin;
        } else {
            // Negativo: usar método de Ca2
            int valorAbs = -numero; // |X|
            // se usa decimalABinario de fase1
            String binAbs = f1.decimalABinario(valorAbs);
            // Rellenar con ceros hasta tener n bits seleccionado
            while (binAbs.length() < n) {
                binAbs = "0" + binAbs;
            }
            // Complemento a uno (invertir bits)
            String c1 = complementoUno(binAbs);
            // Sumar 1, complemento a dos
            String ca2 = sumarUnoBinario(c1);
            // Por si en la suma se genera un bit extra, nos quedamos con los últimos n bits
            if (ca2.length() > n) {
                ca2 = ca2.substring(ca2.length() - n);
            }
            return ca2;
        }
    }
    // Complemento a uno: cambia 0 a 1, 1 a 0
    public String complementoUno(String bin) {
        String res = "";
        for (int i = 0; i < bin.length(); i++) {
            char c = bin.charAt(i);
            if (c == '0') {
                res = res + "1";
            } else {
                res = res + "0";
            }
        }
        return res;
    }
    // Suma 1 a un número binario 
    public String sumarUnoBinario(String bin) {
        String resultado = "";
        int carry = 1; // empezamos sumando 1
        for (int i = bin.length() - 1; i >= 0; i--) {
            char c = bin.charAt(i);
            if (c == '0') {
                if (carry == 1) {
                    resultado = "1" + resultado;
                    carry = 0;
                } else {
                    resultado = "0" + resultado;
                }
            } else { 
                if (carry == 1) {
                    resultado = "0" + resultado;
                    carry = 1;
                } else {
                    resultado = "1" + resultado;
                }
            }
        }
        if (carry == 1) {
            resultado = "1" + resultado; // posible bit extra (overflow)
        }
        return resultado;
    }
    // Paso B: Ca2 A DECIMAL
    public int ca2ADecimal(String bin) {
        int n = bin.length();
        // Si el bit más significativo es 0, número positivo
        if (bin.charAt(0) == '0') {
            // Usamos binToDec de fase2
            return f2.binToDec(bin);
        } else {
            // Negativo: invertir, sumar 1, convertir y poner signo -
            String c1 = complementoUno(bin);
            String magBin = sumarUnoBinario(c1);

            // Si se generó un bit extra, tomar los últimos n bits
            if (magBin.length() > n) {
                magBin = magBin.substring(magBin.length() - n);
            }
            int magnitud = f2.binToDec(magBin);
            return -magnitud;
        }
    }
}
