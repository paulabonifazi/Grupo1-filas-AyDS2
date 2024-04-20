package server;
import Excepciones.*;
import TCP.*;
import interfaces.INotificacion;

public class GestorNotificacion extends Thread implements INotificacion{
	private TCPServidor serverNotificacion;
	private MonitorNotificacion llamados;
	private String ipClienteEsperado;
	private Boolean fin=false; 
	
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
	 			this.llamados.setActivado(true); //activa el sistema de llamado
	 			while(!fin) { //No recibe datos, solo envia.
		 			llamado=llamados.take(); //espera por un elemento en el buffer de salida, en caso de ser interrumpida es porque es fin del servidor
		 			mostrar(llamado.getDNI(),llamado.getBox()); //!!!) EL SISTEMA DE LLAMADO DEBE INDICAR "Recibido" por cada mensaje que recibe, con ello el server sabe si todavia se mantiene la conexion
	 			}
	 		}
		} 
	 	catch (ExcepcionErrorAlAceptar | ExcepcionFinTimeoutAceptar e) { 
			try {
				this.llamados.setActivado(false);
				serverNotificacion.cerrarPuertoServidor(); 
			} catch (ExcepcionErrorAlCerrar e1) {
				// no puede hacerse nada más que terminar el thread
			}
		}
	 	catch(ExcepcionDeInterrupcion|ExcepcionFinConexion | InterruptedException|ExcepcionLecturaErronea e) { //se diferencia, ya que en estos casos ya se habia hecho el .accept() por ende hay que cerrar el socket, además del serversocket
	 		try {
	 			this.llamados.setActivado(false);
	 			serverNotificacion.cerrarConexion();
				serverNotificacion.cerrarPuertoServidor(); //por si acaso no se cerro (si se cierra y ya estaba cerrado se tira la excepcion error al cerrar) 
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
