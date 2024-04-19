package back;

import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


public class Historico {
	private ConcurrentHashMap<String, Atencion> atenciones;

	public Historico() {
		this.atenciones= new ConcurrentHashMap<String, Atencion>();
	}
	
	// Método para agregar una operación al historial
    public void agregarAtencion(Atencion atencion) {
        atenciones.put(atencion.getDNI(), atencion);
    }
	
    public String estado() {
    	String rta=atenciones.size()+"/";
		
		 Iterator<Atencion> iterator=atenciones.values().iterator();
	        while (iterator.hasNext()) {
	            Atencion sig = iterator.next();
	            rta+=sig.getTiempoDeEspera()+","+sig.getTiempoDeSolicitud()+","+sig.getTiempoDeAtencion();
	            if(iterator.hasNext()){
	            	rta+=",";
	            }
	            
	        }
	     return rta; //Devuelve como resultado una parte del string que se le envia a estadistico
    }
    
}
