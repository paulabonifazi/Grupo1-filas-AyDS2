package back;

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
    	semaforodeSolicitud.acquire();
        Turno turno= coladeTurnos.take();
        semaforodeSolicitud.release();
        return turno;
    }
    
 // Método para obtener el tamaño de la cola
    public int size() {
        return coladeTurnos.size();
    }
}
