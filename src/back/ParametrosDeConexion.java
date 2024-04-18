package back;

import java.util.Iterator;
import java.util.concurrent.ConcurrentHashMap;

public class ParametrosDeConexion {
	private int PuertoLibre; //este atributo lo completa el gestor de conexiones (no el main)
	private String contraseña;
	private boolean finalizar=false;

	public ParametrosDeConexion(String contraseña) {
		this.contraseña=contraseña;
	}

	public int getPuertoLibre() {
		return PuertoLibre;
	}

	public void setPuertoLibre(int puertoLibre) {
		PuertoLibre = puertoLibre;
	}

	public String getContraseña() {
		return contraseña;
	}

	public void setContraseña(String contraseña) {
		this.contraseña = contraseña;
	}

	public boolean isFinalizar() {
		return finalizar;
	}

	public void setFinalizar(boolean finalizar) {
		this.finalizar = finalizar;
	}
	
	
}
