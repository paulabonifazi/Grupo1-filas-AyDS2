package server;

public class Llamado {
	private String DNI;
	private String IdBox;
	
	
	public Llamado(String dni, String IdBox ) {
		super();
		this.DNI=dni;
		this.IdBox=IdBox;
	}
	
	public String getDNI() {
		return this.DNI;
	}
	
	public String getBox(){
		return this.IdBox;
	}
}
