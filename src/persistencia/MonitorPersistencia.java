package persistencia;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class MonitorPersistencia {
	private BlockingQueue<String> notificaciones=new LinkedBlockingQueue<String>();


		public MonitorPersistencia() {
		}
		
		// Método para colocar un elemento en la cola
	    public void put(String evento) throws InterruptedException {
	    		notificaciones.put(evento);
	    }
	    
	 // Método para retirar un elemento de la cola
	    public String take() throws InterruptedException {
	    	return notificaciones.take();
	    }
		 
}
