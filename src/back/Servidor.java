package back;

import java.util.InputMismatchException;
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
			}while(!valida);
			ParametrosDeConexion parametros= new ParametrosDeConexion(contrase�a); //ingresar contrase�a al empezar el servidor 
			GestorConexion gestorConexion= new GestorConexion(cola,bufferSalida,historico,parametros,contrase�a);
			gestorConexion.start();
			while (!parametros.isFinalizar()) {
				System.out.println("Seleccione una opcion:\n 1)Mostrar puerto de entrada.\n 2)Mostar IP del servidor\n 3)Mostrar contrase�a de conexi�n.\n 4)Cambiar contrase�a de conexi�n.\n 5)Cerrar servidor");
				int opcion=0;
				valida=false;
				do {
					try {
				        opcion = scanner.nextInt(); // Intentar leer un entero
				        valida=true;
				    } catch (InputMismatchException e) {
				        System.out.println("Error: debe ingresar un n�mero entero.");
				    }
					finally {
						scanner.nextLine(); // Limpia el buffer, tanto si lee el caracter despues del int o el string incorrecto
					}
				}while(!valida);
				
                switch (opcion) {
                    case 1:
                        System.out.println("El n�mero de puerto de entrada es: "+ parametros.getPuertoLibre()+"\n");
                        break;
                    case 2:
                        System.out.println("La IP del servidor es: "+ parametros.getIP()+"\n");
                        break;
                    case 3:
                    	System.out.println("La contrase�a es: "+ parametros.getContrase�a()+"\n");
                        break;
                    case 4:
                        System.out.println("Ingrese la nueva contrase�a:(no puede ser vacia)");
                        valida=false;
                        do {
            				contrase�a = scanner.nextLine();
            				if(!contrase�a.isEmpty() && contrase�a!=null && !contrase�a.isBlank())
            					valida=true;
            				else
            					System.out.println("Error, reingrese la contrase�a (no puede ser vacia)");
            			}while(!valida);
                        parametros.setContrase�a(contrase�a);
                        break;
                    case 5:
                        parametros.setFinalizar(true); //cuando se quiera terminar con el programa, hay que matar todos los threads de conexion
						try {
							Thread.sleep(3000);
							System.out.println("El servidor se ha cerrado correctamente, puede cerrar el programa\n");
						} catch (InterruptedException e) {
							System.out.println("Error critico");
							e.printStackTrace();
						}
                        return;
                    default:
                        System.out.println("Opci�n no v�lida. Intente nuevamente.");
                }
			}
		}
				
	}
}