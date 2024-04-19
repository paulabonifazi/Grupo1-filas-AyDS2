package server;
import Excepciones.*;
import TCP.*;

public class GestorNotificacion extends Thread{
	TCPServidor serverNotificacion;
	MonitorDeCola cola;
	String ipClienteEsperado;
	
	public GestorNotificacion(MonitorDeCola cola,TCPServidor serverNotificacion,String ipClienteEsperado) {
		super();
		this.serverNotificacion = serverNotificacion;
		this.cola = cola;
		this.ipClienteEsperado = ipClienteEsperado;
	}


	@Override
    public void run() {
		String mensaje = null;
		String[] elementos = null;
		String respuesta=null;
	 	try {
	 		this.serverNotificacion.aceptarConexion(7000); //espera por 7 segundos
	 		if(serverNotificacion.validarIPCliente(ipClienteEsperado)) {
	 			while(true) { //No recibe datos, solo envia.
		 			/**
		 			 * Quedo a la espera de qué envia el gestor de atencion
		 			 * 
		 			 * 
		 			 */
		 			try {
		 				serverNotificacion.enviarMensajeACliente(respuesta, false);
					} catch (ExcepcionLecturaErronea e) {
						//nunca ocurre porque no se habilita la comprobacion
					}
	 			}
	 		}
		} 
	 	catch (ExcepcionErrorAlAceptar | ExcepcionFinTimeoutAceptar | ExcepcionDeInterrupcion|ExcepcionFinConexion e) {
			try {
				serverNotificacion.cerrarConexion();
				serverNotificacion.cerrarPuertoServidor(); //por si acaso no se cerro (si se cierra y ya estaba cerrado se tira la excepcion error al cerrar)
			} catch (ExcepcionErrorAlCerrar e1) {
				// no puede hacerse nada más que terminar el thread
			}
		}
	}
}
