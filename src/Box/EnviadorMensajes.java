package Box;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

import Excepciones.ExcepcionFinConexion;
import Excepciones.ExcepcionLecturaErronea;
import TCP.TCPCliente;

public class EnviadorMensajes extends Thread{
	TCPCliente cliente;
	BlockingQueue<String> blockingQueue;
	ControladorVistaOperador controlador;
	
	public EnviadorMensajes(TCPCliente cliente,BlockingQueue blockingQueue,ControladorVistaOperador controlador) {
		super();
		this.cliente = cliente;
		this.blockingQueue = blockingQueue;
		this.controlador=controlador;
	}
	
	public void run() {
		String mensaje;
		while(!this.isInterrupted()) {
			mensaje=null;
			try {
				mensaje=blockingQueue.take();
				switch(mensaje) {
				case "Fin":
					cliente.enviarMensajeAlServidor(mensaje, true);
				case "Ausente":
					cliente.enviarMensajeAlServidor(mensaje, true);
				default:
					cliente.enviarMensajeAlServidor(mensaje, false);
			
			}
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();					
			} catch (ExcepcionLecturaErronea e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ExcepcionFinConexion e) {
				blockingQueue.add(mensaje);
				controlador.reintentarConexion();
				this.interrupt();
			}

			
		}
	}
			
}