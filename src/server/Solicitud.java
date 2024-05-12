package server;

import java.time.LocalTime;

public class Solicitud {
	private LocalTime hrinicio;
	private String idBox;
	
	public Solicitud(String IdBox) {
		this.idBox=IdBox;
		hrinicio=LocalTime.now();
	}
	
	public Solicitud(String IdBox,String hr) {
		this.idBox=IdBox;
		hrinicio=LocalTime.parse(hr);
	}

	public LocalTime getHrSolicitud() {
		return hrinicio;
	}

	public String getIdBox() {
		return idBox;
	}

	@Override
	public String toString() {
		return  idBox+","+hrinicio.toString();
	}
}
