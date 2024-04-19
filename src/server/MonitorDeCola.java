package server;

import java.util.Iterator;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.Semaphore;

public class MonitorDeCola {
	private BlockingQueue<Turno> coladeTurnos;
	private Semaphore semaforodeSolicitud;

	public MonitorDeCola() {
		this.coladeTurnos=new LinkedBlockingQueue<Turno>();
		this.semaforodeSolicitud=new Semaphore(1, true);
	}
	
	// Método para colocar un elemento en la cola
    public void put(Turno elemento) throws InterruptedException {
    	coladeTurnos.put(elemento);
    }
    
 // Método para retirar un elemento de la cola
    public Turno take() throws InterruptedException {
    	semaforodeSolicitud.acquire(); //garantiza que la toma de turnos es de a 1 box y que es por orden de llegada
        Turno turno= coladeTurnos.take();
        semaforodeSolicitud.release();
        return turno;
    }
    
 // Método para obtener el tamaño de la cola
    public int size() {
        return coladeTurnos.size();
    }
    
    public boolean contiene(String DNI) {
    	Iterator<Turno> iterator = coladeTurnos.iterator();
    	Boolean encuentra=false;
        while (iterator.hasNext()&& !encuentra) {
            Turno elem = iterator.next();
            if (elem.getDni().equals(DNI)) {
                encuentra=true;
            }
        }
        
        return encuentra;
    }
}
