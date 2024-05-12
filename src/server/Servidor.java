package server;

import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.LinkedList;
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
		LinkedList<Esclavo> listaEsclavos=new LinkedList<Esclavo>();
		LinkedList<InfoConexion> listaConexiones=null;
		String contrasenia=null;
		
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
					System.out.println("Ingresar contrase�a de conexion: (no puede ser vacia)");
					valida=false;
					if(contrasenia==null) {
						do {
							contrasenia = scanner.nextLine();
							if(!contrasenia.isEmpty() && contrasenia!=null && !contrasenia.isBlank())
								valida=true;
							else
								System.out.println("Error, reingrese la contrase�a (no puede ser vacia)");
						}while(!valida);
					}
					ParametrosDeConexion parametros= new ParametrosDeConexion(contrasenia); //ingresar contrase�a al empezar el servidor 
					TCPServidor puertoLibre;
					try {
						puertoLibre = new TCPServidor();
						GestorConexion gestorConexion= new GestorConexion(cola,bufferSalida,historico,parametros,puertoLibre,conexiones,listaEsclavos,listaConexiones);
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
			                        	contrasenia = scanner.nextLine();
			            				if(!contrasenia.isEmpty() && contrasenia!=null && !contrasenia.isBlank())
			            					valida=true;
			            				else
			            					System.out.println("Error, reingrese la contrase�a (no puede ser vacia)");
			            			}while(!valida);
			                        parametros.setContrase�a(contrasenia);
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
			                        System.out.println("Opci�n no v�lida. Intente nuevamente.");
			                }
						}
					} catch (ExcepcionNoHayPuertos e) {
						// Al crear el puerto libre para el gestor no hay puertos libres
						System.out.println("Error critico: no hay puertos disponibles");
					}
				}
				else { //Es modo Esclavo
					String lectura=null;
					String Ip;
					String mensaje;
					String IDEsclavo;
					int puerto=0;
					int desconexiones=0;
					Boolean act,conectado;
					String[] elementos,infconexiones,infesclavos;
					int i;
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
						
						System.out.println("Ingresar contrase�a del maestro: (no puede ser vacia)");
						valida=false;
						do {
							contrasenia = scanner.nextLine();
							if(!contrasenia.isEmpty() && contrasenia!=null && !contrasenia.isBlank())
								valida=true;
							else
								System.out.println("Error, reingrese la contrase�a (no puede ser vacia)");
						}while(!valida);
						
						try {
							TCPCliente conexionConMaestro= new TCPCliente(Ip, puerto);
							conexionConMaestro.enviarMensajeAlServidor(contrasenia+";Esclavo", false);
							mensaje=conexionConMaestro.recibirmensajeDeServidor(false);
							if(mensaje!=null) {
								elementos = mensaje.split(";");	 //le llega un mensaje que es: exito;puerto;nroEsclavo
								if(elementos.length >= 3 && elementos[0].equals("Exito")) {
									IDEsclavo=elementos[2];
									try {
										conexionConMaestro.cerrarConexion();
									} catch (ExcepcionErrorAlCerrar e) {
										//si ocurre no se puede hacer nada
									}
									puerto=Integer.parseInt(elementos[1]); //nuevo puerto dado por el servidor
									conexionConMaestro=new TCPCliente(Ip, puerto);
									conectado=true;
									while(conectado){
										desconexiones=0;
										while(desconexiones<2) { //interactua con el servidor hasta que no se puede reconectar (reintenta 2 veces)
											try {
												mensaje=conexionConMaestro.recibirmensajeDeServidor(true);
												desconexiones=0;
												elementos = mensaje.split("/");	
												if(elementos.length==8)
												cola.parse(elementos[0],elementos[1]);
												bufferSalida.parse(elementos[2]);
												historico.parse(elementos[3]);
												contrasenia= elementos[4];
												
												infconexiones=elementos[5].split(";");
												String[] conexion;
												i=0;
												listaConexiones= new LinkedList<InfoConexion>();
												while(i<infconexiones.length) {
													conexion=infconexiones[i].split(",");
													listaConexiones.add(new InfoConexion(conexion[0], conexion[1], conexion[2]));
													i++;
												}
												
												infesclavos=elementos[6].split(";");
												String[]esclavo;
												i=0;
												listaEsclavos= new LinkedList<Esclavo>();
												while(i<infesclavos.length) {
													esclavo=infesclavos[i].split(",");
													listaConexiones.add(new InfoConexion(esclavo[0], esclavo[1], esclavo[2]));
													i++;
												}
											}catch (ExcepcionFinConexion e) {
												while(desconexiones<2)
													desconexiones++;
											}
										}
										try {
											conexionConMaestro.cerrarConexion();
										} catch (ExcepcionErrorAlCerrar e) {
											//si ocurre no se puede hacer nada
										}
										act=false;
										conectado=false;
										Esclavo esclavo;
										while(!listaEsclavos.isEmpty() && (!act||!conectado)) {
											esclavo=listaEsclavos.remove();
											if(esclavo.getID().equals(IDEsclavo)) { //no avanza mas porque llego a el
												act=true;
												modo="1";
											}
											else {
												desconexiones=0;
												while (desconexiones<2) {
													try {
														conexionConMaestro= new TCPCliente(esclavo.getIP(), puerto);
														conectado=true;
													}catch (ExcepcionErrorConexion e) {
														desconexiones++;
													}
												}
											}
										}
									} //si esta conectado, entonces sigue siendo esclavo, mientras que si no est� conectado es porque el es el maestro
								}
								else {
									System.out.println("Error: contrase�a incorrecta");
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
							System.out.println("Error de conexion: reintente");
						}
					}while(modo.charAt(0)=='0');
					
					
				}
			}
		}			
	}
	
}
