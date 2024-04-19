package server;
import Excepciones.*;
import TCP.*;
import interfaces.INotificacion;

public class GestorNotificacion extends Thread implements INotificacion{
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
		Atencion llamado;
	 	try {
	 		this.serverNotificacion.aceptarConexion(7000); //espera por 7 segundos
	 		if(serverNotificacion.validarIPCliente(ipClienteEsperado)) {
	 			this.llamados.setActivado(true);
	 			while(true) { //No recibe datos, solo envia.
		 			llamado=llamados.take(); //espera por un elemento en el buffer de salida, en caso de ser interrumpida es porque es fin del servidor
		 			try {
		 				serverNotificacion.enviarMensajeACliente(mostrar(llamado.getDNI(), llamado.getBox()), false);//!!!) Los tiempos en los que se muestran los boxes los maneja el controlador de TvLlamados
					} catch (ExcepcionLecturaErronea e) {
						//nunca ocurre porque no se habilita la comprobacion
					}
	 			}
	 		}
		} 
	 	catch (ExcepcionErrorAlAceptar | ExcepcionFinTimeoutAceptar e) { 
			try {
				this.llamados.setActivado(false);
				serverNotificacion.cerrarPuertoServidor(); 
			} catch (ExcepcionErrorAlCerrar e1) {
				// no puede hacerse nada m�s que terminar el thread
			}
		}
	 	catch(ExcepcionDeInterrupcion|ExcepcionFinConexion | InterruptedException e) { //se diferencia, ya que en estos casos ya se habia hecho el .accept() por ende hay que cerrar el socket, adem�s del serversocket
	 		try {
	 			this.llamados.setActivado(false);
	 			serverNotificacion.cerrarConexion();
				serverNotificacion.cerrarPuertoServidor(); //por si acaso no se cerro (si se cierra y ya estaba cerrado se tira la excepcion error al cerrar) 
			} catch (ExcepcionErrorAlCerrar e1) {
				// no puede hacerse nada m�s que terminar el thread
			}
	 		
	 	}
	}


	@Override
	public String mostrar(String dni, String IDBox) {
			return dni+";"+IDBox;
	}
}
