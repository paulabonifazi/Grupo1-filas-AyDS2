package server;

import java.time.LocalTime;


public class Turno {
	private int ausencias;
	private LocalTime hrRegistro;
	private LocalTime hrIngresoCola;
	private Cliente cliente;

	public Turno() {
		cliente=null;
		ausencias=0;
	}
	
	public Turno(Cliente cliente) {
		this.cliente = cliente;
		hrRegistro=	LocalTime.now();
		hrIngresoCola=LocalTime.now();
		ausencias=0;
	}
	
	public Turno(Cliente cliente,String hrregistro,String hrausente, String ausencias) {
		this.cliente = cliente;
		this.hrRegistro=LocalTime.parse(hrregistro);
		this.hrIngresoCola=LocalTime.parse(hrausente);
		this.ausencias=Integer.parseInt(ausencias);
	}


	public LocalTime getHrRegistro() {
		return hrRegistro;
	}
	
	public LocalTime getHrIngresoCola() {
		return hrIngresoCola;
	}

	public String getDni() {
		String dni=null;
		if(cliente!=null)
			dni=cliente.getDni();
		return dni;
	}
	
	public int getedad() {
		int edad=0;
		if(cliente!=null) {
			edad=cliente.getEdad();
		}
		return edad;
	}
	
	public String getgrupo() {
		String grupo=null;
		if(cliente!=null)
			grupo=cliente.getGrupo();
		return grupo;
	}
	
	public Cliente getCliente() {
		return cliente;
	}
	
	public void setTurno(Cliente cliente,LocalTime hrRegistro, LocalTime hrIngresoCola,int ausencias) { //le permite al subthread de solicitud de turno asignarle un turno al thread de gestorBox
		this.cliente = cliente;
		this.hrRegistro= hrRegistro;
		this.hrIngresoCola=hrIngresoCola;
		this.ausencias=ausencias;
	}
	
	public void addAusencia() {
		int aux;
		aux=this.ausencias;
		this.ausencias=aux+1;
		hrIngresoCola=	LocalTime.now();
	}

	public int getAusencias() {
		return this.ausencias;
	}

	@Override
	public String toString() {
		return cliente.estado()+","+hrRegistro.toString()+","+hrIngresoCola.toString()+","+ausencias;
	}
	
	
}
