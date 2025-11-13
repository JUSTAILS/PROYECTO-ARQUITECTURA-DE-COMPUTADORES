package pkFases;

import java.util.Scanner;

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
        int bin = 0;
        System.out.print("Parte entera en binario: ");
        if (parteEntera == 0) {
            System.out.println("0");
        } else {
            bin = f1.decimalABinario(parteEntera);
            System.out.println(bin);
        }

        // 4. Convertir parte fraccionaria a binario (máx. 10 bits)
        String binFrac = "";
        double fraccion = parteFraccionaria;
        if(fraccion == 0) {
            System.out.print("Parte fraccionaria en binario: 0.0\n");
            return;
        } else {
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
        }
        
        // 5. Obtener cadena completa del binario
        String binEnteraStr = String.valueOf(bin);
        String binarioCompleto = binEnteraStr + "." + binFrac;

        // 6. Normalizar (forma 1.xxxxx × 2^e)
        int exponente = binEnteraStr.length() - 1;
        String mantisa = (binEnteraStr + binFrac).substring(1); // quitamos el primer 1

        // 7.  Mostrar resultados de normalización
        System.out.println("Binario sin normalizar: " + binarioCompleto);
        System.out.println("Forma normalizada: 1." + mantisa + " × 2^" + exponente);

        // 8. Calcular exponente sesgado
        int exponenteSesgado = exponente + 127;
        int exponenteSesgados = f1.decimalABinario(exponenteSesgado);
        System.out.println("Exponente sin sesgar: " + exponente);
        System.out.print("Exponente sesgado (decimal): " + exponenteSesgado + "\nExponente sesgado (binario): " + exponenteSesgados);
        
        // 9. Mostrar mantisa (23 bits)
        String mantisa23 = mantisa;
        if (mantisa23.length() > 23) {
            mantisa23 = mantisa23.substring(0, 23); // recortar si excede
        } else {
            while (mantisa23.length() < 23) {
                mantisa23 += "0"; // completar con ceros
            }
        }

        System.out.println("\nMantisa (23 bits): " + mantisa23);

        // 10. Mostrar representación final IEEE 754
        System.out.println("\nRepresentación IEEE 754 (32 bits): ");
        System.out.println( signo + exponenteSesgados + mantisa23);

    }

    public void comaFlotanteADecimal(String numero) {
        System.out.println("\n--- CONVERSIÓN COMA FLOTANTE A DECIMAL ---");

        //1. Determinar el signo
        String signo = "";
        if(numero.charAt(0) == '1') {
            signo = "-";
            System.out.println("El signo es: " + signo);
        } else if (numero.charAt(0) == '0') {
            signo = "+";
            System.out.println("El signo es: " + signo);
        } else {
            System.out.println("Formato invalido.");
            return;
        }

        // 2. Extraer exponente
        String exponenteBin = numero.substring(1, 9);
        System.out.println("Exponente (binario): " + exponenteBin);
        int exponenteDec = f2.binToDec(exponenteBin);
        
        // 3. Calcular el exponente real 
        int exponenteCalculo = exponenteDec - 127;
        System.out.println("Exponente sesgado: " + exponenteDec);
        System.out.println("Exponente real (sin sesgo): " + exponenteCalculo);

        // 4. Extraer mantisa
        String mantisaBin = numero.substring(9, 32);
        System.out.println("Mantisa en binario sin normalizar 1." + mantisaBin);

        double mantisaBinaria = Double.parseDouble(mantisaBin); //paso a double la mantisa
        double valorNormalizado = mantisaBinaria * Math.pow(10, -23); //ajusto la mantisa para que se 0.xxxx
        double resultado = cifrasSignificativas(valorNormalizado, 10); //limito a 10 cifras significativas
        double valorNormalizadoCompleto = (1 + resultado) * Math.pow(10, exponenteCalculo); //agrego el 1 de la normalizacion y aplico el exponente
        double resultadoFinal = cifrasSignificativas(valorNormalizadoCompleto, 10); //limito a 10 cifras significativas el resultado final
        System.out.println("Mantisa en binaria normalizada: " + resultadoFinal);

        // 5. Separar parte entera y fraccionaria
        int parteEntera = (int) resultadoFinal;
        double parteFraccionaria = resultadoFinal - parteEntera;
        double resultadoFracc = cifrasSignificativas(parteFraccionaria, 10);

        System.out.println("Parte entera: " + parteEntera);
        System.out.println("Parte fraccionaria: " + resultadoFracc);

        // 6. Convertir parte entera a decimal usando fase2
        double dec = 0;
        System.out.print("Parte entera en decimal: ");
        if (parteEntera == 0) {
            System.out.println("0");
        } else {
            String parteEnteraString = String.valueOf(parteEntera);
            dec = f2.binToDec(parteEnteraString);
            System.out.println(dec);
        }

        // 7. Convertir parte decimal binaria a decimal decimal 
        String parteFracString = String.valueOf(resultadoFracc);
        parteFracString = parteFracString.replace(",", "."); // reemplaza coma por punto
        int punto = parteFracString.indexOf('.'); // Extrae solo los dígitos después del punto
        if (punto != -1 && punto < parteFracString.length() - 1) {
            parteFracString = parteFracString.substring(punto + 1);
        } else {
            parteFracString = "0";
        }
        
        double decimalfrac = 0.0;
        for (int i = 0; i < parteFracString.length(); i++) {
            char c = parteFracString.charAt(i);
            if (c == '1') {
                decimalfrac += Math.pow(2, -(i + 1));
            }
        }
        System.out.println("Parte fraccionaria en decimal: " + decimalfrac);

        // 8. Calcular valor final
        String valorDecimal = signo + (dec + decimalfrac);
        System.out.println("Valor decimal aproximado: " + valorDecimal);
    }

    //Metodo para limitar cifras significativas
    public static double cifrasSignificativas(double valor, int cifras) {
        if (valor == 0) return 0;
        final double d = Math.ceil(Math.log10(valor < 0 ? -valor : valor));
        final int power = cifras - (int) d;
        final double magnitude = Math.pow(10, power);
        final long shifted = Math.round(valor * magnitude);
        return shifted / magnitude;
    }

}