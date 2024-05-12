package server;
import Excepciones.*;
import TCP.*;
import interfaces.INotificacion;

public class GestorNotificacion extends Thread implements INotificacion{
	private TCPServidor serverNotificacion;
	private MonitorNotificacion llamados;
	private String ipClienteEsperado;
	
	public GestorNotificacion(MonitorNotificacion llamados,TCPServidor serverNotificacion,String ipClienteEsperado) {
		super();
		this.serverNotificacion = serverNotificacion;
		this.llamados = llamados;
		this.ipClienteEsperado = ipClienteEsperado;
	}


	@Override
    public void run() {
		Llamado llamado;
		int desconexiones=0;
	 	try {
	 		this.serverNotificacion.aceptarConexion(7000); //espera por 7 segundos
	 		if(serverNotificacion.validarIPCliente(ipClienteEsperado)) {
	 			this.llamados.setActivado(true); //activa el sistema de llamado
	 			while(desconexiones<2) { //No recibe datos, solo envia.
		 			try {
		 				llamado=llamados.take(); //espera por un elemento en el buffer de salida, en caso de ser interrumpida es porque es fin del servidor
			 			mostrar(llamado.getDNI(),llamado.getBox()); //!!!) EL SISTEMA DE LLAMADO DEBE INDICAR "Recibido" por cada mensaje que recibe, con ello el server sabe si todavia se mantiene la conexion
		 			}
		 			catch(ExcepcionFinConexion|ExcepcionDeInterrupcion e) {
		 				while (desconexiones<2) {
		 						Thread.sleep(500);
			 					try {
									serverNotificacion.recibirmensajeDeCliente(0, false); //como ya se envió un mensaje, se reintenta recibir la confirmacion. Si nunca se recibe se da por perdida la conexion
									desconexiones=0;
			 					} catch (ExcepcionFinConexion | ExcepcionFinTimeoutLectura e1) {
									desconexiones++;
								}
		 				}
		 			}
	 			}
	 		}
		} 
	 	catch (ExcepcionErrorAlAceptar | ExcepcionFinTimeoutAceptar e) { 
		}
	 	catch(ExcepcionDeInterrupcion | InterruptedException|ExcepcionLecturaErronea e) { //se diferencia, ya que en estos casos ya se habia hecho el .accept() por ende hay que cerrar el socket, además del serversocket
	 	}
	 	finally {
	 		try {
				this.llamados.setActivado(false);
				serverNotificacion.cerrarPuertoServidor(); 
				serverNotificacion.cerrarConexion();
			} catch (ExcepcionErrorAlCerrar e1) {
				// no puede hacerse nada más que terminar el thread
			}
	 	}
	}


	@Override
	public void mostrar(String dni, String IDBox) throws ExcepcionFinConexion, ExcepcionDeInterrupcion, ExcepcionLecturaErronea {
			try {
 				serverNotificacion.enviarMensajeACliente(dni+";"+IDBox, true);//!!!) Los tiempos en los que se muestran los boxes los maneja el controlador de TvLlamados
			}
			finally {}
	}
}
