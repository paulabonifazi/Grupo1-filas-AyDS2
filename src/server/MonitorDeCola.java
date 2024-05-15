package server;

import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.Semaphore;

public class MonitorDeCola {
	private BlockingQueue<Turno> coladeTurnos;
	private Semaphore semaforodeSolicitud;
	//TODO Estructura para las atenciones pendientes, donde se agregan desde el metodo take (cuando se retira un turno y se inicia una atencion) y se retira cuando el box finaliza/ ausenta una atencion (va a tener que haber un metodo sincronziado para eliminar la atencion pendiente)
	private ConcurrentHashMap<String, String> atencionesAbiertas; 
	
	public MonitorDeCola() {
		this.coladeTurnos=new LinkedBlockingQueue<Turno>();
		this.semaforodeSolicitud=new Semaphore(1, true);
		atencionesAbiertas= new ConcurrentHashMap<String, String>();
	}
	
	// M�todo para colocar un elemento en la cola
    public void put(Turno elemento) throws InterruptedException {
    		coladeTurnos.put(elemento);
    }
    
 // M�todo para retirar un elemento de la cola
    public Turno take(String Id) throws InterruptedException {
    	Turno turno = null;
    	try {
	    	semaforodeSolicitud.acquire(); //garantiza que la toma de turnos es de a 1 box y que es por orden de llegada
	        turno = coladeTurnos.take();
	        atencionesAbiertas.put(Id, turno.getDni());
	        semaforodeSolicitud.release();
	        
    	}catch (InterruptedException e){
    		if(semaforodeSolicitud.availablePermits()==0) //por si a caso se interrumpio en el take... se asegure de devolver permiso
    			semaforodeSolicitud.release();
    		throw e;
        }
    	return turno;
    }
    
    public String finAtencion(String Id)  {
    	return  atencionesAbiertas.remove(Id);
    }
    
    public String hasAtencion(String Id)  {
    	return  atencionesAbiertas.get(Id);
    }
    
 // M�todo para obtener el tama�o de la cola
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
    
    public String estado() {
    	String estado="";
    	Turno turno;
    	Iterator<Turno> iterator = coladeTurnos.iterator();
    	while (iterator.hasNext()) {
    		turno=iterator.next();
    		estado+=turno.toString();
    		if(iterator.hasNext())
    			estado+=";";
    	}
    	estado+="/";
    	Iterator<Map.Entry<String, String>> it = atencionesAbiertas.entrySet().iterator();
    	Map.Entry<String, String> atencion;
    	while (it.hasNext()) {
    		atencion=it.next();
    		estado+=atencion.getKey()+","+atencion.getValue();
    		if(it.hasNext())
    			estado+=";";
    	}
    	return estado;
    }
    
    public void parse(String cola, String atenciones) {
		 int i;
		 this.coladeTurnos= new LinkedBlockingQueue<Turno>();
		 if(cola!=null && !cola.isBlank() && !cola.isEmpty()) {
			 String[] turnos=cola.split(";");
			 String[] turno;
			 i=0;
			 while(i<turnos.length) {
					 turno=turnos[i].split(",");
					 if(turno.length==3) {
						try {
							this.coladeTurnos.put(new Turno(turno[0],turno[1],turno[2]));
						} catch (InterruptedException e) {
						}
					 }
					 i++;
			 }
		 }
		 this.atencionesAbiertas=new ConcurrentHashMap<String, String>();
		 if(atenciones!=null && !atenciones.isBlank() && !atenciones.isEmpty()) {
			 String[] pendientes=atenciones.split(";");
			 String[] atencion;
			 i=0;
			 while(i<pendientes.length) {
				 atencion=pendientes[i].split(",");
				 if(atencion.length==2) {
					this.atencionesAbiertas.put(atencion[0], atencion[1]);
				 }
				 i++;
			 }
		 }
	 }
    
}

