package back;

import java.util.Scanner;

import interfaces.ILlamado;
import interfaces.IRegistro;

public class Servidor extends Thread implements ILlamado, IRegistro{

	public static void main(String[] args) {
		MonitorDeCola cola = new MonitorDeCola();
		MonitorNotificacion bufferSalida=new MonitorNotificacion();
		Atenciones historico= new Atenciones();
		System.out.println("Ingresar contraseña de conexion");
		try(Scanner scanner = new Scanner(System.in)){
			String contraseña = scanner.nextLine();
			Conexiones conexiones= new Conexiones(contraseña); //ingresar contraseña al empezar el servidor 
			GestorConexion gestorConexion= new GestorConexion(cola,bufferSalida,historico,conexiones);
			gestorConexion.start();
			while (true) {
				System.out.println("Seleccione una opcion:\n 1)Mostrar puerto de entrada)\n 2)Mostrar contraseña de conexión \n 3)Cambiar contraseña de conexión");
				int opcion = scanner.nextInt();
                switch (opcion) {
                    case 1:
                        // Implementa la lógica para mostrar el puerto de entrada
                        break;
                    case 2:
                        // Implementa la lógica para mostrar la contraseña de conexión
                        break;
                    case 3:
                        // Implementa la lógica para cambiar la contraseña de conexión
                        break;
                    case 4:
                        // Detener el servidor y salir del programa
                        // (Implementa la lógica para detener el servidor si es necesario)
                        System.out.println("Saliendo del programa...");
                        return;
                    default:
                        System.out.println("Opción no válida. Intente nuevamente.");
                }
			}
		}
				
	}
		

}
