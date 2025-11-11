package pkFases;

import java.util.Scanner;

public class fase2 {
    int opcion = 0;
    int term = 0;
    int sum = 0;
    Scanner sc = new Scanner(System.in);

    public void mostrarMenu() {
        do {
        
            System.out.println("=== Conversor de Bases ===\n1. Binario a Decimal\n2. Octal a Decimal\n3. Hexadecimal a Decimal\n4. Salir");
            System.out.println("Ingrese una opcion: ");
            opcion = sc.nextInt();

            switch(opcion){
                case 1:
                    sum = 0;
                    sc.nextLine(); // Limpiar el buffer
                    binToDec();
                    break;
                case 2:
                    sum = 0;
                    sc.nextLine(); // Limpiar el buffer
                    octToDec();
                    break;
                case 3:
                    sum = 0;
                    sc.nextLine(); // Limpiar el buffer
                    hexToDec();
                    break;
                case 4:
                    System.out.println("Saliendo...");
                    break;
                default:
                    System.out.println("Opcion invalida. Intente de nuevo.");
            }

        } while(opcion != 4);
    }

    public void binToDec(){
        System.out.println("Ingrese un numero binario: ");
        
        //String bin = ""; //Prueba numero binario
        String bin = sc.nextLine();

        for(int i=0; i<bin.length(); i++){
            char c = bin.charAt(i);
            if(c != '0' && c != '1'){
               System.out.println("El numero ingresado no es binario\n");
               sum = -1;
               break;
            } else {
                term = Character.getNumericValue(c) * (int)Math.pow(2, bin.length() - 1 - i);
                sum += term;
            }
        }
        if(sum != -1){
            System.out.println("El numero decimal es: " + sum + "\n");   
        }
    }

    public void octToDec(){
        System.out.println("Ingrese un numero octal: ");

        
        //String oct = ""; //Prueba numero octal
        String oct = sc.nextLine();

        for(int i=0; i<oct.length(); i++){
            char c = oct.charAt(i);
            if(c < '0' || c > '7'){
               System.out.println("El numero ingresado no es octal\n");
               sum = -1;
               break;
            } else {
                term = Character.getNumericValue(c) * (int)Math.pow(8, oct.length() - 1 - i);
                sum += term;
            }
        }
        if(sum != -1){
            System.out.println("El numero decimal es: " + sum + "\n");   
        }
    }

    public void hexToDec(){
        System.out.println("Ingrese un numero hexadecimal: ");

        //String hex = ""; //Prueba numero hexadecimal
        String hex = sc.nextLine();

        for(int i=0; i<hex.length(); i++){
            char c = hex.charAt(i);
            if(Character.getNumericValue(c) < 0 || Character.getNumericValue(c) > 15){
                System.out.println("El numero ingresado no es hexadecimal\n");
                sum = -1;
                break;
            } else {
                term = Character.getNumericValue(c) * (int)Math.pow(16, hex.length() - 1 - i);
                sum += term;
            }
        }
        if(sum != -1){
            System.out.println("El numero decimal es: " + sum + "\n");   
        }
    }
}
