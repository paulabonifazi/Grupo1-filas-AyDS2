package server;

public class ParametrosDeConexion {
	private int PuertoLibre; //este atributo lo completa el gestor de conexiones (no el main)
	private String IP; //este atributo se define en el gestor de conexiones (no en el main)
	private String contrase�a; //lo cambia y consulta el main e impacta en el gestor de conexiones
	private boolean finalizar=false; //lo cambia el main e impacta en todos los threads de conexiones, cerrando recursos y finalizandolos

	public ParametrosDeConexion(String contrase�a) {
		this.contrase�a=contrase�a;
	}

	public int getPuertoLibre() {
		return PuertoLibre;
	}

	public void setPuertoLibre(int puertoLibre) {
		PuertoLibre = puertoLibre;
	}

	public String getContrase�a() {
		return contrase�a;
	}

	public void setContrase�a(String contrase�a) {
		this.contrase�a = contrase�a;
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
