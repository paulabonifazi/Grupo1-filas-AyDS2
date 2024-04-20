package server;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class MonitorNotificacion {
	private BlockingQueue<Atencion> notificaciones=new LinkedBlockingQueue<Atencion>();
	private boolean activado=false;


		public MonitorNotificacion() {
		}
		
		// Método para colocar un elemento en la cola
	    public void put(Atencion elemento) throws InterruptedException {
	    	if(activado)
	    		notificaciones.put(elemento);
	    }
	    
	 // Método para retirar un elemento de la cola
	    public Atencion take() throws InterruptedException {
	    	return notificaciones.take();
	    }

		public boolean isActivado() {
			return activado;
		}

		public void setActivado(boolean cambio) {
			if(activado==true && !cambio) 
				notificaciones=new LinkedBlockingQueue<Atencion>(); //cada vez que se quite el recurso,se vacia la cola de llamados por mostrar
			this.activado = cambio;
		}
	    
	    
}
