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
	
    public String tiempos() {
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
    
    public String estado() {
    	String rta="";
    	Atencion sig;
		 Iterator<Atencion> iterator=atenciones.values().iterator();
	        while (iterator.hasNext()) {
	            sig= iterator.next();
	            rta+=sig.toString();
	            if(iterator.hasNext()){
	            	rta+=";";
	            }
	        }
	     return rta;
    }
    
    public void parse(String mensaje) {
		 int i=0;
		 this.ID=0;
		 if(mensaje!=null && !mensaje.isBlank() && !mensaje.isEmpty()) {
			 String[] historia=mensaje.split(";");
			 String[] entrada;
			 String[] infoturno;
			 String[] infosolicitud;
			 Turno turno;
			 Solicitud solicitud;
			 while(i<historia.length) {
				 entrada=historia[i].split("-");
				 if(entrada.length==4) {
					 infoturno=entrada[0].split(",");
				     infosolicitud=entrada[1].split(",");
					 turno=new Turno(infoturno[0],infoturno[1],infoturno[2]);
					 solicitud=new Solicitud(infosolicitud[0],infosolicitud[1]);
					 this.atenciones.put(ID++, new Atencion(turno, solicitud,entrada[2],entrada[3]));
				 }
				 i++;
			 }
		 }
	 }
}
