package back;

import java.util.Scanner;

import interfaces.ILlamado;
import interfaces.IRegistro;

public class Servidor extends Thread implements ILlamado, IRegistro{

	public static void main(String[] args) {
		MonitorDeCola cola = new MonitorDeCola();
		MonitorNotificacion bufferSalida=new MonitorNotificacion();
		Atenciones historico= new Atenciones();
		
		try(Scanner scanner = new Scanner(System.in)){
			System.out.println("Ingresar contraseña de conexion: (no puede ser vacia)");
			boolean valida=false;
			String contraseña=null;
			do {
				contraseña = scanner.nextLine();
				if(!contraseña.isEmpty() && contraseña!=null && !contraseña.isBlank())
					valida=true;
				else
					System.out.println("Error, reingrese la contraseña (no puede ser vacia)");
			}while(valida);
			ParametrosDeConexion parametros= new ParametrosDeConexion(contraseña); //ingresar contraseña al empezar el servidor 
			GestorConexion gestorConexion= new GestorConexion(cola,bufferSalida,historico,parametros,contraseña);
			gestorConexion.start();
			while (true) {
				System.out.println("Seleccione una opcion:\n 1)Mostrar puerto de entrada)\n 2)Mostrar contraseña de conexión \n 3)Cambiar contraseña de conexión\n");
				int opcion = scanner.nextInt();
                switch (opcion) {
                    case 1:
                        System.out.println("El número de puerto de entrada es: "+ parametros.getPuertoLibre()+"\n");
                        break;
                    case 2:
                    	System.out.println("El número de puerto de entrada es: "+ parametros.getContraseña()+"\n");
                        break;
                    case 3:
                        System.out.println("Ingrese la nueva contraseña:");
                        break;
                    case 4:
                        System.out.println("Saliendo del programa...");
                        return;
                    default:
                        System.out.println("Opción no válida. Intente nuevamente.");
                }
			}
		}
				
	}
		

}
