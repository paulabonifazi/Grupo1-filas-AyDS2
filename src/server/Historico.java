package server;

import java.util.Iterator;
import java.util.concurrent.ConcurrentHashMap;


public class Historico {
	private ConcurrentHashMap<Integer, Atencion> atenciones;
	private int ID=0;
	public Historico() {
		this.atenciones= new ConcurrentHashMap<Integer, Atencion>();
	}
	
	// Método para agregar una operación al historial
    public void agregarAtencion(Atencion atencion) {
        atenciones.put(ID++, atencion);
    }
	
    public String estado() {
    	String rta=atenciones.size()+"/"; //cantidad de atenciones
		
		 Iterator<Atencion> iterator=atenciones.values().iterator();
	        while (iterator.hasNext()) {
	            Atencion sig = iterator.next();
	            rta+=sig.getTiempoDeEspera()+","+sig.getTiempoDeSolicitud()+","+sig.getTiempoDeAtencion(); //para obtener los tiempos promedio
	            if(iterator.hasNext()){
	            	rta+=";";
	            }
	            
	        }
	     return rta; //Devuelve como resultado una parte del string que se le envia a estadistico
    }
    
}
