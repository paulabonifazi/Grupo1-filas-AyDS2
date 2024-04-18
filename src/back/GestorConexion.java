package back;

import java.util.HashMap;

import javax.swing.Icon;

public class GestorConexion extends Thread {
		private MonitorDeCola cola;
		private MonitorNotificacion bufferSalida;
		private Atenciones historico;
		private TCPServidor puertoEntrada;
		private ParametrosDeConexion parametros;
		private HashMap<String, IConexion> conexiones;
		
		public GestorConexion(MonitorDeCola cola, MonitorNotificacion bufferSalida, Atenciones historico,ParametrosDeConexion parametros,String contraseña) {
			super();
			this.cola = cola;
			this.bufferSalida = bufferSalida;
			this.historico = historico;
			this.parametros=parametros;
			this.conexiones=new HashMap<String, IConexion>();
		}
		
		 @Override
		    public void run() {
			 	try {
					this.puertoEntrada=new TCPServidor();
					this.parametros.setPuertoLibre(puertoEntrada.getPuerto());
					while(!parametros.isFinalizar()) {
						try {
							this.puertoEntrada.aceptarConexion();
							String mensaje=this.puertoEntrada.recibirmensajeDeCliente(0, false);
							if  (mensaje!=null){
								String[] elementos = mensaje.split(";"); // Contraseña	
								 if(elementos[0].equals(parametros.getContraseña()) && (elementos[1].equals("Totem")||elementos[1].equals("Box")||elementos[1].equals("TvLlamado"))){
									 //busca puerto libre
									 switch (elementos[1]) {
							            case "Totem":
							            	//se busca puerto libre (crear conexion y pasarlo por parametro)
							                //implementar crear thread(lanzar thread (con ref a conexion e IPcliente) e ingresarlo en conexiones)
							                break;
							            case "Box":
							                //implementar crear thread (verificar nro box no repetido, si cumple se busca puesto libre,mensaje de exito,lanzar thread (con ref a conexion e IPcliente) e ingresarlo en conexiones)
							                break;
							            case "TvLlamado":
							                //implementar crear thread (verificar que sea unico,si cumple se busca puesto libre,mensaje de exito,lanzar thread (con ref a conexion e IPcliente), e ingresarlo en conexiones) e ingresarlo en conexiones
							                break;
							            default:
							                System.out.println("Opción no válida");
							                break;
									 }
									//implementar ejecutar el thread,
									//Respuesta a cliente= "exito... nro de puerto" (debe estar más arriba cuando se sigue evaluando)
				                }
								 else {
									 //Respuesta a cliente="error"
								 }
								 //enviar respuesta a cliente
							}
							else {
								//Respuesta a cliente= error
							}
							puertoEntrada.cerrarConexion();
						} catch (ExcepcionErrorAlAceptar | ExcepcionFinConexion | ExcepcionFinTimeoutLectura e) {
							//como se corta por un error del cliente la ejecución no se sigue con el codigo y se vuelve a empezar el ciclo
						} catch (ExcecionErrorAlCerrar e) {
							//si no se puede cerrar se supone que no hay nada abierto
						}
					}
					//IMPLEMENTAR LA LOGICA PARA CERRAR LOS THREADS
				} catch (ExcepcionNoHayPuertos e) {
					e.printStackTrace(); //ERROR DE NO HAY PUERTOS ES IRRECUPERABLE
				}
		    }

}
