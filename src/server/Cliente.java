package server;

public class Cliente {
	private String dni;
	private int edad;
	private String grupo;
	
	public Cliente(String dni, int edad, String grupo) {
		this.dni=dni;
		this.edad=edad;
		if(grupo!= null) {
			if(ParametrosDeConexion.getInstance().getGrupoindex(grupo)!=-1)
				this.grupo=grupo;
			else
				this.grupo=ParametrosDeConexion.getInstance().getPeorGrupo();
		}
		else
			this.grupo= grupo;
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
