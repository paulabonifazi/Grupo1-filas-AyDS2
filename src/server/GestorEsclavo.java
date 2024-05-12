package server;
import java.util.HashMap;
import java.util.LinkedList;

import Excepciones.*;
import TCP.*;
import interfaces.INotificacion;

public class GestorEsclavo extends Thread{
	private TCPServidor serverEsclavo;
	private String ipClienteEsperado;
	private MonitorDeCola cola;
	private MonitorNotificacion llamados;
	private Historico historico;
	private ParametrosDeConexion parametros;
	private HashMap<String, IConexion> conexiones;
	private  LinkedList<Esclavo> listaEsclavos;
	
	public GestorEsclavo(TCPServidor serverEsclavo,String ipClienteEsperado,MonitorDeCola cola,MonitorNotificacion llamados,Historico historico, ParametrosDeConexion parametros,HashMap<String, IConexion> conexiones,LinkedList<Esclavo> listaEsclavos) {
		super();
		this.serverEsclavo = serverEsclavo;
		this.ipClienteEsperado = ipClienteEsperado;
		this.cola=cola;
		this.llamados=llamados;
		this.historico=historico;
		this.parametros=parametros;
		this.conexiones=conexiones;
		this.listaEsclavos=listaEsclavos;
	}


	@Override
    public void run() {
		int desconexiones=0;
	 	try {
	 		this.serverEsclavo.aceptarConexion(7000); //espera por 7 segundos
	 		if(serverEsclavo.validarIPCliente(ipClienteEsperado)) {
	 			while(desconexiones<2) { //No recibe datos, solo envia.
		 			try {
		 				serverEsclavo.enviarMensajeACliente("hola", true); //TODO Mensaje con toda la info
		 			}
		 			catch(ExcepcionFinConexion|ExcepcionDeInterrupcion e) {
		 				while (desconexiones<2) {
		 						Thread.sleep(500);
			 					try {
									serverEsclavo.recibirmensajeDeCliente(0, false); //como ya se envió un mensaje, se reintenta recibir la confirmacion. Si nunca se recibe se da por perdida la conexion
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
				serverEsclavo.cerrarPuertoServidor(); 
				serverEsclavo.cerrarConexion();
			} catch (ExcepcionErrorAlCerrar e1) {
				// no puede hacerse nada más que terminar el thread
			}
	 	}
	}
}
