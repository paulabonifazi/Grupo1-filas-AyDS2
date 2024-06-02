package server;


import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.InputMismatchException;
import java.util.LinkedList;
import java.util.Scanner;
import java.util.concurrent.PriorityBlockingQueue;

import Excepciones.*;
import TCP.TCPCliente;
import TCP.TCPServidor;
public class Servidor{

	public static void main(String[] args) {
		MonitorDeCola cola = new MonitorDeCola();
		MonitorNotificacion bufferSalida=new MonitorNotificacion();
		Historico historico= new Historico();
		LinkedList<Esclavo> listaEsclavos=new LinkedList<Esclavo>();
		LinkedList<InfoConexion> listaConexiones=new LinkedList<InfoConexion>();
		String contrasenia=null;
		Boolean isFinalizar;
		FactoryStrategyQueue factoryestrategia= new FactoryStrategyQueue(ParametrosDeConexion.getInstance());
		try(Scanner scanner = new Scanner(System.in)){
			String modo;
			boolean valida=false;
			System.out.println("Ingresar modo de servidor [1:Maestro/0:esclavo]");
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
					valida=false;
					AbstractFactoryPersistencia factoryPersistencia= ConfiguradorDeServer.definePersistencia(DetectorDeArchivo.detectarArchivo());
					if(contrasenia==null) {
						System.out.println("Ingresar contraseña de conexion: (no puede ser vacia)");
						do {
							contrasenia = scanner.nextLine();
							if(!contrasenia.isEmpty() && contrasenia!=null && !contrasenia.isBlank())
								valida=true;
							else
								System.out.println("Error, reingrese la contraseña (no puede ser vacia)");
						}while(!valida);
						ParametrosDeConexion.getInstance().setContraseña(contrasenia); //ingresar contraseña al empezar el servidor
						ParametrosDeConexion.getInstance().defineEstrategia(factoryPersistencia.getReaderConfig().getConfig());
					}
					MonitorPersistencia bufferPersistencia= new MonitorPersistencia();
					
					cola.setbufferPersistencia(bufferPersistencia);
					cola.setColadeTurnos(new PriorityBlockingQueue<>(20,factoryestrategia.getStrategy()));
					GestorPersistencia gestorPersistencia= new GestorPersistencia(bufferPersistencia,factoryPersistencia.getWritterLog());
					gestorPersistencia.start();
					TCPServidor puertoLibre;
					try {
						puertoLibre = new TCPServidor();
						GestorConexion gestorConexion= new GestorConexion(cola,bufferSalida,historico,puertoLibre,listaEsclavos,listaConexiones,factoryPersistencia);
						gestorConexion.start();
						isFinalizar=false;
						while (!isFinalizar) {
							System.out.println("Seleccione una opcion:\n 1)Mostrar puerto de entrada.\n 2)Mostar IP del servidor\n 3)Mostrar contraseña de conexión.\n 4)Cambiar contraseña de conexión.\n 5)Mostrar conexiones\n 6)Cerrar servidor");
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
			                    case 1:
			                        System.out.println("El número de puerto de entrada es: "+ ParametrosDeConexion.getInstance().getPuertoLibre()+"\n");
			                        break;
			                    case 2:
			                        System.out.println("La IP del servidor es: "+ ParametrosDeConexion.getInstance().getIP()+"\n");
			                        break;
			                    case 3:
			                    	System.out.println("La contraseña es: "+ ParametrosDeConexion.getInstance().getContraseña()+"\n");
			                        break;
			                    case 4:
			                        System.out.println("Ingrese la nueva contraseña:(no puede ser vacia)");
			                        valida=false;
			                        do {
			                        	contrasenia = scanner.nextLine();
			            				if(!contrasenia.isEmpty() && contrasenia!=null && !contrasenia.isBlank())
			            					valida=true;
			            				else
			            					System.out.println("Error, reingrese la contraseña (no puede ser vacia)");
			            			}while(!valida);
			                        ParametrosDeConexion.getInstance().setContraseña(contrasenia);
			                        break;
			                    case 5:
			                    	mostrarListas(listaEsclavos,listaConexiones);
			                    	break;
			                    case 6:
			                       isFinalizar=true; //para finalizar su propio ciclo y permite verificar a los threads interrumpidos validar su finalizacion
	
			                       try {
										
										System.out.println("Espere a que es cierre el gestor de conexiones para cerrar el programa\n");	
										gestorConexion.interrupt();
										gestorPersistencia.interrupt();
										puertoLibre.cerrarPuertoServidor();//esto genera una excepcion de interrupcion en el thread del gestor
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
									valida=false;
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
												if(elementos.length==7) {
													cola.parse(elementos[0],elementos[1]);
													bufferSalida.parse(elementos[2]);
													historico.parse(elementos[3]);
													ParametrosDeConexion.getInstance().parse(elementos[4]);
													
													infconexiones=elementos[5].split(";");
													String[] conexion;
													i=0;
													listaConexiones= new LinkedList<InfoConexion>();
													while(i<infconexiones.length) {
														if(!infconexiones[i].isBlank()&&!infconexiones[i].isEmpty()) {
															conexion=infconexiones[i].split(",");
															listaConexiones.add(new InfoConexion(conexion[0], conexion[1], conexion[2]));
														}
														i++;
													}
													
													infesclavos=elementos[6].split(";");
													String[]esclavo;
													i=0;
													listaEsclavos= new LinkedList<Esclavo>();
													while(i<infesclavos.length) {
														if(!infesclavos[i].isBlank()&&!infesclavos[i].isEmpty()) {
															esclavo=infesclavos[i].split(",");
															listaEsclavos.add(new Esclavo(esclavo[0], esclavo[1], esclavo[2]));
														}
														i++;
													}
												}
												else
													System.out.println("ERROR");
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
										while(!listaEsclavos.isEmpty() && !act && !conectado) {
											esclavo=listaEsclavos.remove();
											if(esclavo.getID().equals(IDEsclavo)) { //no avanza mas porque llego a el
												act=true;
												modo="1";
											}
											else {
												desconexiones=0;
												while (desconexiones<2 && !conectado) {
													try {
														conexionConMaestro= new TCPCliente(esclavo.getIP(), puerto);
														conectado=true;
													}catch (ExcepcionErrorConexion e) {
														desconexiones++;
													}
												}
											}
										}
										
									} //si esta conectado, entonces sigue siendo esclavo, mientras que si no está conectado es porque el es el maestro
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
							System.out.println("Error de conexion: reintente");
						}
					}while(modo.charAt(0)=='0');
					
					
				}
			}
		} catch (FileNotFoundException e1) {
			System.out.println("Error: "+ e1.getMessage()+ " no se encuentra");
		}
		catch (IOException e1) {
			System.out.println("Error:"+ e1.getMessage());
		} catch (EstrategiaInexistente e) {
			System.out.println("La estrategia del archivo de configuracion es invalida");
		}
		
	}
	
	
	public static void mostrarListas(LinkedList<Esclavo> listaEsclavos,LinkedList<InfoConexion> listaConexiones){
		InfoConexion info;
		int i=0;
		if((listaConexiones!=null && !listaConexiones.isEmpty()) || listaEsclavos!=null && !listaEsclavos.isEmpty()) {
			LinkedList<InfoConexion> listaAuxC=new LinkedList<InfoConexion>();
			if(listaConexiones!=null && !listaConexiones.isEmpty()) {
				while (!listaConexiones.isEmpty()) {
					info=listaConexiones.removeFirst();
					i++;
					System.out.println(i+") ID: "+info.getID()+ ", IP: "+ info.getIP());
					listaAuxC.addLast(info);
				}
				while (!listaAuxC.isEmpty()) {
			         listaConexiones.add(listaAuxC.removeFirst());  // Restaurar en el orden correcto
			     }
			}
			Esclavo esclavo;
			LinkedList<Esclavo> listaAux=new LinkedList<Esclavo>();
			if(listaEsclavos!=null && !listaEsclavos.isEmpty()) {
				while(!listaEsclavos.isEmpty()) {
					esclavo=listaEsclavos.remove();
					i++;
					System.out.println(i+") ID: "+esclavo.getID()+ ", IP: "+ esclavo.getIP());
					listaAux.addLast(esclavo);
				}
				// Restaurar la lista de esclavos en el orden original
			     while (!listaAux.isEmpty()) {
			         listaEsclavos.add(listaAux.removeFirst());  // Restaurar en el orden correcto
			     }
			}
		}
		else
			System.out.println("No hay conexiones activas");
	}
}
