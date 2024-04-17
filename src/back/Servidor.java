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
		Scanner scanner = new Scanner(System.in);
		String contrase�a = scanner.nextLine();
		Conexiones conexiones= new Conexiones(contrase�a); 
		GestorConexion gestorConexion= new GestorConexion(cola,bufferSalida,historico,conexiones);
		gestorConexion.start();
		
		scanner.close();
	}

}
