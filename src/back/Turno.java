package back;

import java.time.LocalTime;


public class Turno {
	LocalTime hrRegistro;
	String dni;
	
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
