package server;

import java.time.LocalTime;


public class Turno {
	private int ausencias;
	private LocalTime hrRegistro;
	private String dni;
	
	public Turno() {
		dni=null;
		ausencias=0;
	}
	
	public Turno(String dni) {
		this.dni = dni;
		hrRegistro=	LocalTime.now();
		ausencias=0;
	}

	public LocalTime getHrRegistro() {
		return hrRegistro;
	}

	public String getDni() {
		return dni;
	}
	
	public void setTurno(String dni,LocalTime hrRegistro,int ausencias) { //le permite al subthread de solicitud de turno asignarle un turno al thread de gestorBox
		this.dni = dni;
		this.hrRegistro= hrRegistro;
		this.ausencias=ausencias;
	}
	
	public void addAusencia() {
		int aux;
		aux=this.ausencias;
		this.ausencias=aux+1;
	}
	
	public int getAusencias() {
		return this.ausencias;
	}
}
