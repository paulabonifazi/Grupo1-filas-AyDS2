package server;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Set;
import Excepciones.*;
import TCP.TCPServidor;
import persistencia.AbstractFactoryPersistencia;

public class GestorConexion extends Thread {
		private MonitorDeCola cola;
		private MonitorNotificacion llamados;
		private Historico historico;
		private TCPServidor puertoEntrada ;
		private HashMap<String, IConexion> conexiones;
		private HashMap<String,IConexion> conexionesEsclavos;
		private  LinkedList<Esclavo> listaEsclavos;
		private LinkedList<InfoConexion> listaConexiones;
		private AbstractFactoryPersistencia factoryPersistencia;
		
		
		public GestorConexion(MonitorDeCola cola, MonitorNotificacion llamados, Historico historico,TCPServidor puertoEntrada,LinkedList<Esclavo> listaEsclavos,LinkedList<InfoConexion> listaConexiones, AbstractFactoryPersistencia factoryPersistencia) {
			super();
			this.cola = cola;
			this.llamados = llamados;
			this.historico = historico;
			this.conexiones=new HashMap<String, IConexion>();
			this.puertoEntrada=puertoEntrada;
			this.listaEsclavos=listaEsclavos;
			this.conexionesEsclavos=new HashMap<String,IConexion>();
			this.listaConexiones=listaConexiones;
			this.factoryPersistencia=factoryPersistencia;
		}
		
		 @Override
		    public void run() {
			 	try {
			 		TCPServidor puertonuevaconexion;
			 		String Respuesta;
			 		String mensaje;
			 		Thread nuevaEjecucion;
			 		IConexion nuevaConexion;
			 		String ID;
			 		String[] elementos;
			 		
			 		ParametrosDeConexion.getInstance().setPuertoLibre(puertoEntrada.getPuerto());
			 		ParametrosDeConexion.getInstance().setIP(puertoEntrada.getIPServidor());
					
					
					//GESTOR DE CONEXIONES
					//TODO CUANDO SE PASA A MAESTRO (cuando empieza en maestro la lista esta vacia y no hace nada) recorrer la lista de conexiones creando los threads (recordar de agregar la estructura de atenciones pendientes, en el gestor de box se debe permitir ingresar de 1 a la operacion ausente/fin, pero verificando que haya una atencion pendiente en la estructura (en la clase monitor de cola))
					InfoConexion info;
					LinkedList<InfoConexion> listaAuxC=new LinkedList<InfoConexion>();
					if(listaConexiones!=null && !listaConexiones.isEmpty()) {
						while (!listaConexiones.isEmpty()) {
							info=listaConexiones.removeFirst();
							switch (this.getnamefromid(info.getID())) { 
						            case "Totem"://Mensaje de Totem: "<contraseña>;Totem"
							            	puertonuevaconexion=new TCPServidor(info.getPuerto()); //se asigna un puerto
						            		nuevaEjecucion=new GestorTotem(cola, puertonuevaconexion, info.getIP(),listaEsclavos,factoryPersistencia.getReaderClient());
						            		nuevaEjecucion.start();
						            		nuevaConexion=new C_Totem(puertonuevaconexion, nuevaEjecucion);
						            		this.conexiones.put(nuevaConexion.getID(),nuevaConexion);
						                break;
						            case "Box":
							            		puertonuevaconexion=new TCPServidor(info.getPuerto()); //se asigna un puerto
							            		nuevaEjecucion=new GestorBox(cola, llamados, historico,  puertonuevaconexion,info.getIP(),info.getID(),listaEsclavos);
							            		nuevaEjecucion.start();
							            		this.conexiones.put(info.getID(), new C_Box(puertonuevaconexion, nuevaEjecucion, info.getID()));
						                break;
						            case "TvLlamado":
						                	puertonuevaconexion=new TCPServidor(info.getPuerto()); //se asigna un puerto
						                	nuevaEjecucion=new GestorNotificacion(llamados, puertonuevaconexion, info.getIP(),false,listaEsclavos);
						                	nuevaEjecucion.start();
						                	this.conexiones.put("L", new C_TvLlamado(puertonuevaconexion,nuevaEjecucion));
						            	break;
						            case "Estadisticos": //Mensaje de Estadistico: "<contraseña>;Estadistico"+
						                	puertonuevaconexion=new TCPServidor(info.getPuerto()); //se asigna un puerto
						                	nuevaEjecucion=new GestorEstadistico(cola, historico, puertonuevaconexion, info.getIP(),listaEsclavos);
						                	
						                	nuevaConexion=new C_Estadistico(puertonuevaconexion,nuevaEjecucion);
						                	nuevaEjecucion.start();
						                	this.conexiones.put(nuevaConexion.getID(),nuevaConexion);
						            	break;
							}
							listaAuxC.addLast(info);
						}
						while (!listaAuxC.isEmpty()) {
					         this.listaConexiones.add(listaAuxC.removeFirst());  // Restaurar en el orden correcto
					     }
					}
					Esclavo esclavo;
					LinkedList<Esclavo> listaAux=new LinkedList<Esclavo>();
					if(listaEsclavos!=null && !listaEsclavos.isEmpty()) {
						while(!listaEsclavos.isEmpty()) {
							esclavo=listaEsclavos.remove();
							puertonuevaconexion=new TCPServidor(esclavo.getPuerto());
							nuevaEjecucion= new GestorEsclavo(puertonuevaconexion,esclavo.getIP(),cola,llamados,historico,conexiones,listaEsclavos);
							nuevaConexion=new C_Esclavo(puertonuevaconexion,nuevaEjecucion,esclavo.getID());
							conexionesEsclavos.put(nuevaConexion.getID(),nuevaConexion);
							listaAux.addLast(esclavo);
							nuevaEjecucion.start();
						}
						// Restaurar la lista de esclavos en el orden original
					     while (!listaAux.isEmpty()) {
					         this.listaEsclavos.add(listaAux.removeFirst());  // Restaurar en el orden correcto
					     }
					}
					
					while(!Thread.interrupted()) {
						try {
							puertonuevaconexion=null;
							Respuesta=null;
							nuevaEjecucion=null;
							nuevaConexion=null;
							ID=null;
							//RECONEXION DE COMPONENTES
							// TODO en cada gestor de componente recordar enviar la info de los esclavos (al inicio de cada interaccion, es decir antes de rebir una instruccion de la componente)
							//luego en cada componente, se recibe el mensaje con la lista de los esclavos y cuando se registre una desconexion: se reintenta 2 veces con el maestro y sino se empieza a intentar con los esclavos recorriendo la lista... (si no se conecta a ningun vuelve al login)
							
							this.puertoEntrada.aceptarConexion(7000);
							mensaje=this.puertoEntrada.recibirmensajeDeCliente(0, false);
							if  (mensaje!=null){
								this.actualizaConexiones(); //elimina las conexiones viejas (cuyos hilos ya terminaron)
								this.actualizaEsclavos(); //elimina los esclavos viejos (ya desconectados)
								elementos = mensaje.split(";");	
								 if(elementos[0].equals(ParametrosDeConexion.getInstance().getContraseña())&& elementos.length >= 2){
									 switch (elementos[1]) { 
							            case "Totem"://Mensaje de Totem: "<contraseña>;Totem"
							            	puertonuevaconexion=new TCPServidor(); //se asigna un puerto
						            		nuevaEjecucion=new GestorTotem(cola, puertonuevaconexion, puertoEntrada.getIPCliente(),listaEsclavos,factoryPersistencia.getReaderClient());
						            		
						            		nuevaEjecucion.start();
						            		nuevaConexion=new C_Totem(puertonuevaconexion, nuevaEjecucion);
											this.listaConexiones.addLast(new InfoConexion(nuevaConexion.getID(), puertoEntrada.getIPCliente(), puertoEntrada.getPuerto()));
						            		this.conexiones.put(nuevaConexion.getID(),nuevaConexion);
						            		
						            		Respuesta="Exito;"+puertonuevaconexion.getPuerto();
						            		System.out.println("\u001B[32m" + "Nueva conexión: "+ nuevaConexion.getID() + "\u001B[0m");
							                break;
							            case "Box": //Mensaje de box: "<contraseña>;Box;<NroDeBox>"
							            	if(elementos.length == 3 && isInt(elementos[2])) {
								            	ID="B"+elementos[2];
								            	if(!conexiones.containsKey(ID)) {
								            		puertonuevaconexion=new TCPServidor(); //se asigna un puerto
								            		nuevaEjecucion=new GestorBox(cola, llamados, historico,  puertonuevaconexion,puertoEntrada.getIPCliente(),ID,listaEsclavos);
								            		
								            		this.conexiones.put(ID, new C_Box(puertonuevaconexion, nuevaEjecucion, ID));
								            		this.listaConexiones.addLast(new InfoConexion(ID, puertoEntrada.getIPCliente(), puertoEntrada.getPuerto()));
								            		nuevaEjecucion.start();
								            		Respuesta="Exito;"+puertonuevaconexion.getPuerto();
								            		System.out.println("\u001B[32m" + "Nueva conexión: "+ ID + "\u001B[0m");
								            	}
								            	else
								            		Respuesta="NroBoxRepetido";
							            	}
							            	else {
							            		Respuesta="IngreseNroBox";
							            	}
							                break;
							            case "TvLlamado": //Mensaje de TVLlamado: "<contraseña>;TvLlamado"
							                if(!conexiones.containsKey("L")) {
							                	puertonuevaconexion=new TCPServidor(); //se asigna un puerto
							                	nuevaEjecucion=new GestorNotificacion(llamados, puertonuevaconexion, puertoEntrada.getIPCliente(),true,listaEsclavos);
							                	
							                	this.conexiones.put("L", new C_TvLlamado(puertonuevaconexion,nuevaEjecucion));
							                	this.listaConexiones.addLast(new InfoConexion("L", puertoEntrada.getIPCliente(), puertoEntrada.getPuerto()));
							                	nuevaEjecucion.start();
							                	Respuesta="Exito;"+puertonuevaconexion.getPuerto();
							                	System.out.println("\u001B[32m" + "Nueva conexión: L"+ "\u001B[0m");
							                }
							                else
							                	Respuesta="YaExistente";
							            	break;
							            case "Estadisticos": //Mensaje de Estadistico: "<contraseña>;Estadistico"+
							                	puertonuevaconexion=new TCPServidor(); //se asigna un puerto
							                	nuevaEjecucion=new GestorEstadistico(cola, historico, puertonuevaconexion, puertoEntrada.getIPCliente(),listaEsclavos);
							                	
							                	nuevaConexion=new C_Estadistico(puertonuevaconexion,nuevaEjecucion);
							                	this.conexiones.put(nuevaConexion.getID(),nuevaConexion);
												this.listaConexiones.addLast(new InfoConexion(nuevaConexion.getID(), puertoEntrada.getIPCliente(), puertoEntrada.getPuerto()));
							                	nuevaEjecucion.start();
							                	Respuesta="Exito;"+puertonuevaconexion.getPuerto();
							                	System.out.println("\u001B[32m" + "Nueva conexión: "+ nuevaConexion.getID() + "\u001B[0m");
							            	break;	
							            case "Esclavo":
							            	//CONEXION DE ESCLAVOS
											// agregar una nueva conexion que es de esclavo (un nuevo objeto Iconexion de tipo esclavo), la cual se registra en las conexiones de esclavo y en la lista de esclavos con un puerto y con una ip. Para cada esclavo se crea un thread gestor de esclavo, el cual envia mensajes periodicamete validando la conexion y en caso de que se caiga se cierra el thread (la entrada de la lista de conexioens) y cuando el gestor de conexiones ve esto, elimina la entrada de la lista de esclavos.
											//el gestor de esclavo debe tener referencia todo (menos la lista de conexiones de esclavo) y dicha info se envia con una cierta estructura que le permite al esclavo poder pisarla al recibirla
											puertonuevaconexion=new TCPServidor();
											nuevaEjecucion= new GestorEsclavo(puertonuevaconexion,puertoEntrada.getIPCliente(),cola,llamados,historico,conexiones,listaEsclavos);
											nuevaConexion=new C_Esclavo(puertonuevaconexion,nuevaEjecucion);
											conexionesEsclavos.put(nuevaConexion.getID(),nuevaConexion);
											this.listaEsclavos.addLast(new Esclavo(nuevaConexion.getID(),nuevaConexion.getPuerto(),puertoEntrada.getIPCliente()));
											nuevaEjecucion.start();
											Respuesta="Exito;"+puertonuevaconexion.getPuerto()+";"+nuevaConexion.getID();
											System.out.println("\u001B[32m" + "Nueva conexión: "+ nuevaConexion.getID() + "\u001B[0m");
							            	break;
							            default:
							                Respuesta= "TipoDeConexionInexistente";
							                break;
									 }
				                }
								 else {
									 Respuesta="ContraseñaErronea";
								 }
									try {
										puertoEntrada.enviarMensajeACliente(Respuesta, false); //no hace falta verificar, si el cliente no recibe el mensaje, se desconecto y no sabra en que puerto conectarse (el thread que se creo se cerrara en 7 seg al ver que nadie se conecta)
									} catch (ExcepcionLecturaErronea e) {
										//no puede ocurrir xq esta el parametro en false
									}
							}
							puertoEntrada.cerrarConexion();

						} catch (ExcepcionFinTimeoutAceptar e) {
							this.actualizaConexiones();
							this.actualizaEsclavos();
						} catch (ExcepcionErrorAlAceptar | ExcepcionFinConexion | ExcepcionFinTimeoutLectura e) {
							//como se corta por un error del cliente la ejecución no se sigue con el codigo y se vuelve a empezar el ciclo
						} catch (ExcepcionErrorAlCerrar e) {
							//si no se puede cerrar se supone que no hay nada abierto y no hay nada mas que hacer que volver a esperar una conexion
						}
					}
				} catch(ExcepcionDeInterrupcion a) {
			 			cierraConexiones();
						System.out.println("Se cerró el gestor de conexiones");
			 	}
			 	catch (ExcepcionNoHayPuertos e) {
					System.out.println("Error critico: no hay puertos disponibles");
				}
			 	
		    }
		 
	 
		 private void actualizaConexiones() {
			 	IConexion conexion;
			 	Set<String> keysToRemove = new HashSet<String>();
			 	Iterator<IConexion> iterator = conexiones.values().iterator();
		        while (iterator.hasNext()) {
		            conexion = iterator.next();
		            if (!conexion.isConectado()) {
		            	keysToRemove.add(conexion.getID());
		            }
		        }
		        LinkedList<InfoConexion> listaAux = new LinkedList<InfoConexion>();
			     InfoConexion aux;
			     
			     // Procesar la lista de esclavos
			     while (!listaConexiones.isEmpty()) {
			         aux = listaConexiones.removeFirst();
			         if (keysToRemove.contains(aux.getID())) {
			             // Remover esclavo desconectado
			        	 System.out.print("\u001B[31m" + "Atencion: desconexión de "+this.getnamefromid(aux.getID()) + " con ID= "+ aux.getID()+ "\u001B[0m"+"\n");
				         conexiones.remove(aux.getID()); 
				    } else {
			             // Mantener esclavo conectado
			             listaAux.addLast(aux);  // Mantener el orden original
			         }
			     }
			     
			     // Restaurar la lista de esclavos en el orden original
			     while (!listaAux.isEmpty()) {
			         this.listaConexiones.add(listaAux.removeFirst());  // Restaurar en el orden correcto
			     }
		 }
		 
		// TODO también actualizar las conexiones de los esclavos
		 private void actualizaEsclavos() {
		     IConexion esclavo;
		     Set<String> keysToRemove = new HashSet<String>();
		     Iterator<IConexion> iterator = conexionesEsclavos.values().iterator();
		     
		     // Identificar conexiones inactivas
		     while (iterator.hasNext()) {
		         esclavo = iterator.next();
		         if (!esclavo.isConectado()) {
		             keysToRemove.add(esclavo.getID());
		         }
		     }
		     
		     LinkedList<Esclavo> listaAux = new LinkedList<Esclavo>();
		     Esclavo aux;
		     
		     // Procesar la lista de esclavos
		     while (!listaEsclavos.isEmpty()) {
		         aux = listaEsclavos.removeFirst();
		         if (keysToRemove.contains(aux.getID())) {
		             // Remover esclavo desconectado
		             conexionesEsclavos.remove(aux.getID());
		             System.out.print("\u001B[31m" + "Atencion: desconexión del esclavo con ID= " + aux.getID() + "\u001B[0m" + "\n");
		         } else {
		             // Mantener esclavo conectado
		             listaAux.addLast(aux);  // Mantener el orden original
		         }
		     }
		     
		     // Restaurar la lista de esclavos en el orden original
		     while (!listaAux.isEmpty()) {
		         this.listaEsclavos.add(listaAux.removeFirst());  // Restaurar en el orden correcto
		     }
		 }

		 
		 private boolean isInt(String cadena) {
			 try {
     	        Integer.parseInt(cadena);
     	        return true;
     	    } catch (NumberFormatException e) {
     	        return false;
     	    }
		 }
		 
		 //TODO cortar las conexiones de los esclavos
		 private void cierraConexiones() {
			 IConexion conexion;
			 Iterator<IConexion> iterator = conexiones.values().iterator();
		        while (iterator.hasNext()) {
		            conexion = iterator.next();
		            if (conexion.isConectado()) {
		            	try {
							conexion.cerrarConexion();
							System.out.println("Se cerró: "+ conexion.getID());
						} catch (ExcepcionErrorAlCerrar e) {
		            		System.out.println("Error critico: no fue posible cerrar la conexion de: "+conexion.getID());
							e.printStackTrace();
						}
		            }
		        }
			 Iterator<IConexion> it = conexionesEsclavos.values().iterator();
		        while (it.hasNext()) {
		            conexion = it.next();
		            if (conexion.isConectado()) {
		            	try {
							conexion.cerrarConexion();
							System.out.println("Se cerró: "+ conexion.getID());
						} catch (ExcepcionErrorAlCerrar e) {
		            		System.out.println("Error critico: no fue posible cerrar la conexion de: "+conexion.getID());
							e.printStackTrace();
						}
		            }
		        }
			 
		     
		 }
		     
		 private String getnamefromid(String Id) {
			 String name=null;
			 switch(Id.charAt(0)) {
				 case 'E': //estadistico
					 	name="Estadisticos";
				 	break;
				 case 'T': //totem
					 	name="Totem";
					 	break;
				 case 'L': //TVLlamado
					 	name= "TvLlamado";
					 	break;
				 case 'B': //Box
					 	name="Box";
					 	break;
			 }
			 return name;
		 }
		 
}
