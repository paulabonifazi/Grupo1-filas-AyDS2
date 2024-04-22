package server;

import java.time.LocalTime;

public class Solicitud {
	private LocalTime hrinicio;
	private String idBox;
	
	public Solicitud(String IdBox) {
		this.idBox=IdBox;
		hrinicio=LocalTime.now();
	}

	public LocalTime getHrSolicitud() {
		return hrinicio;
	}

	public String getIdBox() {
		return idBox;
	}
	
	
	
}
