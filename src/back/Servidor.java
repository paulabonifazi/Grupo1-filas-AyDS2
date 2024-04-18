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
			System.out.println("Ingresar contrase�a de conexion: (no puede ser vacia)");
			boolean valida=false;
			String contrase�a=null;
			do {
				contrase�a = scanner.nextLine();
				if(!contrase�a.isEmpty() && contrase�a!=null && !contrase�a.isBlank())
					valida=true;
				else
					System.out.println("Error, reingrese la contrase�a (no puede ser vacia)");
			}while(valida);
			ParametrosDeConexion parametros= new ParametrosDeConexion(contrase�a); //ingresar contrase�a al empezar el servidor 
			GestorConexion gestorConexion= new GestorConexion(cola,bufferSalida,historico,parametros,contrase�a);
			gestorConexion.start();
			while (true) {
				System.out.println("Seleccione una opcion:\n 1)Mostrar puerto de entrada)\n 2)Mostrar contrase�a de conexi�n \n 3)Cambiar contrase�a de conexi�n\n");
				int opcion = scanner.nextInt();
                switch (opcion) {
                    case 1:
                        System.out.println("El n�mero de puerto de entrada es: "+ parametros.getPuertoLibre()+"\n");
                        break;
                    case 2:
                    	System.out.println("El n�mero de puerto de entrada es: "+ parametros.getContrase�a()+"\n");
                        break;
                    case 3:
                        System.out.println("Ingrese la nueva contrase�a:");
                        break;
                    case 4:
                        System.out.println("Saliendo del programa...");
                        return;
                    default:
                        System.out.println("Opci�n no v�lida. Intente nuevamente.");
                }
			}
		}
				
	}
		

}
