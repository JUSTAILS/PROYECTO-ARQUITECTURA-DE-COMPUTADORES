package pkFases;
import java.util.Scanner;


public class fase1 {
    Scanner input = new Scanner(System.in);

    public void ejecutar() {
        System.out.println("==CONVERTIR DECIMAL A OTRAS BASES==");
        System.out.print("1. Decimal a Binario\n2. Decimal a Octal\n3. Decimal a Hexadecimal\n4. Todas\nIngresa una opción: ");
        int entrada;
        while (true) {
            if (input.hasNextInt()) {
                entrada = input.nextInt();
                input.nextLine(); 
                break;
            } else {
                System.out.println("Error: debes ingresar un número del 1 al 4.");
                input.nextLine(); 
                System.out.print("Ingresa una opción: ");
            }
        }

        
        switch (entrada) {
            case 1:
                int n1 = ingresar();
                System.out.print("Resultado en binario: "+convertirABinarioString(n1));
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
            case 4:
                int n4 = ingresar();
                System.out.println("Resultado en todas las bases");
                System.out.print("Binario: "+convertirABinarioString(n4));
                System.out.print("\nOctal: ");
                decimalAOctal(n4);
                System.out.print("\nHexadecimal: ");
                decimalAHexadecimal(n4);
                break;
            default:
                System.out.println("Opción no válida.");
        }

        System.out.println(); 
    }

    public int decimalABinario(int n) {
        if (n == 0) return 0;

        String binario = convertirABinarioString(n);
        return Integer.parseInt(binario);
    }

    private String convertirABinarioString(int n) {
        if (n == 0) return "";
        return convertirABinarioString(n / 2) + (n % 2);
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

        public int ingresar() {
        while (true) {
            System.out.print("Ingresa el número entero decimal positivo a transformar: ");

            if (input.hasNextInt()) {
                int numero = input.nextInt();
                input.nextLine(); 

                if (numero >= 0) {
                    return numero;
                } else {
                    System.out.println("Error: debe ser un entero positivo.");
                }

            } else {
                System.out.println("Error: debes ingresar un número.");
                input.nextLine(); 
            }
        }
    }

}
