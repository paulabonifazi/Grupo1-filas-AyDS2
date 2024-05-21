package server;
/**
 * Es un Pojo, solo tiene datos del cliente y ningun comportamiento salvo getter y setter
 * 
 */
public class ClientePOJO {
	private String nombre;
	private String fechaDeNacimiento;
	private String membresia;
	
	
	public ClientePOJO(String nombre, String fechaDeNacimiento, String membresia) {
		super();
		this.nombre = nombre;
		this.fechaDeNacimiento = fechaDeNacimiento;
		this.membresia = membresia;
	}


	public String getNombre() {
		return nombre;
	}


	public void setNombre(String nombre) {
		this.nombre = nombre;
	}


	public String getFechaDeNacimiento() {
		return fechaDeNacimiento;
	}


	public void setFechaDeNacimiento(String fechaDeNacimiento) {
		this.fechaDeNacimiento = fechaDeNacimiento;
	}


	public String getMembresia() {
		return membresia;
	}


	public void setMembresia(String membresia) {
		this.membresia = membresia;
	}

	
	
}
