package server;

import java.time.LocalTime;


public class Turno {
	private LocalTime hrRegistro;
	private String dni;
	
	public Turno(String dni) {
		this.dni = dni;
		hrRegistro=	LocalTime.now();
	}

	public LocalTime getHrRegistro() {
		return hrRegistro;
	}

	public String getDni() {
		return dni;
	}
	
	
}
