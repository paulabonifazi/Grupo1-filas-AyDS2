package server;

import java.util.Iterator;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class MonitorNotificacion {
	private BlockingQueue<Llamado> notificaciones=new LinkedBlockingQueue<Llamado>();
	private boolean activado=false;


		public MonitorNotificacion() {
		}
		
		// Método para colocar un elemento en la cola
	    public void put(Llamado elemento) throws InterruptedException {
	    	if(activado)
	    		notificaciones.put(elemento);
	    }
	    
	 // Método para retirar un elemento de la cola
	    public Llamado take() throws InterruptedException {
	    	return notificaciones.take();
	    }

		public boolean isActivado() {
			return activado;
		}

		public void setActivado(boolean cambio) {
			if(activado==true && !cambio) 
				notificaciones=new LinkedBlockingQueue<Llamado>(); //cada vez que se quite el recurso,se vacia la cola de llamados por mostrar
			this.activado = cambio;
		}
	    
		 public String estado() {
		    	String estado="";
		    	Llamado llamado;
		    	Iterator<Llamado> iterator = notificaciones.iterator();
		    	while (iterator.hasNext()) {
		    		llamado=iterator.next();
		    		estado+=llamado.getDNI()+","+llamado.getBox();
		    		if(iterator.hasNext())
		    			estado+=";";
		    	}
		    	return estado;
		 }
}
