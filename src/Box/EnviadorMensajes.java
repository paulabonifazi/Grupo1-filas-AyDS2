package Box;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

import Excepciones.ExcepcionErrorAlCerrar;
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
		System.out.println("Se creo un Thread de EnviadorMensajes"); //DEBUG
		while(!this.isInterrupted()) {
			mensaje=null;
			try {
				mensaje=blockingQueue.take();
				if (mensaje!=null) {
					System.out.println("Mensaje Saliente"+mensaje); //DEBUG
					switch(mensaje) {
						case "Fin":
							cliente.enviarMensajeAlServidor(mensaje, true);
						case "Ausente":
							cliente.enviarMensajeAlServidor(mensaje, true);
						default:
							cliente.enviarMensajeAlServidor(mensaje, false);
					}
				}
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();		
				this.interrupt();
			} catch (ExcepcionLecturaErronea e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ExcepcionFinConexion e) {
				try {
					cliente.cerrarConexion();
				} catch (ExcepcionErrorAlCerrar e1) {
				}
				if (mensaje!=null)
					blockingQueue.add(mensaje);
				this.interrupt();
				System.out.println("Enviador ExcfinConec"); //DEBUG
				controlador.reintentarConexion();
				
			}
			
			
		}
		System.out.println("Este thread ha muerto"); // DEBUG
	}
			
}