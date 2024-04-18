package back;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import javax.swing.Icon;

public class GestorConexion extends Thread {
		private MonitorDeCola cola;
		private MonitorNotificacion bufferSalida;
		private Atenciones historico;
		private TCPServidor puertoEntrada ;
		private ParametrosDeConexion parametros;
		private HashMap<String, IConexion> conexiones;
		
		public GestorConexion(MonitorDeCola cola, MonitorNotificacion bufferSalida, Atenciones historico,ParametrosDeConexion parametros,TCPServidor puertoEntrada) {
			super();
			this.cola = cola;
			this.bufferSalida = bufferSalida;
			this.historico = historico;
			this.parametros=parametros;
			this.conexiones=new HashMap<String, IConexion>();
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
							try {
								this.puertoEntrada.aceptarConexion(0);
							} catch (ExcepcionFinTimeoutAceptar e) {
								//no deberia ocurrir, porque no hay timeout
							}
							mensaje=this.puertoEntrada.recibirmensajeDeCliente(0, false);
							if  (mensaje!=null){
								this.actualizaConexiones(); //elimina las conexiones viejas (cuyos hilos ya terminaron)
								elementos = mensaje.split(";");	
								 if(elementos[0].equals(parametros.getContraseña())){
									 switch (elementos[1]) { 
							            case "Totem"://Mensaje de Totem: "<contraseña>;Totem"
							            	puertonuevaconexion=new TCPServidor(); //se asigna un puerto
						            		nuevaEjecucion=new GestorTotem(cola, puertonuevaconexion, ID);
						            		
						            		nuevaConexion=new Totem(puertonuevaconexion, nuevaEjecucion);
						            		this.conexiones.put(nuevaConexion.getID(),nuevaConexion);
						            		
						            		nuevaEjecucion.start();
						            		Respuesta="Exito";
						            		
							                break;
							            case "Box": //Mensaje de box: "<contraseña>;Box;<NroDeBox>"
							            	ID="B"+elementos[2];
							            	if(isInt(elementos[2])&&!conexiones.containsKey(ID)) {
							            		puertonuevaconexion=new TCPServidor(); //se asigna un puerto
							            		nuevaEjecucion=new Thread(); //Falta poner el tipo de thread!!!
							            		
							            		this.conexiones.put(ID, new Box(puertonuevaconexion, nuevaEjecucion, ID));
							            		
							            		nuevaEjecucion.start();
							            		Respuesta="Exito";
							            	}
							            	else
							            		Respuesta="NroBoxRepetido";
							                break;
							            case "TvLlamado": //Mensaje de TVLlamado: "<contraseña>;TvLlamado"
							                if(!conexiones.containsKey("L")) {
							                	puertonuevaconexion=new TCPServidor(); //se asigna un puerto
							                	nuevaEjecucion=new Thread(); //Falta poner el tipo de thread!!!
							                	
							                	this.conexiones.put("L", new TvLlamado(puertonuevaconexion,nuevaEjecucion));
							                	
							                	nuevaEjecucion.start();
							                	Respuesta="Exito";
							                }
							                else
							                	Respuesta="YaExistente";
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
						} catch (ExcepcionErrorAlAceptar | ExcepcionFinConexion | ExcepcionFinTimeoutLectura e) {
							//como se corta por un error del cliente la ejecución no se sigue con el codigo y se vuelve a empezar el ciclo
						} catch (ExcecionErrorAlCerrar e) {
							//si no se puede cerrar se supone que no hay nada abierto y no hay nada mas que hacer que volver a esperar una conexion
						}
					}
				} catch(ExcepcionDeInterrupcion a) {
					if(parametros.isFinalizar())
			 			cierraConexiones();
					else
						System.out.println("Error critico: se interrumpió el gestor de conexiones");
			 	}
			 	catch (ExcepcionNoHayPuertos e) {
					System.out.println("Error critico: no hay puertos disponibles");
				}
			 	
		    }
		 
		 
		 private void actualizaConexiones() {
			 	Set<String> keysToRemove = new HashSet<String>();
			 	Iterator<IConexion> iterator = conexiones.values().iterator();
		        while (iterator.hasNext()) {
		            IConexion conexion = iterator.next();
		            if (!conexion.isConectado()) {
		            	keysToRemove.add(conexion.getID());
		            }
		        }
		        for (String key : keysToRemove) {
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
		            if (!conexion.isConectado()) {
		            	try {
							conexion.cerrarConexion();
						} catch (ExcecionErrorAlCerrar e) {
		            		System.out.println("Error critico: no fue posible cerrar las conexiones");
							e.printStackTrace();
						}
		            }
		        }
		 }
		        
}
