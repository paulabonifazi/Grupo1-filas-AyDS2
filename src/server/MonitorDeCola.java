package server;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Semaphore;

public class MonitorDeCola {
	private ArrayList<Turno> listadeTurnos;
	private Semaphore semaforodeacceso;
	private Semaphore semaforodeSolicitud;
	private MonitorPersistencia bufferpersistencia;
	//TODO Estructura para las atenciones pendientes, donde se agregan desde el metodo take (cuando se retira un turno y se inicia una atencion) y se retira cuando el box finaliza/ ausenta una atencion (va a tener que haber un metodo sincronziado para eliminar la atencion pendiente)
	private ConcurrentHashMap<String, Turno> atencionesAbiertas; 
	private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
	private IStrategy estrategia;
	
	public void setbufferPersistencia(MonitorPersistencia buffer) {
		this.bufferpersistencia=buffer;
	}
	
	public MonitorDeCola() {
		this.listadeTurnos=new ArrayList<Turno>();
		this.semaforodeSolicitud=new Semaphore(0, true);
		this.semaforodeacceso=new Semaphore(1,true);
		atencionesAbiertas= new ConcurrentHashMap<String, Turno>();
	}
	
	// Mï¿½todo para colocar un elemento en la cola
    public void put(Turno elemento) throws InterruptedException {
    		semaforodeacceso.acquire();;
    		if(elemento.getAusencias()>0) {
    			if(listadeTurnos.size()>3)
    				listadeTurnos.add(3, elemento);
    			else
    				listadeTurnos.add(elemento);
    		}
    		else
    			insertaOrdenado(elemento);
    		if(elemento.getAusencias()==0) {
    			LocalDate fechaActual = LocalDate.now();
    			this.bufferpersistencia.put("Ingreso-"+ elemento.getDni()+"-"+elemento.getHrRegistro().format(formatter)+"["+fechaActual.getDayOfMonth()+"/"+fechaActual.getMonthValue()+"/"+fechaActual.getYear()+"]");
    		}
    		semaforodeSolicitud.release();
    		semaforodeacceso.release();
    }
    
 private void insertaOrdenado(Turno elemento) {
	 int indice = listadeTurnos.size();
	    while (indice > 0 && (listadeTurnos.get(indice-1).getAusencias()>0||estrategia.compare(elemento, listadeTurnos.get(indice-1)) > 0)) {
	        indice--;
	    }
	 // Inserta el nuevo elemento en su posición ordenada
	    listadeTurnos.add(indice, elemento);
	}

	// Mï¿½todo para retirar un elemento de la cola
    public Turno take(String Id) throws InterruptedException {
    	Turno turno = null;
    	try {
	    	semaforodeSolicitud.acquire(); //garantiza que la toma de turnos es de a 1 box y que es por orden de llegada
		    try {	
	    		semaforodeacceso.acquire();
		        turno = listadeTurnos.remove(0);
		        atencionesAbiertas.put(Id, turno);
		        if(turno.getAusencias()==0) {
		        	LocalDate fechaActual = LocalDate.now();
		        	
		        	this.bufferpersistencia.put("Egreso-"+turno.getDni()+"-"+LocalTime.now().format(formatter)+"["+fechaActual.getDayOfMonth()+"/"+fechaActual.getMonthValue()+"/"+fechaActual.getYear()+"]");
		        }
		        semaforodeacceso.release();
		    }
		    catch (InterruptedException e){
	    		semaforodeSolicitud.release();
	    		throw e;
		    }
    	}finally {
			
		}
    	return turno;
    }
    
    public Turno finAtencion(String Id)  {
    	return  atencionesAbiertas.remove(Id);
    }
    
    public String hasAtencion(String Id)  {
    	Turno turno= atencionesAbiertas.get(Id);
    	String respuesta=null;
    	if(turno!=null) {
    		respuesta=turno.getDni();
    	}
    	return respuesta ;
    }
    
 // Mï¿½todo para obtener el tamaï¿½o de la cola
    public int size() {
        return listadeTurnos.size();
    }
    
    public boolean contiene(String DNI) {
    	Iterator<Turno> iterator = listadeTurnos.iterator();
    	Boolean encuentra=false;
        while (iterator.hasNext()&& !encuentra) {
            Turno elem = iterator.next();
            if (elem.getDni().equals(DNI)) {
                encuentra=true;
            }
        }
        if(!encuentra) { // si no encuentra en cola busca en atenciones pendientes
        	iterator= atencionesAbiertas.values().iterator();
        	while (iterator.hasNext()&& !encuentra) {
                Turno elem = iterator.next();
                if (elem.getDni().equals(DNI)) {
                    encuentra=true;
                }
            }
        }
        return encuentra;
    }
    
    public String estado() {
    	String estado="";
    	Turno turno;
    	Iterator<Turno> iterator = listadeTurnos.iterator();
    	while (iterator.hasNext()) {
    		turno=iterator.next();
    		estado+=turno.toString();
    		if(iterator.hasNext())
    			estado+=";";
    	}
    	estado+="/";
    	Iterator<Map.Entry<String, Turno>> it = atencionesAbiertas.entrySet().iterator();
    	Map.Entry<String, Turno> atencion;
    	while (it.hasNext()) {
    		atencion=it.next();
    		estado+=atencion.getKey()+",";
    		estado+=atencion.getValue().toString();
    		if(it.hasNext())
    			estado+=";";
    	}
    	return estado;
    }
    
    public void parse(String cola, String atenciones) {
		 int i;
		 this.listadeTurnos= new ArrayList<Turno>();
		 if(cola!=null && !cola.isBlank() && !cola.isEmpty()) {
			 String[] turnos=cola.split(";");
			 String[] turno;
			 String[] cliente;
			 i=0;
			 while(i<turnos.length) {
					 turno=turnos[i].split(",");
					 if(turno.length==4) {
							cliente= turno[0].split("#");
							if(cliente.length==3)
								this.listadeTurnos.add(new Turno(new Cliente(cliente[0],Integer.parseInt(cliente[1]),cliente[2]),turno[1],turno[2],turno[3]));
					 i++;
			 }
		 }
		 this.atencionesAbiertas=new ConcurrentHashMap<String, Turno>();
		 if(atenciones!=null && !atenciones.isBlank() && !atenciones.isEmpty()) {
			 String[] pendientes=atenciones.split(";");
			 String[] atencion;
			 i=0;
			 while(i<pendientes.length) {
				 atencion=pendientes[i].split(",");
				 if(atencion.length==5) {
					cliente= atencion[1].split("#");
					this.atencionesAbiertas.put(atencion[0], new Turno(new Cliente(cliente[0],Integer.parseInt(cliente[1]),cliente[2]),atencion[2],atencion[3],atencion[4]));
				 }
				 i++;
			 }
		 }
	 }
    }
	public void setStrategy(IStrategy estrategia) {
		this.estrategia=estrategia;
	}
}

