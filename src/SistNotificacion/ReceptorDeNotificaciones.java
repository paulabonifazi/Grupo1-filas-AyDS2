package SistNotificacion;

import Excepciones.ExcepcionErrorAlCerrar;
import Excepciones.ExcepcionFinConexion;
import Excepciones.ExcepcionLecturaErronea;
import TCP.TCPCliente;

public class ReceptorDeNotificaciones extends Thread{
	TCPCliente cliente;
	ControladorVistaMonitor controlador;
	
	
	public ReceptorDeNotificaciones() {
		super();

	}

	public void setCliente(TCPCliente cliente) {
		this.cliente = cliente;
	}


	public void setControlador(ControladorVistaMonitor controlador) {
		this.controlador = controlador;
	}


	public void run() {
		String mensaje;
		String[] elementos;
		while(true) {
			mensaje=null;
			elementos=null;
			if (!cliente.estaCerrado()){		
				do
					try {
						mensaje=cliente.recibirmensajeDeServidor(false);
					} catch (ExcepcionFinConexion e) {
						// TODO Auto-generated catch block
						System.exit(0);
					}
				while (mensaje==null);
				//Mensaje recibido
				elementos = mensaje.split(";");	
				
				actualizarLista(new FilaNotificacion(elementos[0],elementos[1]));
	            try {
					cliente.enviarMensajeAlServidor("Recibido", false);
				} catch (ExcepcionLecturaErronea e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (ExcepcionFinConexion e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}       
	        
			} else {
				try {
					cliente.cerrarConexion();
					interrupt();
				} catch (ExcepcionErrorAlCerrar e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	
	}
	
	public void actualizarLista(FilaNotificacion fila) {
		try {
			controlador.semaforo.acquire();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		controlador.actualizarLista(fila);
		controlador.semaforo.release();
		controlador.actualizarVista();
			
	}			
}