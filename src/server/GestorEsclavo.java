package server;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;

import Excepciones.*;
import TCP.*;

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
	 		this.serverEsclavo.aceptarConexion(30000);
	 		if(serverEsclavo.validarIPCliente(ipClienteEsperado)) {
	 			while(desconexiones<2) { //No recibe datos, solo envia.
		 			try {
		 				Thread.sleep(5000);
		 				serverEsclavo.enviarMensajeACliente(estadoServer(), true);
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
				try {
					serverEsclavo.cerrarConexion();
				} catch (ExcepcionErrorAlCerrar e) {
				}
			}
	 	}
	}
	
	//TODO devolver el estado con un formato para que lo entienda el esclavo
	private String estadoServer() {
		String estado=null;
		estado=cola.estado()+"/"+llamados.estado()+"/"+historico.estado()+"/"+parametros.getContraseña()+"/"+ this.estadoConexiones() +"/"+this.estadoEsclavos();
		return estado;
	}
	//HashMap<String, IConexion> conexiones,LinkedList<Esclavo> listaEsclavos
	private String estadoConexiones() {
		String mensaje="";
		IConexion conexion;
		Iterator<IConexion> it= conexiones.values().iterator();
		while(it.hasNext()) {
			conexion= it.next();
			mensaje+=conexion.toString();
			if(it.hasNext())
				mensaje+=";";
		}
		return mensaje;
	}
	
	private String estadoEsclavos() {
		String mensaje="";
		Esclavo esclavo;
		Iterator<Esclavo> it= listaEsclavos.iterator();
		while(it.hasNext()) {
			esclavo=it.next();
			mensaje+=esclavo.toString();
			if(it.hasNext())
				mensaje+=";";
		}
		return mensaje;
	}
}
