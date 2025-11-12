package pkFases;
import java.util.Scanner;


public class fase1 {
    Scanner input = new Scanner(System.in);
    public String binario = "";

    public void ejecutar() {
        System.out.println("\n==CONVERTIR A OTROS SISTEMAS NUMERICOS==\n");
        System.out.print("1. Binario\n2. Octal\n3. Hexadecimal\nIngresa una opción: ");
        int entrada = input.nextInt();
        
        switch (entrada) {
            case 1:
                int n1 = ingresar();
                System.out.print("Resultado en binario: ");
                decimalABinario(n1);
                break;
            case 2:
                int n2 = ingresar();
                System.out.print("Resultado en octal: ");
                decimalAOctal(n2);
                break;
            case 3:
                int n3 = ingresar();
                System.out.print("Resultado en hexadecimal: ");
                decimalAHexadecimal(n3);
                break;
            default:
                System.out.println("Opción no válida.");
        }

        System.out.println(); 
    }

    public void decimalABinario(int n) {
        if (n > 0) {
            decimalABinario(n / 2);
            System.out.print(n % 2);
        }
    }

    public void decimalAOctal(int n) {
        if (n > 0) {
            decimalAOctal(n / 8);
            System.out.print(n % 8);
        }
    }

    public void decimalAHexadecimal(int n) {
        if (n > 0) {
            decimalAHexadecimal(n / 16);
            int resto = n % 16;
            if (resto < 10)
                System.out.print(resto);
            else
                System.out.print((char) ('A' + (resto - 10)));
        }
    }

    public String getBinario() {
        return binario;
    }

    public void limpiarBinario() {
        binario = "";
    }

    public int ingresar() {
        System.out.print("Ingresa el numero entero decimal positivo a transformar: ");
        return input.nextInt();
    }
}
