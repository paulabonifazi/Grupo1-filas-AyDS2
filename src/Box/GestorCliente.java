package Box;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

import Excepciones.ExcepcionErrorAlCerrar;
import Excepciones.ExcepcionErrorConexion;
import Excepciones.ExcepcionFinConexion;
import Excepciones.ExcepcionLecturaErronea;
import TCP.TCPCliente;

public class GestorCliente extends Thread{
	TCPCliente cliente;
	BlockingQueue<String> blockingQueue;
	ControladorVistaOperador controlador;
	IState estado;
	
	public GestorCliente(TCPCliente cliente,ControladorVistaOperador controlador) {
		super();
		this.cliente = cliente;
		blockingQueue = new LinkedBlockingDeque<>();
		this.controlador=controlador;
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
						e.printStackTrace();
					}
				while (mensaje==null);
				//Mensaje recibido
				elementos = mensaje.split(";");	
				switch (elementos[0]) {
	                case 2: //Actualizar personas en pantalla
	                	controlador.actualizarEstadoCola(cadena);
	                    break;
	                case 3: //Asignar cliente
	                	controlador.asignarCliente();
	                    break;
	     
	                default:
	                   
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
}
