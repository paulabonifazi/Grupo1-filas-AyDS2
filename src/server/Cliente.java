package server;

public class Cliente {
	private String dni;
	private int edad;
	private String grupo;
	
	public Cliente(String dni, int edad, String grupo) {
		this.dni=dni;
		this.edad=edad;
		this.grupo=grupo;
	}

	public String getDni() {
		return dni;
	}

	public int getEdad() {
		return edad;
	}

	public String getGrupo() {
		return grupo;
	}
	
	public String estado() {
		return dni+"#"+edad+"#"+grupo;
	}
}
