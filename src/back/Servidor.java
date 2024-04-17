package back;

import java.util.Scanner;

import interfaces.ILlamado;
import interfaces.IRegistro;

public class Servidor extends Thread implements ILlamado, IRegistro{

	public static void main(String[] args) {
		MonitorDeCola cola = new MonitorDeCola();
		MonitorNotificacion bufferSalida=new MonitorNotificacion();
		Atenciones historico= new Atenciones();
		System.out.println("Ingresar contrase�a de conexion");
		try(Scanner scanner = new Scanner(System.in)){
			String contrase�a = scanner.nextLine();
			Conexiones conexiones= new Conexiones(contrase�a); //ingresar contrase�a al empezar el servidor 
			GestorConexion gestorConexion= new GestorConexion(cola,bufferSalida,historico,conexiones);
			gestorConexion.start();
			while (true) {
				System.out.println("Seleccione una opcion:\n 1)Mostrar puerto de entrada)\n 2)Mostrar contrase�a de conexi�n \n 3)Cambiar contrase�a de conexi�n");
				int opcion = scanner.nextInt();
                switch (opcion) {
                    case 1:
                        // Implementa la l�gica para mostrar el puerto de entrada
                        break;
                    case 2:
                        // Implementa la l�gica para mostrar la contrase�a de conexi�n
                        break;
                    case 3:
                        // Implementa la l�gica para cambiar la contrase�a de conexi�n
                        break;
                    case 4:
                        // Detener el servidor y salir del programa
                        // (Implementa la l�gica para detener el servidor si es necesario)
                        System.out.println("Saliendo del programa...");
                        return;
                    default:
                        System.out.println("Opci�n no v�lida. Intente nuevamente.");
                }
			}
		}
				
	}
		

}
