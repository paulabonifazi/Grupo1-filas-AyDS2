package Box;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

import Excepciones.ExcepcionFinConexion;
import Excepciones.ExcepcionLecturaErronea;
import TCP.TCPCliente;

public class EnviadorMensajes extends Thread{
	TCPCliente cliente;
	BlockingQueue<String> blockingQueue;
	
	public EnviadorMensajes(TCPCliente cliente,BlockingQueue blockingQueue) {
		super();
		this.cliente = cliente;
		this.blockingQueue = blockingQueue;
	}
	
	public void run() {
		String mensaje;
		while(true) {
			mensaje=null;
			try {
				mensaje=blockingQueue.take();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();				
			}
			System.out.println(mensaje);
			try {
				cliente.enviarMensajeAlServidor(mensaje, false);
			} catch (ExcepcionLecturaErronea | ExcepcionFinConexion e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
			
}