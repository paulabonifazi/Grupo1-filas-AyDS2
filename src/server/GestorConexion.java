package server;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import Excepciones.*;
import TCP.TCPServidor;

public class GestorConexion extends Thread {
		private MonitorDeCola cola;
		private MonitorNotificacion llamados;
		private Historico historico;
		private TCPServidor puertoEntrada ;
		private ParametrosDeConexion parametros;
		private HashMap<String, IConexion> conexiones;
		
		public GestorConexion(MonitorDeCola cola, MonitorNotificacion llamados, Historico historico,ParametrosDeConexion parametros,TCPServidor puertoEntrada,HashMap<String, IConexion> conexiones) {
			super();
			this.cola = cola;
			this.llamados = llamados;
			this.historico = historico;
			this.parametros=parametros;
			this.conexiones=conexiones;
			this.puertoEntrada=puertoEntrada;
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
			 		
					this.parametros.setPuertoLibre(puertoEntrada.getPuerto());
					this.parametros.setIP(puertoEntrada.getIPServidor());
					
					while(true) {
						try {
							puertonuevaconexion=null;
							Respuesta=null;
							nuevaEjecucion=null;
							nuevaConexion=null;
							ID=null;
							
							

							//GESTOR DE CONEXIONES
							//TODO CUANDO SE PASA A MAESTRO (cuando empieza en maestro la lista esta vacia y no hace nada) recorrer la lista de conexiones creando los threads (recordar de agregar la estructura de atenciones pendientes, en el gestor de box se debe permitir ingresar de 1 a la operacion ausente/fin, pero verificando que haya una atencion pendiente en la estructura (en la clase monitor de cola))
							//TODO gestor de conexiones tiene: conexiones a componentes/ conexiones a esclavos/ lista de esclavos (habria que recorrer, ademas de la lista de conexiones, la de conexiones de esclavos (no la lista de esclavos), buscando los desconectados para eliminarlos de la entrada de sus 2 listas)
							
							
							//CONEXION DE ESCLAVOS
							//TODO agregar una nueva conexion que es de esclavo (un nuevo objeto Iconexion de tipo esclavo), la cual se registra en las conexiones de esclavo y en la lista de esclavos con un puerto y con una ip. Para cada esclavo se crea un thread gestor de esclavo, el cual envia mensajes periodicamete validando la conexion y en caso de que se caiga se cierra el thread (la entrada de la lista de conexioens) y cuando el gestor de conexiones ve esto, elimina la entrada de la lista de esclavos.
							//el gestor de esclavo debe tener referencia todo (menos la lista de conexiones de esclavo) y dicha info se envia con una cierta estructura que le permite al esclavo poder pisarla al recibirla
							
							//RECONEXION DE COMPONENTES
							// TODO en cada gestor de componente recordar enviar la info de los esclavos (al inicio de cada interaccion, es decir antes de rebir una instruccion de la componente)
							//luego en cada componente, se recibe el mensaje con la lista de los esclavos y cuando se registre una desconexion: se reintenta 2 veces con el maestro y sino se empieza a intentar con los esclavos recorriendo la lista... (si no se conecta a ningun vuelve al login)
							
							
							
							
							this.puertoEntrada.aceptarConexion(7000);
							mensaje=this.puertoEntrada.recibirmensajeDeCliente(0, false);
							if  (mensaje!=null){
								this.actualizaConexiones(); //elimina las conexiones viejas (cuyos hilos ya terminaron)
								elementos = mensaje.split(";");	
								 if(elementos[0].equals(parametros.getContraseña())&& elementos.length >= 2){
									 switch (elementos[1]) { 
							            case "Totem"://Mensaje de Totem: "<contraseña>;Totem"
							            	puertonuevaconexion=new TCPServidor(); //se asigna un puerto
						            		nuevaEjecucion=new GestorTotem(cola, puertonuevaconexion, puertoEntrada.getIPCliente());
						            		
						            		nuevaEjecucion.start();
						            		nuevaConexion=new C_Totem(puertonuevaconexion, nuevaEjecucion);
						            		this.conexiones.put(nuevaConexion.getID(),nuevaConexion);
						            		
						            		Respuesta="Exito;"+puertonuevaconexion.getPuerto();
						            		
							                break;
							            case "Box": //Mensaje de box: "<contraseña>;Box;<NroDeBox>"
							            	if(elementos.length == 3 && isInt(elementos[2])) {
								            	ID="B"+elementos[2];
								            	if(!conexiones.containsKey(ID)) {
								            		puertonuevaconexion=new TCPServidor(); //se asigna un puerto
								            		nuevaEjecucion=new GestorBox(cola, llamados, historico,  puertonuevaconexion,puertoEntrada.getIPCliente(),ID);
								            		
								            		this.conexiones.put(ID, new C_Box(puertonuevaconexion, nuevaEjecucion, ID));
								            		
								            		nuevaEjecucion.start();
								            		Respuesta="Exito;"+puertonuevaconexion.getPuerto();
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
							                	nuevaEjecucion=new GestorNotificacion(llamados, puertonuevaconexion, puertoEntrada.getIPCliente());	
							                	
							                	this.conexiones.put("L", new C_TvLlamado(puertonuevaconexion,nuevaEjecucion));
							                	
							                	nuevaEjecucion.start();
							                	Respuesta="Exito;"+puertonuevaconexion.getPuerto();
							                }
							                else
							                	Respuesta="YaExistente";
							            	break;
							            case "Estadisticos": //Mensaje de Estadistico: "<contraseña>;Estadistico"+
							                	puertonuevaconexion=new TCPServidor(); //se asigna un puerto
							                	nuevaEjecucion=new GestorEstadistico(cola, historico, puertonuevaconexion, puertoEntrada.getIPCliente());
							                	
							                	nuevaConexion=new C_Estadistico(puertonuevaconexion,nuevaEjecucion);
							                	this.conexiones.put(nuevaConexion.getID(),nuevaConexion);
							                	
							                	nuevaEjecucion.start();
							                	Respuesta="Exito;"+puertonuevaconexion.getPuerto();
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
						} catch (ExcepcionErrorAlAceptar | ExcepcionFinConexion | ExcepcionFinTimeoutLectura e) {
							//como se corta por un error del cliente la ejecución no se sigue con el codigo y se vuelve a empezar el ciclo
						} catch (ExcepcionErrorAlCerrar e) {
							//si no se puede cerrar se supone que no hay nada abierto y no hay nada mas que hacer que volver a esperar una conexion
						}
					}
				} catch(ExcepcionDeInterrupcion a) {
					if(parametros.isFinalizar()) {
			 			cierraConexiones();
						System.out.println("Se cerró el gestor de conexiones");
					}
					else
						System.out.println("Error critico: se interrumpió el gestor de conexiones");
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
		        for (String key : keysToRemove) {
		            System.out.print("\u001B[31m" + "Atencion: desconexión de "+this.getnamefromid(conexiones.get(key).getID()) + " con ID= "+ conexiones.get(key).getID()+ "\u001B[0m"+"\n");
		            conexiones.remove(key);
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

		 private void cierraConexiones() {
			 Iterator<IConexion> iterator = conexiones.values().iterator();
		        while (iterator.hasNext()) {
		            IConexion conexion = iterator.next();
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
				 	name="Estadistico";
			 	break;
			 case 'T': //totem
				 	name="Totem";
				 	break;
			 case 'L': //TVLlamado
				 	name= "TVLlamado";
				 	break;
			 case 'B': //Box
				 	name="Box";
				 	break;
			 }
			 return name;
		 }
		 
}
