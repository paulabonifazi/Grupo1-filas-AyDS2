package server;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class MonitorNotificacion {
	private BlockingQueue<Atencion> notificaciones=new LinkedBlockingQueue<Atencion>();


		public MonitorNotificacion() {
		}
		
		// Método para colocar un elemento en la cola
	    public void put(Atencion elemento) throws InterruptedException {
	    	notificaciones.put(elemento);
	    }
	    
	 // Método para retirar un elemento de la cola
	    public Atencion take() throws InterruptedException {
	    	return notificaciones.take();
	    }
}
