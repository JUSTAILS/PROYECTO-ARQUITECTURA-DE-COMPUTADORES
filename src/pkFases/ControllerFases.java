package pkFases;
import java.util.Scanner;
public class ControllerFases {
    fase1 f1 = new fase1();
    fase2 f2 = new fase2();
    fase3 f3 = new fase3();
    fase4 f4 = new fase4();
    public void IniciarFases(){
        Scanner entrada = new Scanner(System.in);
        boolean salir = false;
        while (!salir) {
            System.out.println("\n==PROYECTO ARQUITECTURA DE COMPUTADORES==");
            System.out.print(
            "\nSelecciona la fase a ejecutar\n" +
            "1. Decimal a otras bases\n" +
            "2. Otras bases a Decimal\n" +
            "3. Aritmética binaria y complemento a Dos\n" +
            "4. Representación en coma flotante\n" +
            "5. Salir\n" +
            "Ingresa una opción: "
        );

            int fase = entrada.nextInt();
            switch (fase) {
                case 1:
                    f1.ejecutar();
                    break;
                case 2:
                    f2.mostrarMenu();
                    break;
                case 3:
                    f3.ejecutar();
                    break;
                case 4:
                    f4.comaFlotante();
                    break;
                case 5:
                    System.out.println("Saliendo del programa.");
                    salir = true;
                    entrada.close();
                    break;
                default:
                    System.out.println("Opción no válida. Intente de nuevo.");
            }
        }
    }
}
