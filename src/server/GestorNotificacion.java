package server;
import Excepciones.*;
import TCP.*;

public class GestorNotificacion extends Thread{
	TCPServidor serverNotificacion;
	MonitorNotificacion llamados;
	String ipClienteEsperado;
	
	public GestorNotificacion(MonitorNotificacion llamados,TCPServidor serverNotificacion,String ipClienteEsperado) {
		super();
		this.serverNotificacion = serverNotificacion;
		this.llamados = llamados;
		this.ipClienteEsperado = ipClienteEsperado;
	}


	@Override
    public void run() {
		String respuesta=null;
		Atencion llamado;
	 	try {
	 		this.serverNotificacion.aceptarConexion(7000); //espera por 7 segundos
	 		if(serverNotificacion.validarIPCliente(ipClienteEsperado)) {
	 			while(true) { //No recibe datos, solo envia.
		 			llamado=llamados.take(); //espera por un elemento en el buffer de salida, en caso de ser interrumpida es porque es fin del servidor
		 			respuesta=llamado.getDNI()+";"+llamado.getBox(); //arma el mensaje. "<dni>,<IDbox>"(el id de box es "B<nrobox>"
		 			try {
		 				serverNotificacion.enviarMensajeACliente(respuesta, false); //!!!) Los tiempos en los que se muestran los boxes los maneja el controlador de TvLlamados
					} catch (ExcepcionLecturaErronea e) {
						//nunca ocurre porque no se habilita la comprobacion
					}
	 			}
	 		}
		} 
	 	catch (ExcepcionErrorAlAceptar | ExcepcionFinTimeoutAceptar e) { 
			try {
				serverNotificacion.cerrarPuertoServidor(); 
			} catch (ExcepcionErrorAlCerrar e1) {
				// no puede hacerse nada más que terminar el thread
			}
		}
	 	catch(ExcepcionDeInterrupcion|ExcepcionFinConexion | InterruptedException e) { //se diferencia, ya que en estos casos ya se habia hecho el .accept() por ende hay que cerrar el socket, además del serversocket
	 		try {
	 			serverNotificacion.cerrarConexion();
				serverNotificacion.cerrarPuertoServidor(); //por si acaso no se cerro (si se cierra y ya estaba cerrado se tira la excepcion error al cerrar) 
			} catch (ExcepcionErrorAlCerrar e1) {
				// no puede hacerse nada más que terminar el thread
			}
	 		
	 	}
	}
}
