package server;

import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Scanner;
import Excepciones.*;
import TCP.TCPCliente;
import TCP.TCPServidor;
public class Servidor{

	public static void main(String[] args) {
		MonitorDeCola cola = new MonitorDeCola();
		MonitorNotificacion bufferSalida=new MonitorNotificacion();
		Historico historico= new Historico();
		HashMap<String, IConexion> conexiones= new HashMap<String, IConexion>();
		//TODO lista de esclavos (no necesariamete sincronizada xq solo la modifica el gestor de esclavos,se pasa por parametro) Y utilizar los parametros de conexion para establecer el puerto de los esclavos (y recordar agregar la funcionalidad del main para mostrar dicho puerto)
		
		
		try(Scanner scanner = new Scanner(System.in)){
			System.out.println("Ingresar modo de servidor [1:Maestro/0:esclavo]");
			String modo;
			boolean valida=false;
			do {
				modo = scanner.nextLine();
				if (modo != null && !modo.isEmpty() && !modo.isBlank() && modo.length() == 1 && (modo.charAt(0) == '0' || modo.charAt(0) == '1')) {
					valida=true;
				}	
				else
					System.out.println("Error, reingrese la modo [1:Maestro/0:esclavo]");
			}while(!valida);
			while(modo!=null) {
				if(modo.charAt(0)=='1') { //es MAESTRO
					System.out.println("Ingresar contraseña de conexion: (no puede ser vacia)");
					valida=false;
					String contraseña=null;
					do {
						contraseña = scanner.nextLine();
						if(!contraseña.isEmpty() && contraseña!=null && !contraseña.isBlank())
							valida=true;
						else
							System.out.println("Error, reingrese la contraseña (no puede ser vacia)");
					}while(!valida);
					//TODO si ya se tenia una contraseña, xq antes era el esclavo entonces utilizar esa (si contraseña!=null). Por lo tanto contraseña tendria que estar arriba con los otros parametros
					ParametrosDeConexion parametros= new ParametrosDeConexion(contraseña); //ingresar contraseña al empezar el servidor 
					TCPServidor puertoLibre;
					try {
						puertoLibre = new TCPServidor();
						GestorConexion gestorConexion= new GestorConexion(cola,bufferSalida,historico,parametros,puertoLibre,conexiones);
						gestorConexion.start();
						while (!parametros.isFinalizar()) {
							System.out.println("Seleccione una opcion:\n 1)Mostrar puerto de entrada.\n 2)Mostar IP del servidor\n 3)Mostrar contraseña de conexión.\n 4)Cambiar contraseña de conexión.\n 5)Cerrar servidor");
							int opcion=0;
							valida=false;
							do {
								try {
							        opcion = scanner.nextInt(); // Intentar leer un entero
							        valida=true;
							    } catch (InputMismatchException e) {
							        System.out.println("Error: debe ingresar un número entero.");
							    }
								finally {
									scanner.nextLine(); // Limpia el buffer, tanto si lee el caracter despues del int o el string incorrecto
								}
							}while(!valida);
							
			                switch (opcion) {
			              //TODO Agregar una funcion para mostrar el puerto de conexion de esclavos (ya sea esta misma junto con el puerto de entrada o sin o otra opcion)
			                    case 1:
			                        System.out.println("El número de puerto de entrada es: "+ parametros.getPuertoLibre()+"\n");
			                        break;
			                    case 2:
			                        System.out.println("La IP del servidor es: "+ parametros.getIP()+"\n");
			                        break;
			                    case 3:
			                    	System.out.println("La contraseña es: "+ parametros.getContraseña()+"\n");
			                        break;
			                    case 4:
			                        System.out.println("Ingrese la nueva contraseña:(no puede ser vacia)");
			                        valida=false;
			                        do {
			            				contraseña = scanner.nextLine();
			            				if(!contraseña.isEmpty() && contraseña!=null && !contraseña.isBlank())
			            					valida=true;
			            				else
			            					System.out.println("Error, reingrese la contraseña (no puede ser vacia)");
			            			}while(!valida);
			                        parametros.setContraseña(contraseña);
			                        break;
			                    case 5:
			                        parametros.setFinalizar(true); //para finalizar su propio ciclo y permite verificar a los threads interrumpidos validar su finalizacion
									try {
										puertoLibre.cerrarPuertoServidor();//esto genera una excepcion de interrupcion en el thread del gestor
										System.out.println("Espere a que es cierre el gestor de conexiones para cerrar el programa\n");	
									} 
									catch (ExcepcionErrorAlCerrar e) {
										System.out.println("Error critico: no se ha cerrado correctamente el sistema");
									} 
									//Consultar si no quiere ser esclavo de alg (modo=0 o si es un no modo=null y muere)
									try {
										Thread.sleep(10000);
									} catch (InterruptedException e) {
										//no puede interrumpirse
									}
									System.out.println("Ingrese 0 para ser esclavo o 1 si desea terminar");
									do {
										modo = scanner.nextLine();
										if (modo != null && !modo.isEmpty() && !modo.isBlank() && modo.length() == 1 && (modo.charAt(0) == '0' || modo.charAt(0) == '1')) {
											valida=true;
											if(modo.charAt(0) == '1')
												modo=null;
										}	
										else
											System.out.println("Error: ingrese 0 para ser esclavo o 1 si desea terminar");
									}while(!valida);
			                        break;
			                    default:
			                        System.out.println("Opción no válida. Intente nuevamente.");
			                }
						}
					} catch (ExcepcionNoHayPuertos e) {
						// Al crear el puerto libre para el gestor no hay puertos libres
						System.out.println("Error critico: no hay puertos disponibles");
					}
				}
				else { //Es modo Esclavo
					boolean conectado=false;
					String lectura=null;
					String Ip,contrasenia;
					String mensaje;
					String[] elementos;
					int nroEsclavo;
					int puerto=0;
					

					//TODO ingresar contraseña ip y el puerto (de los esclavos del maestro)
					do {
						System.out.println("Ingrese el puerto del maestro");
						valida=false;
						do {
							lectura = scanner.nextLine();
							if (lectura != null && !lectura.isEmpty() && !lectura.isBlank()) {
								try {
									puerto=Integer.parseInt(lectura);
									if(puerto>0) {
										valida=true;
									}
								}
								catch(NumberFormatException a){
									System.out.println("Error: ingrese un NUMERO de puerto");
								}	
							}	
							else
								System.out.println("Error: no puede ser un puerto vacio");
						}while(!valida);
		
						System.out.println("Ingrese  la IP del maestro (no puede ser vacia)");
						valida=false;
						do {
							Ip = scanner.nextLine();
							if (Ip != null && !Ip.isEmpty() && !Ip.isBlank() ) {
								valida=true;
								if(modo.charAt(0) == '1')
									modo=null;
							}	
							else
								System.out.println("Error: reingrese la IP del maestro (no puede ser vacia)");
						}while(!valida);
						
						System.out.println("Ingresar contraseña del maestro: (no puede ser vacia)");
						valida=false;
						do {
							contrasenia = scanner.nextLine();
							if(!contrasenia.isEmpty() && contrasenia!=null && !contrasenia.isBlank())
								valida=true;
							else
								System.out.println("Error, reingrese la contraseña (no puede ser vacia)");
						}while(!valida);
						
						try {
							TCPCliente conexionConMaestro= new TCPCliente(Ip, puerto);
							conexionConMaestro.enviarMensajeAlServidor(contrasenia+";Esclavo", null);
							mensaje=conexionConMaestro.recibirmensajeDeServidor(null);
							if(mensaje!=null) {
								elementos = mensaje.split(";");	 //le llega un mensaje que es: exito;puerto;nroEsclavo
								if(elementos.length >= 3 && elementos[0].equals("Exito")) {
									nroEsclavo=Integer.parseInt(elementos[2]);
									try {
										conexionConMaestro.cerrarConexion();
									} catch (ExcepcionErrorAlCerrar e) {
										//si ocurre no se puede hacer nada
									}
									conexionConMaestro=new TCPCliente(Ip, Integer.parseInt(elementos[1]));
									//TODO Se pone a escuchar los mensajes del maestro y pisa sus estructuras con ellos.
								}
								else {
									System.out.println("Error: contraseña incorrecta");
								}
							}
						}
						catch(NumberFormatException e){
							System.out.println("Error: Mensaje del servidor equivocado, reintente conexion");
						}
						catch (ExcepcionErrorConexion e) {
							System.out.println("Error de conexion: Ip o puerto erroneos");
						}
						catch (ExcepcionLecturaErronea | ExcepcionFinConexion e) {
							//TODO caso en el que se cae el servidor... recorrer los esclavos o volverse maestro
							// en caso de no recibir el mensaje intenta reconectarse 2 veces al maestro y sino empieza con el recorrido de los esclavos hasta llegar a el (se hace maestro) o conectarse a alguno (se hace esclavo y espera sus mensajes de vuelta).
						}
					}while(conectado);
					
					
				}
			}
		}			
	}
	
}
