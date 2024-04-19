package server;

public class ParametrosDeConexion {
	private int PuertoLibre; //este atributo lo completa el gestor de conexiones (no el main)
	private String IP; //este atributo se define en el gestor de conexiones (no en el main)
	private String contraseña; //lo cambia y consulta el main e impacta en el gestor de conexiones
	private boolean finalizar=false; //lo cambia el main e impacta en todos los threads de conexiones, cerrando recursos y finalizandolos

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

	public String getIP() {
		return IP;
	}

	public void setIP(String iP) {
		IP = iP;
	}
	
}
