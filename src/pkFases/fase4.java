package pkFases;

import java.util.Scanner;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
public class fase4 {
    Scanner input = new Scanner(System.in);
    fase1 f1 = new fase1();
    fase2 f2 = new fase2();


    public void comaFlotante() {    
    System.out.println("\n==Conversion y representacion numerica en coma flotante==\n");
    System.out.print("1. Decimal a coma flotante \n2. Coma flotante a decimal\nSelecciona una opcion: ");
    int opcion = input.nextInt();

    switch (opcion) {
        case 1:
            System.out.print("Ingresa un numero decimal a convertir: ");
            double decimal = input.nextDouble();
            decimalAComaFlotante(decimal);
            break;
        
        case 2:
            System.out.print("Ingresa el numero en coma flotante a convertir: ");
            String flotante = input.next();
            comaFlotanteADecimal(flotante);
            break;

        default:
            System.out.println("Opcion no valida.");
            break;
        }
    }

    public void decimalAComaFlotante(double numero) {
        System.out.println("\n--- CONVERSIÓN DECIMAL A COMA FLOTANTE ---");

        boolean negativo = numero < 0;
        if (negativo) numero = -numero;

        // 1. Determinar el signo
        String signo = negativo ? "1" : "0";
        System.out.println("Signo: " + signo);

        // 2. Separar parte entera y fraccionaria
        int parteEntera = (int) numero;
        double parteFraccionaria = numero - parteEntera;

        System.out.println("Parte entera: " + parteEntera);
        System.out.println("Parte fraccionaria: " + parteFraccionaria);

        // 3. Convertir parte entera a binario usando fase1
        System.out.print("Parte entera en binario: ");
        if (parteEntera == 0) {
            System.out.println("0");
        } else {
            f1.decimalABinario(parteEntera);
            System.out.println();
        }

        // 4. Convertir parte fraccionaria a binario (máx. 10 bits)
        String binFrac = "";
        double fraccion = parteFraccionaria;
        for (int i = 0; i < 10 && fraccion != 0; i++) {
            fraccion *= 2;
            if (fraccion >= 1) {
                binFrac += "1";
                fraccion -= 1;
            } else {
                binFrac += "0";
            }
        }
        System.out.print("Parte fraccionaria en binario: 0." + binFrac + "\n");


        // 5. Obtener cadena completa del binario
        String binEnteraStr = (parteEntera == 0) ? "0" : Integer.toBinaryString(parteEntera);
        String binarioCompleto = binEnteraStr + "." + binFrac;

        // 6. Normalizar (forma 1.xxxxx × 2^e)
        int exponente = binEnteraStr.length() - 1;
        String mantisa = (binEnteraStr + binFrac).substring(1); // quitamos el primer 1

        // 7.  Mostrar resultados de normalización
        System.out.println("Binario sin normalizar: " + binarioCompleto);
        System.out.println("Forma normalizada: 1." + mantisa + " × 2^" + exponente);

        // 8. Calcular exponente sesgado
        int exponenteSesgado = exponente + 127;
        f1.limpiarBinario();
        f1.decimalABinario(exponenteSesgado);
        String exponenteSesgados = f1.getBinario();
        System.out.println("Exponente sin sesgar: " + exponente);
        System.out.print("Exponente sesgado (decimal): " + exponenteSesgado + "\n Exponente sesgado (binario): " + exponenteSesgados);
        


        // 9. Mostrar mantisa (23 bits)
        String mantisa23 = mantisa;
        if (mantisa23.length() > 23) {
            mantisa23 = mantisa23.substring(0, 23); // recortar si excede
        } else {
            while (mantisa23.length() < 23) {
                mantisa23 += "0"; // completar con ceros
            }
        }

        System.out.println("Mantisa (23 bits): " + mantisa23);

        // 10. Mostrar representación final IEEE 754
        System.out.println("\nRepresentación IEEE 754 (32 bits): ");
        System.out.println( signo + exponenteSesgados + mantisa23);

    }

    public void comaFlotanteADecimal(String numero) {
        System.out.println("\n--- CONVERSIÓN COMA FLOTANTE A DECIMAL ---");

        //1. Determinar el signo
        if(numero.charAt(0) == '1') {
            System.out.println("El numero es: -");
        } else if (numero.charAt(0) == '0') {
            System.out.println("El numero es: +");
        } else {
            System.out.println("Formato invalido.");
            return;
        }

        // 2. Extraer exponente
        String exponenteBin = numero.substring(1, 9);
        System.out.println("Exponente (binario): " + exponenteBin);

        InputStream entradaOriginal = System.in;
        try {
            // Crear un flujo de datos que contenga el valor que queremos pasar
            ByteArrayInputStream entradaFalsa = new ByteArrayInputStream((exponenteBin + "\n").getBytes());
            System.setIn(entradaFalsa); // redirigir System.in temporalmente

            // Llamar al método que pide un número desde consola
            f2.binToDec(); // se ejecutará con el valor simulado

        } finally {
            System.setIn(entradaOriginal); // restaurar System.in siempre
        }

        // 3. Calcular el exponente real (hacemos nuestro propio parse)
        int exponenteDecimal = Integer.parseInt(exponenteBin, 2);
        int exponenteCalculo = exponenteDecimal - 127;
        System.out.println("Exponente sesgado: " + exponenteDecimal);
        System.out.println("Exponente real (sin sesgo): " + exponenteCalculo);

        // 4. Extraer mantisa
        String mantisaBin = numero.substring(9, 32);
        System.out.println("Mantisa en binario sin normalizar 1." + mantisaBin);

        double mantisaBinaria = Double.parseDouble(mantisaBin);
        double valorNormalizado = mantisaBinaria * Math.pow(10, -24);
        double valorNormalizadoCompleto = (1 + valorNormalizado) * Math.pow(10, exponenteCalculo);
        System.out.println("Mantisa en decimal: " + valorNormalizadoCompleto);

        // Convertir mantisa binaria a decimal
        double mantisaDecimal = 1.0;
        for (int i = 0; i < mantisaBin.length(); i++) {
            if (mantisaBin.charAt(i) == '1') {
                mantisaDecimal += Math.pow(2, -(i + 1));
            }
        }
        
        // 5. Calcular valor final
        double valorDecimal = mantisaDecimal * Math.pow(2, exponenteCalculo);
        if (numero.charAt(0) == '1') valorDecimal *= -1;

        System.out.println("Valor decimal aproximado: " + valorDecimal);
    }
}
