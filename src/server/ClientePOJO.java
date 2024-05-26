package prueba;
/**
 * Es un Pojo, solo tiene datos del cliente y ningun comportamiento salvo getter y setter
 * 
 */
public class ClientePOJO {
	public String nombre;
	public String fechaDeNacimiento;
	public String membresia;
	
	
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


	@Override
	public String toString() {
		return "ClientePOJO [nombre=" + nombre + ", fechaDeNacimiento=" + fechaDeNacimiento + ", membresia=" + membresia
				+ "]";
	}

	
	
}
