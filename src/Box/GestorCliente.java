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
	
	public GestorCliente(TCPCliente cliente) {
		super();
		this.cliente = cliente;
		blockingQueue = new LinkedBlockingDeque<>();
	}


	public void run() {
		while(true) {
			String mensaje=null;
			if (!cliente.estaCerrado()){		
				do
					try {
						mensaje=cliente.recibirmensajeDeServidor(false);
					} catch (ExcepcionFinConexion e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				while (mensaje==null);
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
