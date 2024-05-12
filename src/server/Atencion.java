package server;

import java.time.Duration;
import java.time.LocalTime;

public class Atencion {
	private Turno turno;
	private Solicitud solicitud;
	private LocalTime hrinicio,hrfin;
	
	
	public Atencion(Turno turno, Solicitud solicitud) {
		super();
		this.turno = turno;
		this.solicitud = solicitud;
		hrinicio=LocalTime.now();
	}
	
	public void registrarFin() {
		hrfin=LocalTime.now();
	}
	
	public String getDNI() {
		return this.turno.getDni();
	}
	
	public long getTiempoDeEspera() {
		return Duration.between(turno.getHrRegistro(), hrinicio).getSeconds();
	}
	
	
	public long getTiempoDeAtencion() {
		return Duration.between(hrinicio, hrfin).getSeconds();
	}
	
	public long getTiempoDeSolicitud() {
		return Duration.between(solicitud.getHrSolicitud(), hrinicio).getSeconds();
	}
	
	public String getBox(){
		return solicitud.getIdBox();
	}

	@Override
	public String toString() {
		return  turno.toString()+"-"+solicitud.toString()+"-"+hrinicio.toString()+"-"+hrfin.toString();
	}
	
	
}
